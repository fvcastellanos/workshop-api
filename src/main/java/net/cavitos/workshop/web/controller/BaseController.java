package net.cavitos.workshop.web.controller;

import org.springframework.validation.BeanPropertyBindingResult;

public abstract class BaseController {
 
    protected static final String DEFAULT_SIZE = "20";
    protected static final String DEFAULT_PAGE = "0";

    protected BeanPropertyBindingResult buildErrorObject(final Object object) {

        return new BeanPropertyBindingResult(object, object.getClass().getName());
    }    

    protected String buildSelf(final String baseRoute, final String tenantId, final String self) {

        return new StringBuilder()
            .append(baseRoute)
            .append("/")
            .append(tenantId)
            .append("/")
            .append(self)
            .toString();
    }    
}
