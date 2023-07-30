package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.web.ProductCategory;
import net.cavitos.workshop.model.entity.ProductCategoryEntity;
import net.cavitos.workshop.web.controller.ProductCategoryController;
import net.cavitos.workshop.web.controller.ProductController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class ProductCategoryTransformer     {

    private ProductCategoryTransformer() {
    }

    public static ProductCategory toWeb(final ProductCategoryEntity entity) {

        final var selfLink = linkTo(methodOn(ProductCategoryController.class)
                .getById(entity.getId(), null))
                .withSelfRel();

        final var active = ActiveStatus.of(entity.getActive())
                .name();

        final var productCategory = new ProductCategory();
        productCategory.setCode(entity.getCode());
        productCategory.setName(entity.getName());
        productCategory.setDescription(entity.getDescription());
        productCategory.setActive(active);

        productCategory.add(selfLink);

        return  productCategory;
    }
}
