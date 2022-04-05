package net.cavitos.workshop.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.cavitos.workshop.model.entity.CarBrand;

public interface CarBrandRepository extends PagingAndSortingRepository<CarBrand, String> {

    Page<CarBrand> findAll(Pageable pageable);
}
