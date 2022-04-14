package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.domain.model.web.CarLine;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.model.entity.CarLineEntity;
import net.cavitos.workshop.service.CarLineService;
import net.cavitos.workshop.transformer.CarLineTransformer;
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
import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.CAR_BRANDS_RESOURCE;
import static net.cavitos.workshop.web.controller.Route.CAR_LINES_RESOURCE;

@RestController
@RequestMapping(CAR_BRANDS_RESOURCE + "/{carBrandId}" + CAR_LINES_RESOURCE)
public class CarLineController extends BaseController {

    private final CarLineService carLineService;

    @Autowired
    public CarLineController(final CarLineService carLineService) {

        this.carLineService = carLineService;
    }

    @GetMapping
    public ResponseEntity<Page<ResourceResponse<CarLine>>> getByTenant(@PathVariable @NotBlank final String carBrandId,
                                                                       @RequestParam(defaultValue = "1") final int active,
                                                                       @RequestParam(defaultValue = "") final String name,
                                                                       @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                                       @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var carLinePage = carLineService.findAll(DEFAULT_TENANT, carBrandId, active, name,
                page, size);

        final var carLines = carLinePage.stream()
                .map(CarLineTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(carLines, Pageable.ofSize(size), carLinePage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse<CarLine>> getById(@PathVariable @NotBlank final String carBrandId,
                                                             @PathVariable @NotBlank final String id) {

        final var carLineEntity = carLineService.findById(DEFAULT_TENANT, carBrandId, id);
        final var resource = CarLineTransformer.toWeb(carLineEntity);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResourceResponse<CarLine>> add(@PathVariable @NotBlank final String carBrandId,
                                                         @Valid @RequestBody final CarLine carLine) {

        final var entity = carLineService.add(DEFAULT_TENANT, carBrandId, carLine);
        final var resource = CarLineTransformer.toWeb(entity);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse<CarLine>> update(@PathVariable @NotBlank final String carBrandId,
                                                            @PathVariable @NotBlank final String id,
                                                            @Valid @RequestBody final CarLine carLine) {

        final var entity = carLineService.update(DEFAULT_TENANT, carBrandId, id, carLine);
        final var resource = CarLineTransformer.toWeb(entity);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }
}
