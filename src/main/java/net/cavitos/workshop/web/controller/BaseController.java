package net.cavitos.workshop.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.cavitos.workshop.security.domain.UserProfile;
import net.cavitos.workshop.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Map;

public abstract class BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @ModelAttribute
    public void addAttributes(final Model model,
                              final HttpServletRequest request) {

        final var csrfToken = (CsrfToken) request.getAttribute("_csrf");

        final var commonAttributes = Map.of(
                "servletPath", basePath,
                "uiHome", basePath + Route.UI_HOME,
                "csrfToken", csrfToken
        );

        model.addAllAttributes(commonAttributes);

        logger.info("Added common attributes to model");
    }

    protected Map<String, Object> buildCommonUIAttributes(HttpServletRequest request) {

        final var csrfToken = (CsrfToken) request.getAttribute("_csrf");

        return Map.of(
                "servletPath", basePath,
                "uiHome", basePath + Route.UI_HOME,
                "csrfToken", csrfToken
        );
    }

}
