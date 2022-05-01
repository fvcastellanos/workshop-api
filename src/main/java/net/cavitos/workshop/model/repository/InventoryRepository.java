package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.InventoryEntity;
import net.cavitos.workshop.model.entity.InvoiceDetailEntity;
import net.cavitos.workshop.model.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface InventoryRepository extends PagingAndSortingRepository<InventoryEntity, String> {

    Optional<InventoryEntity> findByProductEntityAndInvoiceDetailEntityAndOperationTypeAndTenant(ProductEntity productEntity,
                                                                                                 InvoiceDetailEntity invoiceDetailEntity,
                                                                                                 String operationType,
                                                                                                 String tenant);
}
