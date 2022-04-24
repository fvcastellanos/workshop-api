package net.cavitos.workshop.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.cavitos.workshop.model.entity.CarBrandEntity;

import java.util.Optional;

public interface CarBrandRepository extends PagingAndSortingRepository<CarBrandEntity, String> {

    Page<CarBrandEntity> findByTenantAndActiveAndNameContainsIgnoreCase(String tenant, int active, String name, Pageable pageable);
    Optional<CarBrandEntity> findByNameAndTenant(String name, String tenant);
}
