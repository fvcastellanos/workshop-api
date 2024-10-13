package net.cavitos.workshop.web.controller;

public final class Route {

    // API Routes

    public static final String CAR_BRANDS_RESOURCE = "/car-brands";
    public static final String CAR_LINES_RESOURCE = "/lines";
    public static final String CONTACTS_RESOURCE = "/contacts";
    public static final String INVOICES_RESOURCE = "/invoices";
    public static final String PRODUCTS_RESOURCE = "/products";
    public static final String PRODUCT_CATEGORIES_RESOURCE = "/product-categories";
    public static final String WORK_ORDERS_RESOURCE = "/work-orders";
    public static final String INVENTORY_MOVEMENTS_RESOURCE = "/inventory-movements";
    public static final String INVENTORY_MOVEMENT_TYPES = "/inventory-movement-types";

    // UI Routes

    public static final String UI_HOME = "/ui";
    public static final String CAR_BRANDS_UI = UI_HOME + "/car-brands";

    private Route() {
    }
}
