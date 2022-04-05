package net.cavitos.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.cavitos.workshop.model.entity.CarBrand;
import net.cavitos.workshop.model.repository.CarBrandRepository;

@Service
public class CarBrandService {

    private final CarBrandRepository carBrandRepository;

    @Autowired
    public CarBrandService(final CarBrandRepository carBrandRepository) {

        this.carBrandRepository = carBrandRepository;
    }

    public Page<CarBrand> getAll(final int page, final int size) {

        final var pageable = PageRequest.of(page, size);

        return carBrandRepository.findAll(pageable);
    }
    
}
