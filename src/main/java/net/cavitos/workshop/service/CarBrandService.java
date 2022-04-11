package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.exception.BusinessException;
import net.cavitos.workshop.domain.model.web.brand.CarBrand;
import net.cavitos.workshop.model.entity.CarBrandEntity;
import net.cavitos.workshop.model.repository.CarBrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static java.lang.String.format;

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
                                               final String name,
                                               final int page,
                                               final int size) {

        LOGGER.info("get car brands configured for tenant={}", tenant);

        final var pageable = PageRequest.of(page, size);
        return carBrandRepository.findByTenantAndActiveAndNameContains(tenant, active, name, pageable);
    }

    public CarBrandEntity getById(final String tenant,
                                            final String id) {

        final var carBrandEntity = carBrandRepository.findById(id)
                .orElseThrow(() -> new BusinessException(""));

        if (!carBrandEntity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("Car Brand id={} is not assigned to tenant={}", id, tenant);
            throw new BusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Car Brand");
        }

        return carBrandEntity;
    }

    public CarBrandEntity add(final String tenant, final CarBrand carBrand) {

        LOGGER.info("add new car brand with name={} for tenant={}", carBrand.getName(), tenant);

        final var carBrandHolder = carBrandRepository.findByNameAndTenant(carBrand.getName(), tenant);

        if (carBrandHolder.isPresent()) {

            throw new BusinessException(HttpStatus.UNPROCESSABLE_ENTITY,
                    format("Car brand with name=%s already exists for tenant=%s", carBrand.getName(), tenant));
        }

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

    public CarBrandEntity update(final String tenant, final String id, final CarBrand carBrand) {

        LOGGER.info("update car brand with id={}", id);

        var carBrandEntityHolder = carBrandRepository.findById(id);

        var carBrandEntity = carBrandEntityHolder.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
                "Car Brand Not Found"));

        if (!carBrandEntity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("Car Brand id={} is not assigned to tenant={}", id, tenant);
            throw new BusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Car Brand not found");
        }

        carBrandEntity.setName(carBrand.getName());
        carBrandEntity.setDescription(carBrand.getDescription());
        carBrandEntity.setActive(carBrand.getActive());
        carBrandRepository.save(carBrandEntity);

        return carBrandEntity;
    }
}
