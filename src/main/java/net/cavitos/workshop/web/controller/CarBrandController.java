package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.domain.model.web.CarBrand;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.CarBrandService;
import net.cavitos.workshop.transformer.CarBrandTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.CAR_BRANDS_RESOURCE;

@RestController
@RequestMapping(CAR_BRANDS_RESOURCE)
public class CarBrandController extends BaseController {

    private final CarBrandService carBrandService;

    @Autowired
    public CarBrandController(final CarBrandService carBrandService, final UserService userService) {

        super(userService);
        this.carBrandService = carBrandService;
    }

    @GetMapping
    public ResponseEntity<Page<CarBrand>> getByTenant(@RequestParam(defaultValue = "1") final int active,
                                                      @RequestParam(defaultValue = "") final String name,
                                                      @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                      @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                      final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var carBrandEntityPage = carBrandService.getAllByTenant(tenant, active, name, page, size);

        final var brands = carBrandEntityPage.stream()
                .map(CarBrandTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(brands, Pageable.ofSize(size),
                carBrandEntityPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarBrand> getById(@PathVariable @NotBlank final String id,
                                            final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var carBrandEntity = carBrandService.getById(tenant, id);
        final var response = CarBrandTransformer.toWeb(carBrandEntity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarBrand> add(@Valid @RequestBody final CarBrand carBrand,
                                        final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = carBrandService.add(tenant, carBrand);
        final var response = CarBrandTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarBrand> update(@PathVariable @NotBlank final String id,
                                           @RequestBody @Valid final CarBrand carBrand,
                                           final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = carBrandService.update(tenant, id, carBrand);
        final var response = CarBrandTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
