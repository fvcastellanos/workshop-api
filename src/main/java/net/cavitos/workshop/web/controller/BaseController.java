package net.cavitos.workshop.web.controller;

public abstract class BaseController {
 
    protected static final String DEFAULT_SIZE = "20";
    protected static final String DEFAULT_PAGE = "0";

    protected static final String DEFAULT_TENANT = "default";

    protected String buildSelf(final String baseRoute, final String tenantId, final String self) {

        return baseRoute +
                "/" +
                tenantId +
                "/" +
                self;
    }    
}
