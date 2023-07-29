package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.type.ContactType;
import net.cavitos.workshop.domain.model.type.ProductType;
import net.cavitos.workshop.domain.model.web.Product;
import net.cavitos.workshop.model.entity.ProductEntity;
import net.cavitos.workshop.model.generator.TimeBasedGenerator;
import net.cavitos.workshop.model.repository.ProductRepository;
import net.cavitos.workshop.sequence.domain.SequenceType;
import net.cavitos.workshop.sequence.provider.SequenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import static net.cavitos.workshop.domain.model.status.ActiveStatus.ACTIVE;
import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final SequenceProvider sequenceProvider;

    public ProductService(final ProductRepository productRepository,
                          final SequenceProvider sequenceProvider) {

        this.productRepository = productRepository;
        this.sequenceProvider = sequenceProvider;
    }

    public Page<ProductEntity> search(final String tenant,
                                      final String type,
                                      final String text,
                                      final int active,
                                      final int page,
                                      final int size) {

        LOGGER.info("Retrieve all products for tenant={} with text={}, type={},active={}", tenant, text, type, active);

        final var pageable = PageRequest.of(page, size);

        return productRepository.search("%" + text + "%", type, active, tenant, pageable);
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
                .id(TimeBasedGenerator.generateTimeBasedId())
                .type(buildTypeFor(product.getType()))
                .name(product.getName())
                .code(sequenceProvider.calculateNext(SequenceType.PRODUCT))
                .description(product.getDescription())
                .minimalQuantity(product.getMinimalQuantity())
                .tenant(tenant)
                .active(ACTIVE.value())
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

        var productType = ProductType.valueOf(product.getType())
                .value();

        var code = entity.getCode();
        if (!productType.equalsIgnoreCase(product.getType())) {

            code = calculateCode(product.getType());
        }

        final var active = ActiveStatus.valueOf(product.getActive())
                        .value();

        entity.setActive(active);
        entity.setName(product.getName());
        entity.setCode(code);
        entity.setDescription(product.getDescription());
        entity.setType(buildTypeFor(product.getType()));
        entity.setMinimalQuantity(product.getMinimalQuantity());
        entity.setUpdated(Instant.now());

        productRepository.save(entity);

        return entity;
    }

    // ----------------------------------------------------------------------------------------------------

    private void verifyExistingCodeAndTypeForTenant(final String tenant, final Product product) {

        final var existingProductHolder = productRepository.findByCodeEqualsIgnoreCaseAndTenant(product.getCode(),
                tenant);

        if (existingProductHolder.isPresent()) {

            LOGGER.error("Product with code={}, type={} already exists for tenant={}", product.getCode(), product.getType(), tenant);
            throw createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Product already exists");
        }
    }

    private String buildTypeFor(final String value) {

        return ProductType.valueOf(value)
                .value();
    }

    private String calculateCode(final String type) {

        final var productType = ProductType.valueOf(type);

        return switch (productType) {
            case PRODUCT -> sequenceProvider.calculateNext(SequenceType.PRODUCT);
            case SERVICE -> sequenceProvider.calculateNext(SequenceType.SERVICE);
            default -> sequenceProvider.calculateNext(SequenceType.UNKNOWN);
        };
    }

}
