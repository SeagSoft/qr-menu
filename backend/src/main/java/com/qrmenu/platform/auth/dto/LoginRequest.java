package com.qrmenu.platform.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;


    public static class LoginRequestBuilder {
        private String email;
        private String password;

        LoginRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public LoginRequest.LoginRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LoginRequest.LoginRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(this.email, this.password);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "LoginRequest.LoginRequestBuilder(email=" + this.email + ", password=" + this.password + ")";
        }
    }

    public static LoginRequest.LoginRequestBuilder builder() {
        return new LoginRequest.LoginRequestBuilder();
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LoginRequest)) return false;
        LoginRequest other = (LoginRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$email = this.getEmail();
        java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        java.lang.Object this$password = this.getPassword();
        java.lang.Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof LoginRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        java.lang.Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "LoginRequest(email=" + this.getEmail() + ", password=" + this.getPassword() + ")";
    }

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
