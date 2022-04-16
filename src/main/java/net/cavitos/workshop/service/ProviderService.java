package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.web.Provider;
import net.cavitos.workshop.model.entity.ProviderEntity;
import net.cavitos.workshop.model.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;

@Service
public class ProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderService.class);

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(final ProviderRepository providerRepository) {

        this.providerRepository = providerRepository;
    }

    public Page<ProviderEntity> getAll(final String tenant,
                                       final String code,
                                       final String name,
                                       final int active,
                                       final int page,
                                       final int size) {

        LOGGER.info("Retrieve all providers for tenant={} with code={}, name={}, active={}", tenant, code, name,
                active);

        final var pageable = PageRequest.of(page, size);
        return providerRepository.findByTenantAndCodeContainsAndNameContainsAndActive(tenant, code, name, active,
                pageable);
    }

    public ProviderEntity getById(final String tenant, final String id) {

        LOGGER.info("Retrieve provider_id={} for tenant={}", id, tenant);

        final var providerEntity = providerRepository.findById(id)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Provider not found"));

        if (!providerEntity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("provider_id={} is not associated to tenant={}", id, tenant);
            throw createBusinessException(HttpStatus.NOT_FOUND, "Provider not found");
        }

        return providerEntity;
    }

    public ProviderEntity add(final String tenant, final Provider provider) {

        LOGGER.info("trying to add a new provider with name={} for tenant={}", provider.getName(), tenant);

        final var existingProviderHolder = providerRepository.findByCodeEqualsIgnoreCaseAndTenant(provider.getCode(),
                tenant);

        if (existingProviderHolder.isPresent()) {

            LOGGER.error("provider_code={} already exists for tenatn={}", provider.getCode(), tenant);
            throw createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Another provider is using the code=%", provider.getCode());
        }

        final var providerEntity = ProviderEntity.builder()
                .id(UUID.randomUUID().toString())
                .code(provider.getCode())
                .name(provider.getName())
                .description(provider.getDescription())
                .taxId(provider.getTaxId())
                .contact(provider.getContact())
                .tenant(tenant)
                .active(1)
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        providerRepository.save(providerEntity);

        return providerEntity;
    }
}
