package net.cavitos.workshop.service;

import net.cavitos.workshop.model.entity.InventoryMovementTypeEntity;
import net.cavitos.workshop.model.repository.InventoryMovementTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;

@Service
public class InventoryMovementTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryMovementTypeService.class);

    private final InventoryMovementTypeRepository inventoryMovementTypeRepository;

    public InventoryMovementTypeService(final InventoryMovementTypeRepository inventoryMovementTypeRepository) {

        this.inventoryMovementTypeRepository = inventoryMovementTypeRepository;
    }

    public Page<InventoryMovementTypeEntity> search(final int active,
                                                    final String text,
                                                    final String tenant,
                                                    final int page,
                                                    final int size) {

        LOGGER.info("Search for inventory movement types with text: {} for tenant: {}", text, tenant);

        final var pageable = PageRequest.of(page, size);

        return inventoryMovementTypeRepository.search(active, text, tenant, pageable);
    }

    public InventoryMovementTypeEntity getById(final String id,
                                               final String tenant) {

        return inventoryMovementTypeRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Inventory movement type not found"));
    }
}
