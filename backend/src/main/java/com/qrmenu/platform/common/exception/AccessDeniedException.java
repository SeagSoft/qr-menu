package com.qrmenu.platform.common.exception;

/**
 * Thrown when a user attempts to access a resource they don't have permission for.
 */
public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super("ACCESS_DENIED", message);
    }

    public AccessDeniedException() {
        this("You do not have permission to access this resource");
    }
}
