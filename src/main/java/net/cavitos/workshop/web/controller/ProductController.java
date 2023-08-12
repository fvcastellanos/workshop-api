package net.cavitos.workshop.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import net.cavitos.workshop.domain.model.web.Product;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.ProductService;
import net.cavitos.workshop.transformer.ProductTransformer;
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

import static net.cavitos.workshop.web.controller.Route.PRODUCTS_RESOURCE;

@RestController
@RequestMapping(PRODUCTS_RESOURCE)
public class ProductController extends BaseController {

    private final ProductService productService;

    public ProductController(final ProductService productService, final UserService userService) {

        super(userService);
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> search(@RequestParam(defaultValue = "") final String text,
                                                @RequestParam(defaultValue = "P") final String type,
                                                @RequestParam(defaultValue = "%") final String category,
                                                @RequestParam(defaultValue = "1") final int active,
                                                @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var productPage = productService.search(tenant, type, category, text, active, page, size);

        final var products = productPage.stream()
                .map(ProductTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(products, Pageable.ofSize(size), productPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable @NotEmpty final String id,
                                           final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var productEntity = productService.findById(tenant, id);

        final var response = ProductTransformer.toWeb(productEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> add(@RequestBody @Valid final Product product,
                                       final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = productService.add(tenant, product);

        final var response = ProductTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable @NotEmpty final String id,
                                          @RequestBody @Valid final Product product,
                                          final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = productService.update(tenant, id, product);

        final var response = ProductTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
