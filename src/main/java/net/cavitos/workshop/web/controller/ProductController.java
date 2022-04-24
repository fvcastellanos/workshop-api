package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.domain.model.web.Product;
import net.cavitos.workshop.service.ProductService;
import net.cavitos.workshop.transformer.ProductTransformer;
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
import javax.validation.constraints.NotEmpty;

import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.PRODUCTS_RESOURCE;

@RestController
@RequestMapping(PRODUCTS_RESOURCE)
public class ProductController extends BaseController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {

        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> search(@RequestParam(defaultValue = "") final String code,
                                                @RequestParam(defaultValue = "P") final String type,
                                                @RequestParam(defaultValue = "") final String name,
                                                @RequestParam(defaultValue = "1") final int active,
                                                @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) final int page) {

        final var productPage = productService.searchBy(DEFAULT_TENANT, type, code, name, active, page, size);

        final var products = productPage.stream()
                .map(ProductTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(products, Pageable.ofSize(size), productPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable @NotEmpty final String id) {

        final var productEntity = productService.findById(DEFAULT_TENANT, id);

        final var response = ProductTransformer.toWeb(productEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> add(@RequestBody @Valid final Product product) {

        final var entity = productService.add(DEFAULT_TENANT, product);

        final var response = ProductTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable @NotEmpty final String id,
                                          @RequestBody @Valid Product product) {

        final var entity = productService.update(DEFAULT_TENANT, id, product);

        final var response = ProductTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
