package com._up.megastore.security.utils;

public class Endpoints {

    public static String AUTH_ENDPOINTS = "/auth/**";

    public static String ERROR_ENDPOINTS = "/error/**";

    public static String ANY_USER_ENDPOINTS = "/api/v1/users/**";

    public static String[] DELETED_ENTITIES_ENDPOINTS = {
            "/api/v1/products/*/deleted",
            "/api/v1/sizes/*/deleted",
            "/api/v1/categories/*/deleted",
    };

    public static String[] PUBLIC_INFORMATION_ENDPOINTS = {
            "/api/v1/products",
            "/api/v1/products/*",
            "/api/v1/categories",
            "/api/v1/categories/*",
            "/api/v1/sizes",
            "/api/v1/sizes/*",
    };

    public static String[] SAVE_INFORMATION_ENDPOINTS = {
            "/api/v1/products",
            "/api/v1/products/*/restore",
            "/api/v1/categories",
            "/api/v1/categories/*/restore",
            "/api/v1/sizes",
            "/api/v1/sizes/*/restore",
    };

    public static String[] UPDATE_INFORMATION_ENDPOINTS = {
            "/api/v1/products/*",
            "/api/v1/categories/*",
            "/api/v1/sizes/*",
    };

    public static String[] DELETE_INFORMATION_ENDPOINTS = {
            "/api/v1/products/*",
            "/api/v1/categories/*",
            "/api/v1/sizes/*",
    };

    public static String[] USER_ORDER_MODIFICATION_ENDPOINTS = {
            "/api/v1/orders",
            "/api/v1/orders/*/cancel",
    };

    public static String[] ADMIN_ORDER_MODIFICATON_ENDPOINTS = {
            "/api/v1/orders/*/finish",
            "/api/v1/orders/*/mark-in-delivery",
            "/api/v1/orders/*/delivered",
    };

    public static String GET_ALL_ORDERS = "/api/v1/orders";

    public static String GET_ONE_ORDER = "/api/v1/orders/*";

}