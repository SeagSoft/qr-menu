package com.qrmenu.platform.common.exception;

/**
 * Thrown when a business rule is violated.
 */
public class BusinessRuleException extends BusinessException {

    public BusinessRuleException(String errorCode, String message) {
        super(errorCode, message);
    }
}
