package com._up.megastore.security.utils;

public class Endpoints {

    public static String[] WHITE_LISTED_URLS = {
            "/auth/**",
            "/error",
            "/api/v1/users/*/activate",
            "/api/v1/users/recover-password/**",
            "/api/v1/users/resend-activation-email",
            "/api/v1/users/send-new-activation-token",
    };

    public static String[] ALLOWED_TO_GET_BY_USERS_URLS = {
            "/api/v1/products/**",
            "/api/v1/categories/**",
            "/api/v1/sizes/**"
    };

    public static String[] ALLOWED_TO_POST_BY_USERS_URLS = {
            "/api/v1/orders"
    };

    public static String[] ALLOWED_TO_ADMINISTRATORS_URLS = {
            "/api/v1/**"
    };

}