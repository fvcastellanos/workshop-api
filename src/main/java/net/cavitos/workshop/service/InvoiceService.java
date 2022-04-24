package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.type.InvoiceStatus;
import net.cavitos.workshop.domain.model.web.Invoice;
import net.cavitos.workshop.model.entity.InvoiceEntity;
import net.cavitos.workshop.model.repository.ContactRepository;
import net.cavitos.workshop.model.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;

@Service
public class InvoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;
    private final ContactRepository contactRepository;

    public InvoiceService(final InvoiceRepository invoiceRepository,
                          final ContactRepository contactRepository) {

        this.invoiceRepository = invoiceRepository;
        this.contactRepository = contactRepository;
    }

    public Page<InvoiceEntity> search(final String tenant,
                                      final String type,
                                      final String contactName,
                                      final String number,
                                      final String status,
                                      final int page,
                                      final int size) {

        LOGGER.info("Search invoices with type={}, contact_name={}, invoice_number={}, status={} for tenant={}",
                type, contactName, number, status, tenant);

        final var pageable = PageRequest.of(page, size);
        return invoiceRepository.findByContactEntityNameContainsIgnoreCaseAndNumberContainsIgnoreCaseAndTypeAndStatusAndTenant(contactName,
                number, type, status, tenant, pageable);
    }

    public InvoiceEntity findById(final String tenant, final String id) {

        LOGGER.info("Retrieve invoice_id={} for tenant={}", id, tenant);

        return invoiceRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Invoice not found"));
    }

    public InvoiceEntity add(final String tenant, final Invoice invoice) {

        LOGGER.info("Add new Invoice={} for tenant={}", invoice, tenant);

        final var contact = invoice.getContact();
        final var existingInvoiceHolder = invoiceRepository.findBySuffixEqualsIgnoreCaseAndNumberEqualsIgnoreCaseAndContactEntityCodeEqualsIgnoreCaseAndTenant(invoice.getSuffix(),
                invoice.getNumber(), contact.getCode(), tenant);

        if (existingInvoiceHolder.isPresent()) {

            LOGGER.error("invoice_suffix={}, invoice_number={} already exists for tenant={}", invoice.getSuffix(),
                    invoice.getNumber(), tenant);

            throw createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Invoice already exists");
        }

        final var contactEntity = contactRepository.findByCodeEqualsIgnoreCaseAndTenant(contact.getCode(), tenant)
                .orElseThrow(() -> createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Contact not found"));

        var createdDate = LocalDate.parse(invoice.getInvoiceDate(), DateTimeFormatter.ISO_LOCAL_DATE);

        var entity = InvoiceEntity.builder()
                .id(UUID.randomUUID().toString())
                .contactEntity(contactEntity)
                .suffix(invoice.getSuffix())
                .number(invoice.getNumber())
                .imageUrl(invoice.getImageUrl())
                .type(invoice.getType())
                .status(InvoiceStatus.ACTIVE.value())
                .tenant(tenant)
                .invoiceDate(Instant.parse(invoice.getInvoiceDate()))
                .effectiveDate(Instant.parse(invoice.getEffectiveDate()))
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        invoiceRepository.save(entity);

        return entity;
    }
}
