package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.Product;
import net.cavitos.workshop.domain.model.web.response.LinkResponse;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.model.entity.ProductEntity;
import net.cavitos.workshop.web.controller.ProductController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class ProductTransformer {

    private ProductTransformer() {
    }

    public static Product toWeb(final ProductEntity productEntity) {

        final var selfLink = linkTo(methodOn(ProductController.class)
                .getById(productEntity.getId()))
                .withSelfRel();

        final var product = new Product();
        product.setCode(productEntity.getCode());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setType(productEntity.getType());
        product.setActive(productEntity.getActive());
        product.setMinimalQuantity(productEntity.getMinimalQuantity());

        product.add(selfLink);

        return product;
    }
}
