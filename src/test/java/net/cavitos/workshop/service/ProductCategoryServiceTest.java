package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.web.ProductCategory;
import net.cavitos.workshop.model.entity.ProductCategoryEntity;
import net.cavitos.workshop.model.repository.ProductCategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static net.cavitos.workshop.common.assertion.CommonAssertions.assertBusinessException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductCategoryServiceTest {

    private static final String TENANT = "tenant";

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductCategoryService productCategoryService;

    @AfterEach
    void tearDown() {

        verifyNoMoreInteractions(productCategoryRepository);
    }

    @Test
    void testWhenFindByIdNotFound() {

        when(productCategoryRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertBusinessException(() -> productCategoryService.findById(TENANT, "id"),
                "Product Category not found",
                HttpStatus.NOT_FOUND);

        verify(productCategoryRepository).findById(anyString());
    }

    @Test
    void testWhenFindByIdAndTenantIsDifferent() {

        final var entity = buildProductCategoryEntity();

        when(productCategoryRepository.findById(anyString()))
                .thenReturn(Optional.of(entity));

        assertBusinessException(() -> productCategoryService.findById("test", "id"),
                "Product Category not found",
                HttpStatus.NOT_FOUND);

        verify(productCategoryRepository).findById(anyString());
    }

    @Test
    void testFindById() {

        final var entity = buildProductCategoryEntity();

        when(productCategoryRepository.findById(anyString()))
                .thenReturn(Optional.of(entity));

        final var productCategoryEntity = productCategoryService.findById(TENANT, "id");

        assertThat(productCategoryEntity)
                .isNotNull()
                        .hasFieldOrPropertyWithValue("id", "id")
                        .hasFieldOrPropertyWithValue("name", "name");

        verify(productCategoryRepository).findById(anyString());
    }

    @Test
    void testAddWhenNameAlreadyExists() {

        final var productCategory = buildProductCategory();

        when(productCategoryRepository.findByTenantAndName(TENANT, productCategory.getName()))
                .thenReturn(Optional.of(buildProductCategoryEntity()));

        assertBusinessException(() -> productCategoryService.add(TENANT, productCategory),
                "Product Category with name: name already exists",
                HttpStatus.UNPROCESSABLE_ENTITY);

        verify(productCategoryRepository).findByTenantAndName(TENANT, productCategory.getName());
    }

    @Test
    void testAddWhenCodeAlreadyExists() {

        final var productCategory = buildProductCategory();

        when(productCategoryRepository.findByTenantAndName(TENANT, productCategory.getName()))
                .thenReturn(Optional.empty());

        when(productCategoryRepository.findByTenantAndCode(TENANT, productCategory.getCode()))
                .thenReturn(Optional.of(buildProductCategoryEntity()));

        assertBusinessException(() -> productCategoryService.add(TENANT, productCategory),
                "Product Category with code: code already exists",
                HttpStatus.UNPROCESSABLE_ENTITY);

        verify(productCategoryRepository).findByTenantAndName(TENANT, productCategory.getName());
        verify(productCategoryRepository).findByTenantAndCode(TENANT, productCategory.getCode());
    }

    @Test
    void testAddProductCategory() {

        final var productCategory = buildProductCategory();

        when(productCategoryRepository.findByTenantAndName(TENANT, productCategory.getName()))
                .thenReturn(Optional.empty());

        when(productCategoryRepository.findByTenantAndCode(TENANT, productCategory.getCode()))
                .thenReturn(Optional.empty());

        when(productCategoryRepository.save(any(ProductCategoryEntity.class)))
                .thenReturn(buildProductCategoryEntity());

        final var entity = productCategoryService.add(TENANT, productCategory);

        assertThat(entity)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", "id")
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("tenant", "tenant")
                ;

        verify(productCategoryRepository).findByTenantAndName(TENANT, productCategory.getName());
        verify(productCategoryRepository).findByTenantAndCode(TENANT, productCategory.getCode());
        verify(productCategoryRepository).save(any(ProductCategoryEntity.class));
    }

    // ----------------------------------------------------------------------------

    private ProductCategoryEntity buildProductCategoryEntity() {

        return ProductCategoryEntity.builder()
                .id("id")
                .name("name")
                .description("description")
                .tenant("tenant")
                .build();
    }

    private ProductCategory buildProductCategory() {

        final var productCategory = new ProductCategory();
        productCategory.setCode("code");
        productCategory.setName("name");
        productCategory.setDescription("description");

        return productCategory;
    }
}
