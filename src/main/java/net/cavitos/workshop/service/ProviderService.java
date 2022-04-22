package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.web.Contact;
import net.cavitos.workshop.model.entity.ProviderEntity;
import net.cavitos.workshop.model.repository.ContactRepository;
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

    private final ContactRepository contactRepository;

    @Autowired
    public ProviderService(final ContactRepository contactRepository) {

        this.contactRepository = contactRepository;
    }

    public Page<ProviderEntity> getAll(final String tenant,
                                       final String code,
                                       final String type,
                                       final String name,
                                       final int active,
                                       final int page,
                                       final int size) {

        LOGGER.info("Retrieve all providers for tenant={} with code={}, type={} name={}, active={}", tenant, code, type,
                name, active);

        final var pageable = PageRequest.of(page, size);
        return contactRepository.findByTenantAndCodeContainsAndNameContainsAndActive(tenant, code, type, name, active,
                pageable);
    }

    public ProviderEntity getById(final String tenant, final String id) {

        LOGGER.info("Retrieve provider_id={} for tenant={}", id, tenant);

        final var providerEntity = contactRepository.findById(id)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Provider not found"));

        if (!providerEntity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("provider_id={} is not associated to tenant={}", id, tenant);
            throw createBusinessException(HttpStatus.NOT_FOUND, "Provider not found");
        }

        return providerEntity;
    }

    public ProviderEntity add(final String tenant, final Contact contact) {

        LOGGER.info("trying to add a new provider with name={} for tenant={}", contact.getName(), tenant);

        final var existingProviderHolder = contactRepository.findByCodeEqualsIgnoreCaseAndTenant(contact.getCode(),
                contact.getType(), tenant);

        if (existingProviderHolder.isPresent()) {

            LOGGER.error("provider_code={} already exists for tenant={}", contact.getCode(), tenant);
            throw createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Another provider is using the code=%", contact.getCode());
        }

        final var providerEntity = ProviderEntity.builder()
                .id(UUID.randomUUID().toString())
                .code(contact.getCode())
                .name(contact.getName())
                .description(contact.getDescription())
                .taxId(contact.getTaxId())
                .contact(contact.getContact())
                .tenant(tenant)
                .active(1)
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        contactRepository.save(providerEntity);

        return providerEntity;
    }

    public ProviderEntity update(final String tenant, final String id, final Contact contact) {

        LOGGER.info("trying to update provider_id={} for tenant={}", id, tenant);

        final var providerEntity = contactRepository.findById(id)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Provider not found"));

        if (!providerEntity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("provider_id={} is not assigned to tenant={}", id, tenant);
            throw createBusinessException(HttpStatus.NOT_FOUND, "Provider not found");
        }

        providerEntity.setName(contact.getName());
        providerEntity.setDescription(contact.getDescription());
        providerEntity.setContact(contact.getContact());
        providerEntity.setTaxId(contact.getTaxId());
        providerEntity.setActive(contact.getActive());
        providerEntity.setUpdated(Instant.now());

        contactRepository.save(providerEntity);

        return providerEntity;
    }
}
