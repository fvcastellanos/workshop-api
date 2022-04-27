package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.InvoiceDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface InvoiceDetailRepository extends PagingAndSortingRepository<InvoiceDetailEntity, String> {

    Optional<InvoiceDetailEntity> findByInvoiceEntityIdAndProductEntityIdAndTenant(String invoiceId,
                                                                                   String productId,
                                                                                   String tenant);

    Optional<InvoiceDetailEntity> findByIdAndTenant(String id, String tenant);

    Page<InvoiceDetailEntity> findByInvoiceEntityIdAndTenant(String invoiceId, String tenant, Pageable pageable);
}
