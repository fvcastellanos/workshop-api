package net.cavitos.workshop.model.repository;

import net.cavitos.workshop.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {

    Page<ProductEntity> findByTypeAndCodeContainsIgnoreCaseAndNameContainsIgnoreCaseAndActiveAndTenant(String type,
                                                                                                       String code,
                                                                                                       String name,
                                                                                                       int active,
                                                                                                       String tenant,
                                                                                                       Pageable pageable);

    Optional<ProductEntity> findByCodeEqualsIgnoreCaseAndTenant(String code, String tenant);
}
