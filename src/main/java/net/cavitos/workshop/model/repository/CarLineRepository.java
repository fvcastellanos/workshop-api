package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.CarBrandEntity;
import net.cavitos.workshop.model.entity.CarLineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CarLineRepository extends PagingAndSortingRepository<CarLineEntity, String> {

    Page<CarLineEntity> findByCarBrandAndTenantAndActiveAndNameContainsIgnoreCase(CarBrandEntity carBrandEntity,
                                                                                  String tenant,
                                                                                  int active,
                                                                                  String name,
                                                                                  Pageable pageable);

    Optional<CarLineEntity> findByCarBrandAndNameAndTenant(CarBrandEntity carBrandEntity, String name, String tenant);

    Optional<CarLineEntity> findByIdAndTenant(String id, String tenant);
}
