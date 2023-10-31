package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.InventoryEntity;
import net.cavitos.workshop.model.entity.InvoiceDetailEntity;
import net.cavitos.workshop.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.Instant;
import java.util.Optional;

public interface InventoryRepository extends CrudRepository<InventoryEntity, String>,
                                             PagingAndSortingRepository<InventoryEntity, String> {

    @Query(
            """
                select inventory from InventoryEntity inventory
                where inventory.tenant = :tenant
                    and UPPER(inventory.operationType) like UPPER(:operationType)
                    and inventory.created >= :initialDate
                    and inventory.created <= :finalDate
            """
    )
    Page<InventoryEntity> search(String operationType,
                                 Instant initialDate,
                                 Instant finalDate,
                                 String tenant,
                                 Pageable pageable);

    Optional<InventoryEntity> findByProductEntityAndInvoiceDetailEntityAndOperationTypeAndTenant(ProductEntity productEntity,
                                                                                                 InvoiceDetailEntity invoiceDetailEntity,
                                                                                                 String operationType,
                                                                                                 String tenant);

    Optional<InventoryEntity> findByIdAndTenant(String id, String tenant);
}
