package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.ProviderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProviderRepository extends PagingAndSortingRepository<ProviderEntity, String> {

    Page<ProviderEntity> findByTenantAndCodeContainsAndNameContainsAndActive(String tenant,
                                                                             String code,
                                                                             String name,
                                                                             int active,
                                                                             Pageable pageable);

    Optional<ProviderEntity> findByCodeEqualsIgnoreCaseAndTenant(String code, String tenant);
}
