package net.cavitos.workshop.web.controller.ui;

import jakarta.validation.Valid;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.CarBrandService;
import net.cavitos.workshop.web.controller.Route;
import net.cavitos.workshop.web.controller.ui.model.CarBrandModel;
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
    public String index(@RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                        @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                        @ModelAttribute("searchModel") SearchModel searchModel,
                        final Model model) {

        performSearch(searchModel, model);
        return "car-brands/home";
    }

    @PostMapping
    public String search(@ModelAttribute("searchModel") final SearchModel searchModel,
                         final Model model) {

        performSearch(searchModel, model);
        return "car-brands/home";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("carBrandModel") final CarBrandModel carBrandModel,
                      final Model model) {

        return "car-brands/add-update";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("carBrandModel") @Valid final CarBrandModel carBrandModel,
                       final Model model) {



        return "car-brands/add-update";
    }

    // -----------------------------------------------------------

    private void performSearch(final SearchModel searchModel,
                               final Model model) {

        var carBrandEntityPage = carBrandService.getAllByTenant("resta", searchModel.getActive(),
                searchModel.getText(), searchModel.getPage(), searchModel.getSize());

        final var brands = carBrandEntityPage.stream()
                .collect(Collectors.toList());

        searchModel.setTotalElements(carBrandEntityPage.getTotalElements());
        searchModel.setTotalPages(carBrandEntityPage.getTotalPages());

        model.addAttribute("items", brands);
    }

}
