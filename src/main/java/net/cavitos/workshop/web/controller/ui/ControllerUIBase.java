package net.cavitos.workshop.web.controller.ui;

import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.web.controller.BaseController;
import net.cavitos.workshop.web.controller.ui.model.SearchModel;

public abstract class ControllerUIBase extends BaseController {

    public ControllerUIBase(UserService userService) {
        super(userService);
    }

    protected SearchModel buildSearchModel() {

        final var searchRequest = new SearchModel();
        searchRequest.setText("");
        searchRequest.setActive(1);
        searchRequest.setCode("");
        searchRequest.setType("");
        searchRequest.setPage(1);
        searchRequest.setSize(25);

        return searchRequest;
    }
}
