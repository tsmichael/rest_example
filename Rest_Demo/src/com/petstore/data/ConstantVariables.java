package com.petstore.data;

public final class ConstantVariables {

    private ConstantVariables() {
        throw new IllegalStateException("Utility class");
    }

    public static final String API_URL = System.getenv("API_URL");
    public static final int API_PORT = Integer.parseInt(System.getenv("API_PORT"));
    public static final String API_PATH = System.getenv("API_PATH");

    public static final String STORE_PATH = "/store/order";
    public static final String STORE_ID = "/{id}";
}
