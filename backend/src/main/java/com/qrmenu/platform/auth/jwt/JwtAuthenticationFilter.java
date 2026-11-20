package com.qrmenu.platform.auth.jwt;

import com.qrmenu.platform.common.context.TenantContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String DEFAULT_KIOSK_TOKEN = "GOURMET-KDS-2026";
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Claims claims = tokenProvider.getClaimsFromToken(jwt);
                String email = claims.getSubject();
                Long businessId = claims.get("businessId", Long.class);
                Long branchId = claims.get("branchId", Long.class);
                // Set Tenant Context for the current thread
                if (businessId != null) {
                    TenantContext.setBusinessId(businessId);
                }
                if (branchId != null) {
                    TenantContext.setBranchId(branchId);
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Check Kitchen Kiosk Token for /api/staff/** KDS requests
                String kioskToken = request.getHeader("X-Kiosk-Token");
                if (StringUtils.hasText(kioskToken) && (DEFAULT_KIOSK_TOKEN.equals(kioskToken) || kioskToken.startsWith("GOURMET-KDS-")) && request.getRequestURI().startsWith("/api/staff/")) {
                    TenantContext.setBusinessId(1L);
                    TenantContext.setBranchId(1L);
                    UsernamePasswordAuthenticationToken kioskAuth = new UsernamePasswordAuthenticationToken("kiosk@gourmet.com", null, List.of(new SimpleGrantedAuthority("ROLE_KITCHEN")));
                    kioskAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(kioskAuth);
                }
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            // ALWAYS clear tenant context to avoid ThreadLocal leaks
            TenantContext.clear();
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
