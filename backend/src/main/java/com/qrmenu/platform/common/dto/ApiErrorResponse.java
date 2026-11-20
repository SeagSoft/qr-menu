package com.qrmenu.platform.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

/**
 * Standard error response returned by all API endpoints.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private Instant timestamp;
    private int status;
    private String errorCode;
    private String message;
    private String path;
    private List<ValidationError> validationErrors;
    private String traceId;


    public static class ValidationError {
        private String field;
        private String message;


        public static class ValidationErrorBuilder {
            private String field;
            private String message;

            ValidationErrorBuilder() {
            }

            /**
             * @return {@code this}.
             */
            public ApiErrorResponse.ValidationError.ValidationErrorBuilder field(String field) {
                this.field = field;
                return this;
            }

            /**
             * @return {@code this}.
             */
            public ApiErrorResponse.ValidationError.ValidationErrorBuilder message(String message) {
                this.message = message;
                return this;
            }

            public ApiErrorResponse.ValidationError build() {
                return new ApiErrorResponse.ValidationError(this.field, this.message);
            }

            @java.lang.Override
            public java.lang.String toString() {
                return "ApiErrorResponse.ValidationError.ValidationErrorBuilder(field=" + this.field + ", message=" + this.message + ")";
            }
        }

        public static ApiErrorResponse.ValidationError.ValidationErrorBuilder builder() {
            return new ApiErrorResponse.ValidationError.ValidationErrorBuilder();
        }

        public String getField() {
            return this.field;
        }

        public String getMessage() {
            return this.message;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @java.lang.Override
        public boolean equals(java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof ApiErrorResponse.ValidationError)) return false;
            ApiErrorResponse.ValidationError other = (ApiErrorResponse.ValidationError) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            java.lang.Object this$field = this.getField();
            java.lang.Object other$field = other.getField();
            if (this$field == null ? other$field != null : !this$field.equals(other$field)) return false;
            java.lang.Object this$message = this.getMessage();
            java.lang.Object other$message = other.getMessage();
            if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
            return true;
        }

        protected boolean canEqual(java.lang.Object other) {
            return other instanceof ApiErrorResponse.ValidationError;
        }

        @java.lang.Override
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            java.lang.Object $field = this.getField();
            result = result * PRIME + ($field == null ? 43 : $field.hashCode());
            java.lang.Object $message = this.getMessage();
            result = result * PRIME + ($message == null ? 43 : $message.hashCode());
            return result;
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ApiErrorResponse.ValidationError(field=" + this.getField() + ", message=" + this.getMessage() + ")";
        }

        public ValidationError() {
        }

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }


    public static class ApiErrorResponseBuilder {
        private Instant timestamp;
        private int status;
        private String errorCode;
        private String message;
        private String path;
        private List<ValidationError> validationErrors;
        private String traceId;

        ApiErrorResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ApiErrorResponse.ApiErrorResponseBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ApiErrorResponse.ApiErrorResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ApiErrorResponse.ApiErrorResponseBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ApiErrorResponse.ApiErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ApiErrorResponse.ApiErrorResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ApiErrorResponse.ApiErrorResponseBuilder validationErrors(List<ValidationError> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ApiErrorResponse.ApiErrorResponseBuilder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public ApiErrorResponse build() {
            return new ApiErrorResponse(this.timestamp, this.status, this.errorCode, this.message, this.path, this.validationErrors, this.traceId);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ApiErrorResponse.ApiErrorResponseBuilder(timestamp=" + this.timestamp + ", status=" + this.status + ", errorCode=" + this.errorCode + ", message=" + this.message + ", path=" + this.path + ", validationErrors=" + this.validationErrors + ", traceId=" + this.traceId + ")";
        }
    }

    public static ApiErrorResponse.ApiErrorResponseBuilder builder() {
        return new ApiErrorResponse.ApiErrorResponseBuilder();
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public int getStatus() {
        return this.status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPath() {
        return this.path;
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ApiErrorResponse)) return false;
        ApiErrorResponse other = (ApiErrorResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getStatus() != other.getStatus()) return false;
        java.lang.Object this$timestamp = this.getTimestamp();
        java.lang.Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        java.lang.Object this$errorCode = this.getErrorCode();
        java.lang.Object other$errorCode = other.getErrorCode();
        if (this$errorCode == null ? other$errorCode != null : !this$errorCode.equals(other$errorCode)) return false;
        java.lang.Object this$message = this.getMessage();
        java.lang.Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        java.lang.Object this$path = this.getPath();
        java.lang.Object other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
        java.lang.Object this$validationErrors = this.getValidationErrors();
        java.lang.Object other$validationErrors = other.getValidationErrors();
        if (this$validationErrors == null ? other$validationErrors != null : !this$validationErrors.equals(other$validationErrors)) return false;
        java.lang.Object this$traceId = this.getTraceId();
        java.lang.Object other$traceId = other.getTraceId();
        if (this$traceId == null ? other$traceId != null : !this$traceId.equals(other$traceId)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof ApiErrorResponse;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getStatus();
        java.lang.Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        java.lang.Object $errorCode = this.getErrorCode();
        result = result * PRIME + ($errorCode == null ? 43 : $errorCode.hashCode());
        java.lang.Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        java.lang.Object $path = this.getPath();
        result = result * PRIME + ($path == null ? 43 : $path.hashCode());
        java.lang.Object $validationErrors = this.getValidationErrors();
        result = result * PRIME + ($validationErrors == null ? 43 : $validationErrors.hashCode());
        java.lang.Object $traceId = this.getTraceId();
        result = result * PRIME + ($traceId == null ? 43 : $traceId.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ApiErrorResponse(timestamp=" + this.getTimestamp() + ", status=" + this.getStatus() + ", errorCode=" + this.getErrorCode() + ", message=" + this.getMessage() + ", path=" + this.getPath() + ", validationErrors=" + this.getValidationErrors() + ", traceId=" + this.getTraceId() + ")";
    }

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(Instant timestamp, int status, String errorCode, String message, String path, List<ValidationError> validationErrors, String traceId) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
        this.traceId = traceId;
    }
}
