package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.model.entity.CarBrandEntity;
import net.cavitos.workshop.service.CarBrandService;
import net.cavitos.workshop.domain.model.web.brand.CarBrand;
import net.cavitos.workshop.domain.model.web.response.LinkResponse;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.transformer.CarBrandTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static net.cavitos.workshop.web.controller.Route.CAR_BRANDS_RESOURCE;

@RestController
@RequestMapping(CAR_BRANDS_RESOURCE)
public class CarBrandController extends BaseController {

    private final CarBrandService carBrandService;

    @Autowired
    public CarBrandController(final CarBrandService carBrandService) {

        this.carBrandService = carBrandService;
    }

    @GetMapping("/{tenant}")
    public ResponseEntity<Page<CarBrandEntity>> getByTenant(@PathVariable @NotBlank @Size(max = 50) final String tenant,
                                                            @RequestParam(defaultValue = "1") final int active,
                                                            @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                            @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var carBrands = carBrandService.getAllByTenant(tenant, active, page, size);

        return new ResponseEntity<>(carBrands, HttpStatus.OK);
    }

    @PostMapping("/{tenant}")
    public ResponseEntity<ResourceResponse<CarBrand>> add(@PathVariable @NotBlank @Size(max = 50) final String tenant,
                                                          @Valid @RequestBody final CarBrand carBrand) {

        var entity = carBrandService.add(tenant, carBrand);
        var response = CarBrandTransformer.toWeb(tenant, entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
}
