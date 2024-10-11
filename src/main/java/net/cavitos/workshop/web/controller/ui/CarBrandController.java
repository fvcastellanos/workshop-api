package net.cavitos.workshop.web.controller.ui;

import net.cavitos.workshop.web.controller.Route;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(Route.CAR_BRANDS_RESOURCE)
public class CarBrandController {

    @GetMapping
    public String index(final Model model) {

        model.addAttribute("basePath", "/v1/workshop");

        return "carBrands/home";
    }

}
