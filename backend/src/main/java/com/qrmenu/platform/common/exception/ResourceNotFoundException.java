package com.qrmenu.platform.common.exception;

/**
 * Thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resourceType, Object identifier) {
        super("RESOURCE_NOT_FOUND",
              String.format("%s not found with identifier: %s", resourceType, identifier));
    }
}
