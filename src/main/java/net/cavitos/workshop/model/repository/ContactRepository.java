package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ContactRepository extends PagingAndSortingRepository<ContactEntity, String> {

    Page<ContactEntity> findByTenantAndCodeContainsAndTypeAndNameContainsAndActive(String tenant,
                                                                                   String code,
                                                                                   String type,
                                                                                   String name,
                                                                                   int active,
                                                                                   Pageable pageable);

    Optional<ContactEntity> findByCodeEqualsIgnoreCaseAndTypeAndTenant(String code, String type, String tenant);
}