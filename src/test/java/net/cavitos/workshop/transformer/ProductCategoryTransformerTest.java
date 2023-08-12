package net.cavitos.workshop.transformer;

import net.cavitos.workshop.model.entity.ProductCategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static net.cavitos.workshop.common.assertion.CommonAssertions.assertHateoasLink;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoryTransformerTest {

    @Test
    void testProductCategoryTransform() {

        final var entity = buildEntity();

        final var productCategory = ProductCategoryTransformer.toWeb(entity);

        assertThat(productCategory)
                .hasFieldOrPropertyWithValue("code", "code")
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("active", "ACTIVE")
                ;

        assertHateoasLink(productCategory, "self", "/product-categories/123");
    }

    // ------------------------------------------------------------------------------------------

    private ProductCategoryEntity buildEntity() {

        return ProductCategoryEntity.builder()
                .id("123")
                .code("code")
                .name("name")
                .description("description")
                .active(1)
                .build();
    }
}
