package net.cavitos.workshop.web.controller.ui;

import jakarta.servlet.http.HttpServletRequest;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.web.controller.Route;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Route.UI_HOME)
public class HomeControllerUI extends ControllerUIBase {

    public HomeControllerUI(UserService userService) {
        super(userService);
    }

    @GetMapping
    public String home(HttpServletRequest request, final Model model) {

        model.addAllAttributes(buildCommonUIAttributes(request));

        return "home";
    }
}
