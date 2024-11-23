package com._up.megastore.security.utils;

public class Constants {

    private Constants() {}

    public static final String BEARER = "Bearer ";
    public static final String BASIC = "Basic ";
    public static final String JWT_EXCEPTION_DEFAULT_MESSAGE = "Invalid JWT. Please contact an administrator";
    public static final String EXPIRED_JWT_EXCEPTION_MESSAGE = "Session expired. Please log in again";
    public static final String SIGNATURE_EXCEPTION_MESSAGE = "Invalid token. Please contact an administrator";
    public static final String CLAIMS_JWT_EXCEPTION_MESSAGE = "Token claims are invalid. Please contact an administrator";
    public static final String BASIC_AUTH_EXCEPTION_MESSAGE = "Username or password are incorrect";
}