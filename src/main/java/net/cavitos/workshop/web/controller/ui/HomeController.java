package net.cavitos.workshop.web.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(final Model model) {

        model.addAllAttributes(buildAttributeMap());

        return "home";
    }

    private Map<String, String> buildAttributeMap() {

        return Map.of(
                "basePath", "/v1/workshop"
        );
    }

}
