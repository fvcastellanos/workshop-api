package net.cavitos.workshop.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.cavitos.workshop.model.entity.CarBrandEntity;

public interface CarBrandRepository extends PagingAndSortingRepository<CarBrandEntity, String> {

    Page<CarBrandEntity> findByTenantAndActive(String tenant, int active, Pageable pageable);
}
