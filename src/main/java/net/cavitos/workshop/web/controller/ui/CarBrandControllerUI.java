package net.cavitos.workshop.web.controller.ui;

import jakarta.servlet.http.HttpServletRequest;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.CarBrandService;
import net.cavitos.workshop.transformer.CarBrandTransformer;
import net.cavitos.workshop.web.controller.Route;
import net.cavitos.workshop.web.controller.ui.model.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping(Route.CAR_BRANDS_UI)
public class CarBrandControllerUI extends ControllerUIBase {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarBrandControllerUI.class);

    private final CarBrandService carBrandService;

    public CarBrandControllerUI(final UserService userService,
                                final CarBrandService carBrandService) {
        super(userService);
        this.carBrandService = carBrandService;
    }

    @GetMapping
    public String index(final HttpServletRequest request, final Model model) {

        var searchRequest = buildSearchRequest();
        var carBrandEntityPage = carBrandService.getAllByTenant("resta", searchRequest.getActive(),
                searchRequest.getText(), searchRequest.getPage(), searchRequest.getSize());

        final var brands = carBrandEntityPage.stream()
                .collect(Collectors.toList());

        model.addAllAttributes(buildCommonUIAttributes(request));
        model.addAttribute("searchRequest", searchRequest);
        model.addAttribute("items", brands);

        return "car-brands/home";
    }

    @PostMapping
    public String search(final HttpServletRequest request,
                         final SearchRequest searchRequest,
                         final Model model) {

        var carBrandEntityPage = carBrandService.getAllByTenant("resta", searchRequest.getActive(),
                searchRequest.getText(), searchRequest.getPage(), searchRequest.getSize());

        final var brands = carBrandEntityPage.stream()
                .collect(Collectors.toList());

        model.addAllAttributes(buildCommonUIAttributes(request));
        model.addAttribute("searchRequest", buildSearchRequest());
        model.addAttribute("items", brands);

        model.addAllAttributes(buildCommonUIAttributes(request));
//        model.addAttribute("searchRequest", buildSearchRequest());

        LOGGER.info("model: {}", model);

        return "car-brands/home";
    }
}
