package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.web.Product;
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

        final var active = ActiveStatus.of(productEntity.getActive())
                .name();

        final var product = new Product();
        product.setCode(productEntity.getCode());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setType(productEntity.getType());
        product.setActive(active);
        product.setMinimalQuantity(productEntity.getMinimalQuantity());

        product.add(selfLink);

        return product;
    }
}
