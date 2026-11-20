package com.qrmenu.platform.auth.dto;

public class AuthResponse {
    private String accessToken;
    private String tokenType; // "Bearer"
    private StaffUserDto user;


    public static class AuthResponseBuilder {
        private String accessToken;
        private String tokenType;
        private StaffUserDto user;

        AuthResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public AuthResponse.AuthResponseBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AuthResponse.AuthResponseBuilder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AuthResponse.AuthResponseBuilder user(StaffUserDto user) {
            this.user = user;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this.accessToken, this.tokenType, this.user);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "AuthResponse.AuthResponseBuilder(accessToken=" + this.accessToken + ", tokenType=" + this.tokenType + ", user=" + this.user + ")";
        }
    }

    public static AuthResponse.AuthResponseBuilder builder() {
        return new AuthResponse.AuthResponseBuilder();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public StaffUserDto getUser() {
        return this.user;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setUser(StaffUserDto user) {
        this.user = user;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AuthResponse)) return false;
        AuthResponse other = (AuthResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$accessToken = this.getAccessToken();
        java.lang.Object other$accessToken = other.getAccessToken();
        if (this$accessToken == null ? other$accessToken != null : !this$accessToken.equals(other$accessToken)) return false;
        java.lang.Object this$tokenType = this.getTokenType();
        java.lang.Object other$tokenType = other.getTokenType();
        if (this$tokenType == null ? other$tokenType != null : !this$tokenType.equals(other$tokenType)) return false;
        java.lang.Object this$user = this.getUser();
        java.lang.Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof AuthResponse;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $accessToken = this.getAccessToken();
        result = result * PRIME + ($accessToken == null ? 43 : $accessToken.hashCode());
        java.lang.Object $tokenType = this.getTokenType();
        result = result * PRIME + ($tokenType == null ? 43 : $tokenType.hashCode());
        java.lang.Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "AuthResponse(accessToken=" + this.getAccessToken() + ", tokenType=" + this.getTokenType() + ", user=" + this.getUser() + ")";
    }

    public AuthResponse() {
    }

    public AuthResponse(String accessToken, String tokenType, StaffUserDto user) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.user = user;
    }
}
