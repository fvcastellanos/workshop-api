package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.domain.model.web.Provider;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.service.ProviderService;
import net.cavitos.workshop.transformer.ProviderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.PROVIDERS_RESOURCE;

@RestController
@RequestMapping(PROVIDERS_RESOURCE)
public class ProviderController extends BaseController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(final ProviderService providerService) {

        this.providerService = providerService;
    }

    @GetMapping
    public ResponseEntity<Page<ResourceResponse<Provider>>> getByTenant(@RequestParam(defaultValue = "") @Size(max = 50) final String code,
                                                                        @RequestParam(defaultValue = "") @Size(max = 150) final String name,
                                                                        @RequestParam(defaultValue = "1") final int active,
                                                                        @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                                        @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var providerPage = providerService.getAll(DEFAULT_TENANT, code, name, active, page, size);

        final var providers = providerPage.stream()
                .map(ProviderTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(providers, Pageable.ofSize(size), providerPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse<Provider>> getById(@PathVariable @NotEmpty final String id) {

        final var providerEntity = providerService.getById(DEFAULT_TENANT, id);

        final var resource = ProviderTransformer.toWeb(providerEntity);
        return  new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResourceResponse<Provider>> add(@RequestBody @Valid Provider provider) {

        final var entity = providerService.add(DEFAULT_TENANT, provider);

        final var resource = ProviderTransformer.toWeb(entity);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }
}
