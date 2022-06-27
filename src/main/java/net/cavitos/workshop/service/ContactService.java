package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.type.ContactType;
import net.cavitos.workshop.domain.model.web.Contact;
import net.cavitos.workshop.model.entity.ContactEntity;
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

import static net.cavitos.workshop.domain.model.status.ActiveStatus.ACTIVE;
import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;

@Service
public class ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(final ContactRepository contactRepository) {

        this.contactRepository = contactRepository;
    }

    public Page<ContactEntity> getAll(final String tenant,
                                      final String code,
                                      final String type,
                                      final String name,
                                      final int active,
                                      final int page,
                                      final int size) {

        LOGGER.info("Retrieve all contacts for tenant={} with code={}, type={} name={}, active={}", tenant, code, type,
                name, active);

        final var pageable = PageRequest.of(page, size);
        return contactRepository.findByTenantAndCodeContainsIgnoreCaseAndTypeAndNameContainsIgnoreCaseAndActive(tenant, code, type, name,
                active, pageable);
    }

    public ContactEntity getById(final String tenant, final String id) {

        LOGGER.info("Retrieve contact_id={} for tenant={}", id, tenant);

        final var entity = contactRepository.findById(id)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Contact not found"));

        if (!entity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("provider_id={} is not associated to tenant={}", id, tenant);
            throw createBusinessException(HttpStatus.NOT_FOUND, "Provider not found");
        }

        return entity;
    }

    public ContactEntity add(final String tenant, final Contact contact) {

        LOGGER.info("trying to add a new contact with name={}, type={} for tenant={}", contact.getName(), contact.getType(), tenant);

        verifyExistingCodeTypeForTenant(tenant, contact);


        final var providerEntity = ContactEntity.builder()
                .id(UUID.randomUUID().toString())
                .code(contact.getCode())
                .type(buildContactTypeFrom(contact.getType()))
                .name(contact.getName())
                .description(contact.getDescription())
                .taxId(contact.getTaxId())
                .contact(contact.getContact())
                .tenant(tenant)
                .active(ACTIVE.value())
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        contactRepository.save(providerEntity);

        return providerEntity;
    }

    public ContactEntity update(final String tenant, final String id, final Contact contact) {

        LOGGER.info("trying to update contact_id={} for tenant={}", id, tenant);

        final var providerEntity = contactRepository.findById(id)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Contact not found"));

        if (!providerEntity.getTenant().equalsIgnoreCase(tenant)) {

            LOGGER.error("contact_id={} is not assigned to tenant={}", id, tenant);
            throw createBusinessException(HttpStatus.NOT_FOUND, "Contact not found");
        }

        final var type = ContactType.valueOf(contact.getType())
                .value();

        if (!providerEntity.getCode().equalsIgnoreCase(contact.getCode()) || !providerEntity.getType().equalsIgnoreCase(type)) {

            verifyExistingCodeTypeForTenant(tenant, contact);
        }

        final var active = ActiveStatus.valueOf(contact.getActive())
                        .value();

        providerEntity.setName(contact.getName());
        providerEntity.setCode(contact.getCode());
        providerEntity.setType(buildContactTypeFrom(contact.getType()));
        providerEntity.setDescription(contact.getDescription());
        providerEntity.setContact(contact.getContact());
        providerEntity.setTaxId(contact.getTaxId());
        providerEntity.setActive(active);
        providerEntity.setUpdated(Instant.now());

        contactRepository.save(providerEntity);

        return providerEntity;
    }

    // ----------------------------------------------------------------------------------------------------

    private void verifyExistingCodeTypeForTenant(final String tenant, final Contact contact) {

        final var existingContactHolder = contactRepository.findByCodeEqualsIgnoreCaseAndTenant(contact.getCode(),
                tenant);

        if (existingContactHolder.isPresent()) {

            LOGGER.error("contact_code={} and type={} already exists for tenant={}", contact.getCode(), contact.getType(), tenant);
            throw createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Another contact is using the code=%s for type=%s",
                    contact.getCode(), contact.getType());
        }
    }

    private String buildContactTypeFrom(String value) {

        return ContactType.valueOf(value)
                .value();
    }
}
