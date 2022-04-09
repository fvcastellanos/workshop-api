package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.web.brand.CarBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.cavitos.workshop.model.entity.CarBrandEntity;
import net.cavitos.workshop.model.repository.CarBrandRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class CarBrandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBrandService.class);

    private final CarBrandRepository carBrandRepository;

    @Autowired
    public CarBrandService(final CarBrandRepository carBrandRepository) {

        this.carBrandRepository = carBrandRepository;
    }

    public Page<CarBrandEntity> getAllByTenant(final String tenant,
                                               final int active,
                                               final int page,
                                               final int size) {

        LOGGER.info("get car brands configured for tenant={}", tenant);

        final var pageable = PageRequest.of(page, size);

        return carBrandRepository.findByTenantAndActive(tenant, active, pageable);
    }

    public CarBrandEntity add(final String tenant, final CarBrand carBrand) {

        LOGGER.info("add new car brand with name={} for tenant={}", carBrand.getName(), tenant);

        var entity = CarBrandEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(carBrand.getName())
                .description(carBrand.getDescription())
                .tenant(tenant)
                .created(Instant.now())
                .active(1)
                .build();

        return carBrandRepository.save(entity);
    }

}
