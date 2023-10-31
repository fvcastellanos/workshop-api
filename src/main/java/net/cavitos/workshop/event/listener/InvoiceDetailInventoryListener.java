package net.cavitos.workshop.event.listener;

import net.cavitos.workshop.event.model.InvoiceDetailEvent;
import net.cavitos.workshop.model.entity.InventoryEntity;
import net.cavitos.workshop.model.entity.InvoiceDetailEntity;
import net.cavitos.workshop.model.entity.ProductEntity;
import net.cavitos.workshop.model.generator.TimeBasedGenerator;
import net.cavitos.workshop.model.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static net.cavitos.workshop.factory.DateTimeFactory.getUTCNow;

@Component
public class InvoiceDetailInventoryListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDetailInventoryListener.class);

    private static final String INVENTORY_PRODUCT_TYPE = "P";
    private static final String INPUT_OPERATION_TYPE = "I";
    private static final String MOVEMENT_DESCRIPTION = "Inventory automatic movement triggered by invoice detail event";

    private final InventoryRepository inventoryRepository;

    public InvoiceDetailInventoryListener(final InventoryRepository inventoryRepository) {

        this.inventoryRepository = inventoryRepository;
    }

    @EventListener(InvoiceDetailEvent.class)
    public void handleEvent(InvoiceDetailEvent invoiceDetailEvent) {

        LOGGER.info("Invoice Detail Event of type={} with invoice_detail_id={}",
                invoiceDetailEvent.getEventType(), invoiceDetailEvent.getInvoiceDetailEntity().getId());

        final var eventType = invoiceDetailEvent.getEventType();
        final var invoiceDetailEntity = invoiceDetailEvent.getInvoiceDetailEntity();

        switch (eventType) {
            case ADD -> addInventoryMovement(invoiceDetailEntity);
            case UPDATE -> updateInventoryMovement(invoiceDetailEntity);
            case DELETE -> deleteInventoryMovement(invoiceDetailEntity);
        }
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

            // Recalculate total and unit price to reflect discount into unit price of product in inventory movement
            final var total = (invoiceDetailEntity.getQuantity() * invoiceDetailEntity.getUnitPrice())
                    - invoiceDetailEntity.getDiscountAmount();

            final var unitPrice = total / invoiceDetailEntity.getQuantity();

            final var operationDate = invoiceDetailEntity.getInvoiceEntity()
                    .getInvoiceDate();

            final var movement = InventoryEntity.builder()
                    .id(TimeBasedGenerator.generateTimeBasedId())
                    .invoiceDetailEntity(invoiceDetailEntity)
                    .productEntity(productEntity)
                    .quantity(invoiceDetailEntity.getQuantity())
                    .unitPrice(unitPrice)
                    .total(total)
                    .operationType(INPUT_OPERATION_TYPE)
                    .operationDate(operationDate)
                    .tenant(tenant)
                    .description(MOVEMENT_DESCRIPTION)
                    .created(getUTCNow())
                    .updated(getUTCNow())
                    .build();

            inventoryRepository.save(movement);
            return;
        }

        LOGGER.info("product_id={} of invoice_detail_id={} and tenant={} is not candidate for inventory", productEntity.getId(),
                invoiceDetailEntity.getId(), tenant);
    }

    @Transactional
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

    private Optional<InventoryEntity> findInventoryMovement(final ProductEntity productEntity,
                                                            final InvoiceDetailEntity invoiceDetailEntity,
                                                            final String tenant) {

        return inventoryRepository.findByProductEntityAndInvoiceDetailEntityAndOperationTypeAndTenant(productEntity,
                invoiceDetailEntity, INPUT_OPERATION_TYPE, tenant);
    }
}
