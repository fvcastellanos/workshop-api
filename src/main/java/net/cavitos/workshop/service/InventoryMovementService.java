package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.type.InventoryOperationType;
import net.cavitos.workshop.domain.model.web.InventoryMovement;
import net.cavitos.workshop.model.entity.InventoryEntity;
import net.cavitos.workshop.model.repository.InventoryRepository;
import net.cavitos.workshop.model.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;

@Service
public class InventoryMovementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryMovementService.class);

    private final InventoryRepository inventoryRepository;

    private final ProductRepository productRepository;

    public InventoryMovementService(final InventoryRepository inventoryRepository,
                                    final ProductRepository productRepository) {

        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public Page<InventoryEntity> search(final String operationType,
                                        final String operationTypeCode,
                                        final Instant initialDate,
                                        final Instant finalDate,
                                        final String tenant,
                                        final int page,
                                        final int size) {

        LOGGER.info("Search inventory movements from: {} to: {} for operationType: {} and tenant: {}",
                initialDate, finalDate, operationType, tenant);

        final var pageable = PageRequest.of(page, size);

        return inventoryRepository.search(operationType, operationTypeCode, initialDate, finalDate, tenant, pageable);
    }

    public InventoryEntity findById(final String id, final String tenant) {

        return inventoryRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Inventory movement not found"));
    }

    @Transactional
    public InventoryEntity add(final String tenant, final InventoryMovement movement) {

        LOGGER.info("Add inventory movement for tenant: {}", tenant);

        final var product = movement.getProduct();

        final var productEntity = productRepository.findByCodeEqualsIgnoreCaseAndTenant(product.getCode(), tenant)
                .orElseThrow(() -> createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Product not found"));

        final var total = (movement.getUnitPrice() * movement.getQuantity()) - movement.getDiscountAmount();

        final var entity = InventoryEntity.builder()
                .tenant(tenant)
                .productEntity(productEntity)
                .description(movement.getDescription())
                .quantity(movement.getQuantity())
                .unitPrice(movement.getUnitPrice())
                .discountAmount(movement.getDiscountAmount())
                .total(total)
                .created(Instant.now())
                .build();

        return inventoryRepository.save(entity);
    }

    @Transactional
    public void delete(final String id, final String tenant) {

        LOGGER.info("Delete inventory movement with id: {} for tenant: {}", id, tenant);

        final var movementHolder = inventoryRepository.findByIdAndTenant(id, tenant);

        if (movementHolder.isEmpty()) {

            LOGGER.info("Inventory movement with id: {} not found for tenant: {}", id, tenant);
            return;
        }

        inventoryRepository.delete(movementHolder.get());
    }
}
