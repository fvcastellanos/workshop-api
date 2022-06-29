package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.security.domain.UserProfile;
import net.cavitos.workshop.security.service.UserService;

import java.security.Principal;

public abstract class BaseController {

    private final UserService userService;

    protected static final String DEFAULT_SIZE = "20";
    protected static final String DEFAULT_PAGE = "0";

    protected static final String DEFAULT_TENANT = "default";

    public BaseController(UserService userService) {

        this.userService = userService;
    }

    protected String buildSelf(final String baseRoute, final String tenantId, final String self) {

        return baseRoute +
                "/" +
                tenantId +
                "/" +
                self;
    }

    protected UserProfile getUserProfile(Principal principal) {

        return userService.getUserProfile(principal.getName());
    }

    protected String getUserTenant(Principal principal) {

        final var userProfile = getUserProfile(principal);

        return userProfile.getTenant();
    }
}
