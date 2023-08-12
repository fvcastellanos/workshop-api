package net.cavitos.workshop.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import net.cavitos.workshop.domain.model.web.ProductCategory;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.ProductCategoryService;
import net.cavitos.workshop.transformer.ProductCategoryTransformer;
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

import java.security.Principal;
import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.PRODUCT_CATEGORIES_RESOURCE;

@RestController
@RequestMapping(PRODUCT_CATEGORIES_RESOURCE)
public class ProductCategoryController extends BaseController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(final UserService userService,
                                     final ProductCategoryService productCategoryService) {
        super(userService);
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductCategory>> search(@RequestParam(defaultValue = "") final String text,
                                                        @RequestParam(defaultValue = "1") final int active,
                                                        @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                        @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                        final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var productCategoryPage = productCategoryService.search(tenant, active, text, page, size);

        final var productCategories = productCategoryPage.stream()
                .map(ProductCategoryTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(productCategories, Pageable.ofSize(size),
                productCategoryPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getById(@PathVariable @NotEmpty final String id,
                                                   final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var productCategory = productCategoryService.findById(tenant, id);
        final var entity = ProductCategoryTransformer.toWeb(productCategory);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductCategory> add(@RequestBody @Valid final ProductCategory productCategory,
                                               final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var entity = productCategoryService.add(tenant, productCategory);
        final var response = ProductCategoryTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> update(@PathVariable @NotEmpty final String id,
                                                  @RequestBody @Valid final  ProductCategory productCategory,
                                                  final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var entity = productCategoryService.update(tenant, id, productCategory);
        final var response = ProductCategoryTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
