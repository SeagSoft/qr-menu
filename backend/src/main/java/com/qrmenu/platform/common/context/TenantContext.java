package com.qrmenu.platform.common.context;

/**
 * ThreadLocal context for holding tenant (Business and Branch) information.
 */
public class TenantContext {

    private static final ThreadLocal<Long> currentBusinessId = new ThreadLocal<>();
    private static final ThreadLocal<Long> currentBranchId = new ThreadLocal<>();

    public static void setBusinessId(Long businessId) {
        currentBusinessId.set(businessId);
    }

    public static Long getBusinessId() {
        return currentBusinessId.get();
    }

    public static void setBranchId(Long branchId) {
        currentBranchId.set(branchId);
    }

    public static Long getBranchId() {
        return currentBranchId.get();
    }

    public static void clear() {
        currentBusinessId.remove();
        currentBranchId.remove();
    }
}
