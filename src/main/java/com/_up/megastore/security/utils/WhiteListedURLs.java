package com._up.megastore.security.utils;

public class WhiteListedURLs {

    public static String[] WHITE_LISTED_URLS = {
            "/auth/**",
            "/error",
            "/api/v1/users/*/activate"
    };

}