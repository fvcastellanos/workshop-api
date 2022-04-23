package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.web.Product;
import net.cavitos.workshop.model.entity.ProductEntity;
import net.cavitos.workshop.model.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    public Page<ProductEntity> searchBy(final String tenant,
                                        final String type,
                                        final String code,
                                        final String name,
                                        final int active,
                                        final int page,
                                        final int size) {

        LOGGER.info("Retrieve all products for tenant={} with code={}, type={} name={}, active={}", tenant, code, type,
                name, active);

        final var pageable = PageRequest.of(page, size);
        return productRepository.findByTypeAndCodeContainsAndNameContainsAndActiveAndTenant(type, code, name, active,
                tenant, pageable);
    }

    public ProductEntity findById(final String tenant, final String id) {

        LOGGER.info("Get product_id={} for tenant={}", id, tenant);

        final var entity = productRepository.findById(id)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Product not found"));

        if (!entity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("product_id={} is not associated with tenant={}", id, tenant);
            throw createBusinessException(HttpStatus.NOT_FOUND, "Product not found");
        }

        return entity;
    }

    public ProductEntity add(final String tenant, final Product product) {

        LOGGER.info("Add a new product with name={} for tenant={}", product.getName(), tenant);

        verifyExistingCodeAndTypeForTenant(tenant, product);

        var entity = ProductEntity.builder()
                .id(UUID.randomUUID().toString())
                .type(product.getType())
                .name(product.getName())
                .code(product.getCode())
                .description(product.getDescription())
                .minimalQuantity(product.getMinimalQuantity())
                .tenant(tenant)
                .active(1)
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        productRepository.save(entity);

        return entity;
    }

    public ProductEntity update(final String tenant, final String id, final Product product) {

        LOGGER.info("Trying to update product_id={} for tenant={}", id, tenant);

        final var entity = productRepository.findById(id)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Product not found"));

        if (!entity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("product_id={} is not associated with tenant={}", id, tenant);
            throw createBusinessException(HttpStatus.NOT_FOUND, "Product not found");
        }

        if (!entity.getCode().equalsIgnoreCase(product.getCode()) || !entity.getType().equalsIgnoreCase(product.getType())) {

            verifyExistingCodeAndTypeForTenant(tenant, product);
        }

        entity.setActive(product.getActive());
        entity.setName(product.getName());
        entity.setCode(product.getCode());
        entity.setDescription(product.getDescription());
        entity.setType(product.getType());
        entity.setMinimalQuantity(product.getMinimalQuantity());
        entity.setUpdated(Instant.now());

        productRepository.save(entity);

        return entity;
    }

    // ----------------------------------------------------------------------------------------------------

    private void verifyExistingCodeAndTypeForTenant(final String tenant, final Product product) {

        final var existingProductHolder = productRepository.findByCodeEqualsIgnoreCaseAndTypeAndTenant(product.getCode(),
                product.getType(), tenant);

        if (existingProductHolder.isPresent()) {

            LOGGER.error("Product with code={}, type={} already exists for tenant={}", product.getCode(), product.getType(), tenant);
            throw createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Product already exists");
        }
    }
}
