package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.security.domain.UserProfile;
import net.cavitos.workshop.security.service.UserService;
import org.springframework.beans.factory.annotation.Value;

import java.security.Principal;
import java.util.Map;

public abstract class BaseController {

    private final UserService userService;

    protected static final String DEFAULT_SIZE = "20";
    protected static final String DEFAULT_PAGE = "0";

    protected static final String DEFAULT_TENANT = "default";

    @Value("${server.servlet.context-path}")
    protected String basePath;

    public BaseController(UserService userService) {

        this.userService = userService;
    }

    protected UserProfile getUserProfile(Principal principal) {

        return userService.getUserProfile(principal.getName());
    }

    protected String getUserTenant(Principal principal) {

        final var userProfile = getUserProfile(principal);

        return userProfile.getTenant();
    }

    protected Map<String, Object> buildAttributeMap() {

        return Map.of(
                "basePath", basePath
        );
    }

}
