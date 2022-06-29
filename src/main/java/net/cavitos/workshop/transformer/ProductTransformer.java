package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.type.ProductType;
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
                .getById(productEntity.getId(), null))
                .withSelfRel();

        final var active = ActiveStatus.of(productEntity.getActive())
                .name();

        final var type = ProductType.of(productEntity.getType())
                .name();

        final var product = new Product();
        product.setCode(productEntity.getCode());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setType(type);
        product.setActive(active);
        product.setMinimalQuantity(productEntity.getMinimalQuantity());

        product.add(selfLink);

        return product;
    }
}
