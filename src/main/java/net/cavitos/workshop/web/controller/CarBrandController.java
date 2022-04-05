package net.cavitos.workshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.cavitos.workshop.model.entity.CarBrand;
import net.cavitos.workshop.service.CarBrandService;

@RestController
@RequestMapping("/car-brands")
public class CarBrandController extends BaseController {

    private final CarBrandService carBrandService;

    @Autowired
    public CarBrandController(final CarBrandService carBrandService) {
        
        this.carBrandService = carBrandService;
    }

    @GetMapping
    public ResponseEntity<Page<CarBrand>> getByTenant(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE) final int page,
                                                      @RequestParam(name = "size", defaultValue = DEFAULT_SIZE) final int size) {

        final var carBrands = carBrandService.getAll(page, size);

        return new ResponseEntity<>(carBrands, HttpStatus.OK);
    }
    
}
