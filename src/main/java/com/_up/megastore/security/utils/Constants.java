package com._up.megastore.security.utils;

public class Constants {
    public static String BEARER = "Bearer ";
    public static String BASIC = "Basic ";
    public static String JWT_EXCEPTION_DEFAULT_MESSAGE = "Invalid JWT. Please contact an administrator";
    public static String EXPIRED_JWT_EXCEPTION_MESSAGE = "Session expired. Please log in again";
    public static String SIGNATURE_EXCEPTION_MESSAGE = "Invalid token. Please contact an administrator";
    public static String CLAIMS_JWT_EXCEPTION_MESSAGE = "Token claims are invalid. Please contact an administrator";
    public static String BASIC_AUTH_EXCEPTION_MESSAGE = "Username or password are incorrect";
}