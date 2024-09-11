package com._up.megastore.security.utils;

public class Endpoints {

    public static String[] WHITE_LISTED_URLS = {
            "/auth/**",
            "/error",
            "/api/v1/users/*/activate"
    };

    public static String[] ALLOWED_TO_GET_BY_USERS_URLS = {
            "/api/v1/products/**",
            "/api/v1/categories/**",
            "/api/v1/sizes/**"
    };

    public static String[] ALLOWED_TO_ADMINISTRATORS_URLS = {
            "/api/v1/**"
    };

}