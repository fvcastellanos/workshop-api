package net.cavitos.workshop.web.controller.ui;

import jakarta.servlet.http.HttpServletRequest;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.CarBrandService;
import net.cavitos.workshop.web.controller.Route;
import net.cavitos.workshop.web.controller.ui.model.SearchModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String index(@RequestParam(defaultValue = "") final String text,
                        @RequestParam(defaultValue = "1") final int active,
                        @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                        @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                        final HttpServletRequest request,
                        final Model model) {

        var carBrandEntityPage = carBrandService.getAllByTenant("resta", active, text, page, size);

        final var brands = carBrandEntityPage.stream()
                .collect(Collectors.toList());

        final var searchModel = new SearchModel();
        searchModel.setText(text);
        searchModel.setActive(active);
        searchModel.setPage(page);
        searchModel.setSize(size);
//        searchModel.setTotalElements(carBrandEntityPage.getTotalElements());
//        searchModel.setTotalPages(carBrandEntityPage.getTotalPages());
        searchModel.setTotalElements(1000);
        searchModel.setTotalPages(40);

        model.addAllAttributes(buildCommonUIAttributes(request));
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("items", brands);

        return "car-brands/home";
    }

    @PostMapping
    public String search(final HttpServletRequest request,
                         final SearchModel searchModel,
                         final Model model) {

        model.addAllAttributes(buildCommonUIAttributes(request));
//        model.addAttribute("searchRequest", buildSearchRequest());

        LOGGER.info("model: {}", model);

        return "car-brands/home";
    }
}
