package com._up.megastore.security.utils;

public class Endpoints {

    private Endpoints() {}

    public static final String AUTH_ENDPOINTS = "/auth/**";
    public static final String ERROR_ENDPOINTS = "/error/**";
    public static final String ANY_USER_ENDPOINTS = "/api/v1/users/**";
    public static final String ANY_PRODUCTS_ENDPOINT = "/api/v1/products/*";
    public static final String ANY_CATEGORIES_ENDPOINT = "/api/v1/categories/*";
    public static final String ANY_SIZES_ENDPOINT = "/api/v1/sizes/*";
    public static final String GET_ALL_ORDERS = "/api/v1/orders";
    public static final String GET_ONE_ORDER = "/api/v1/orders/*";

    public static final String[] DELETED_ENTITIES_ENDPOINTS = {
            "/api/v1/products/*/deleted",
            "/api/v1/sizes/*/deleted",
            "/api/v1/categories/*/deleted",
    };

    public static final String[] PUBLIC_INFORMATION_ENDPOINTS = {
            "/api/v1/products",
            ANY_PRODUCTS_ENDPOINT,
            "/api/v1/products/*/variants",
            "/api/v1/categories",
            ANY_CATEGORIES_ENDPOINT,
            "/api/v1/sizes",
            ANY_SIZES_ENDPOINT,
    };

    public static final String[] SAVE_INFORMATION_ENDPOINTS = {
            "/api/v1/products",
            "/api/v1/products/*/restore",
            "/api/v1/categories",
            "/api/v1/categories/*/restore",
            "/api/v1/sizes",
            "/api/v1/sizes/*/restore",
    };

    public static final String[] UPDATE_INFORMATION_ENDPOINTS = {
            ANY_PRODUCTS_ENDPOINT,
            ANY_CATEGORIES_ENDPOINT,
            ANY_SIZES_ENDPOINT,
    };

    public static final String[] DELETE_INFORMATION_ENDPOINTS = {
            ANY_PRODUCTS_ENDPOINT,
            ANY_CATEGORIES_ENDPOINT,
            ANY_SIZES_ENDPOINT,
    };

    public static final String[] USER_ORDER_MODIFICATION_ENDPOINTS = {
            GET_ALL_ORDERS,
            "/api/v1/orders/*/cancel",
    };

    public static final String[] ADMIN_ORDER_MODIFICATON_ENDPOINTS = {
            "/api/v1/orders/*/finish",
            "/api/v1/orders/*/mark-in-delivery",
            "/api/v1/orders/*/delivered",
    };
    
}
