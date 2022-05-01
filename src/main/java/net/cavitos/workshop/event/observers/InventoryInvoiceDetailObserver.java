package net.cavitos.workshop.event.observers;

import net.cavitos.workshop.event.model.EventType;
import net.cavitos.workshop.model.entity.InventoryEntity;
import net.cavitos.workshop.model.entity.InvoiceDetailEntity;
import net.cavitos.workshop.model.entity.ProductEntity;
import net.cavitos.workshop.model.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static net.cavitos.workshop.factory.DateTimeFactory.getUTCNow;

@Component
public class InventoryInvoiceDetailObserver implements InvoiceDetailObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryInvoiceDetailObserver.class);

    private static final String INVENTORY_PRODUCT_TYPE = "P";
    private static final String INPUT_OPERATION_TYPE = "I";
    private static final String MOVEMENT_DESCRIPTION = "Inventory automatic movement triggered by invoice detail event";

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryInvoiceDetailObserver(final InventoryRepository inventoryRepository) {

        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public void update(EventType eventType, InvoiceDetailEntity invoiceDetailEntity) {

        switch (eventType) {
            case ADD -> addInventoryMovement(invoiceDetailEntity);
            case UPDATE -> updateInventoryMovement(invoiceDetailEntity);
            case DELETE -> deleteInventoryMovement(invoiceDetailEntity);
        }
    }

    @Override
    public String getName() {

        return this.getClass()
                .getName();
    }

    // --------------------------------------------------------------------------------------------------

    @Transactional
    void addInventoryMovement(final InvoiceDetailEntity invoiceDetailEntity) {

        final var tenant = invoiceDetailEntity.getTenant();
        final var productEntity = invoiceDetailEntity.getProductEntity();

        LOGGER.info("Adding an inventory movement for tenant={} and invoice_detail_id={}", tenant, invoiceDetailEntity.getId());

        if (INVENTORY_PRODUCT_TYPE.equalsIgnoreCase(productEntity.getType())) {

            final var movementHolder = findInventoryMovement(productEntity, invoiceDetailEntity,
                    tenant);

            if (movementHolder.isPresent()) {

                LOGGER.error("Movement already registered for invoice_detail_id={} and tenant={}", invoiceDetailEntity.getId(),
                        tenant);

                return;
            }

            final var movement = InventoryEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .invoiceDetailEntity(invoiceDetailEntity)
                    .productEntity(productEntity)
                    .quantity(invoiceDetailEntity.getQuantity())
                    .unitPrice(invoiceDetailEntity.getUnitPrice())
                    .total(invoiceDetailEntity.getQuantity() * invoiceDetailEntity.getUnitPrice())
                    .operationType(INPUT_OPERATION_TYPE)
                    .tenant(tenant)
                    .description(MOVEMENT_DESCRIPTION)
                    .created(getUTCNow())
                    .build();

            inventoryRepository.save(movement);
            return;
        }

        LOGGER.info("product_id={} of invoice_detail_id={} and tenant={} is not candidate for inventory", productEntity.getId(),
                invoiceDetailEntity.getId(), tenant);
    }

    void deleteInventoryMovement(final InvoiceDetailEntity invoiceDetailEntity) {

        final var tenant = invoiceDetailEntity.getTenant();
        final var productEntity = invoiceDetailEntity.getProductEntity();

        LOGGER.info("Deleting inventory movement for tenant={} and invoice_detail_id={}", tenant, invoiceDetailEntity.getId());

        if (INVENTORY_PRODUCT_TYPE.equalsIgnoreCase(productEntity.getType())) {

            findInventoryMovement(productEntity, invoiceDetailEntity, tenant)
                    .ifPresent(inventoryEntity -> {

                        LOGGER.info("Deleting inventory_id={} for tenant={}", inventoryEntity.getId(), tenant);
                        inventoryRepository.delete(inventoryEntity);
                    });

            return;
        }

        LOGGER.info("product_id={} of invoice_detail_id={} and tenant={} is not candidate for inventory", productEntity.getId(),
                invoiceDetailEntity.getId(), tenant);
    }

    @Transactional
    void updateInventoryMovement(final InvoiceDetailEntity invoiceDetailEntity) {

        deleteInventoryMovement(invoiceDetailEntity);
        addInventoryMovement(invoiceDetailEntity);
    }

    // --------------------------------------------------------------------------------------------------

//    private Optional<InventoryEntity> findInventoryMovement(final String productEntityId, final String invoiceDetailEntityId, final String tenant) {
//
//        return inventoryRepository.findByProductEntityIdAndInvoiceDetailEntityIdAndOperationTypeAndTenant(productEntityId,
//                invoiceDetailEntityId, INPUT_OPERATION_TYPE, tenant);
//    }

    private Optional<InventoryEntity> findInventoryMovement(final ProductEntity productEntity,
                                                            final InvoiceDetailEntity invoiceDetailEntity,
                                                            final String tenant) {

        return inventoryRepository.findByProductEntityAndInvoiceDetailEntityAndOperationTypeAndTenant(productEntity,
                invoiceDetailEntity, INPUT_OPERATION_TYPE, tenant);
    }

}
