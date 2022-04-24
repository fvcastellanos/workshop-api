package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, String> {

    Page<InvoiceEntity> findByContactEntityNameContainsIgnoreCaseAndNumberContainsIgnoreCaseAndTypeAndStatusAndTenant(String contactName,
                                                                                                                      String number,
                                                                                                                      String type,
                                                                                                                      String status,
                                                                                                                      String tenant,
                                                                                                                      Pageable pageable);

    Optional<InvoiceEntity> findByIdAndTenant(String id, String tenant);

    Optional<InvoiceEntity> findBySuffixEqualsIgnoreCaseAndNumberEqualsIgnoreCaseAndContactEntityCodeEqualsIgnoreCaseAndTenant(String suffix,
                                                                                                                               String number,
                                                                                                                               String contact,
                                                                                                                               String tenant);
}
