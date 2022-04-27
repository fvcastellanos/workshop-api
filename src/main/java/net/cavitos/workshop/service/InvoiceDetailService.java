package net.cavitos.workshop.service;

import net.cavitos.workshop.domain.model.web.InvoiceDetail;
import net.cavitos.workshop.model.entity.InvoiceDetailEntity;
import net.cavitos.workshop.model.entity.WorkOrderEntity;
import net.cavitos.workshop.model.repository.InvoiceDetailRepository;
import net.cavitos.workshop.model.repository.InvoiceRepository;
import net.cavitos.workshop.model.repository.ProductRepository;
import net.cavitos.workshop.model.repository.WorkOrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static net.cavitos.workshop.factory.BusinessExceptionFactory.createBusinessException;
import static net.cavitos.workshop.factory.DateTimeFactory.getUTCNow;

@Service
public class InvoiceDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceDetailService.class);

    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final WorkOrderRepository workOrderRepository;

    public InvoiceDetailService(final InvoiceDetailRepository invoiceDetailRepository,
                                final ProductRepository productRepository,
                                final InvoiceRepository invoiceRepository,
                                final WorkOrderRepository workOrderRepository) {

        this.invoiceDetailRepository = invoiceDetailRepository;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.workOrderRepository = workOrderRepository;
    }

    public Page<InvoiceDetailEntity> getInvoiceDetails(final String invoiceId, final String tenant, final int page, final int size) {

        LOGGER.info("Retrieve invoice details for invoice_id={} and tenant={}", invoiceId, tenant);

        final var pageable = PageRequest.of(page, size);
        return invoiceDetailRepository.findByInvoiceEntityIdAndTenant(invoiceId, tenant, pageable);
    }

    public InvoiceDetailEntity add(final String tenant, final String invoiceId, final InvoiceDetail invoiceDetail) {

        LOGGER.info("Add new detail for invoice_id={} and tenant={}", invoiceId, tenant);

        final var product = invoiceDetail.getProduct();
        final var productEntity = productRepository.findByCodeEqualsIgnoreCaseAndTenant(product.getCode(), tenant)
                        .orElseThrow(() -> createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Product not found"));

        final var invoiceEntity = invoiceRepository.findByIdAndTenant(invoiceId, tenant)
                .orElseThrow(() -> createBusinessException(HttpStatus.NOT_FOUND, "Invoice not found"));

        WorkOrderEntity workOrderEntity = null;
        if (StringUtils.isNotBlank(invoiceDetail.getWorkOrderNumber())) {

            workOrderEntity = workOrderRepository.findByNumberEqualsIgnoreCaseAndTenant(invoiceDetail.getWorkOrderNumber(), tenant)
                    .orElseThrow(() -> createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Work Order Number not found"));

        }

        final var invoiceHolder = invoiceDetailRepository.findByInvoiceEntityIdAndProductEntityIdAndTenant(invoiceId,
                productEntity.getId(), tenant);

        if (invoiceHolder.isPresent()) {

            LOGGER.error("product_id={} already exists for invoice_id={} and tenant={}", productEntity.getId(),
                    invoiceId, tenant);

            throw createBusinessException(HttpStatus.UNPROCESSABLE_ENTITY, "Product already exists");
        }

        final var entity = InvoiceDetailEntity.builder()
                .id(UUID.randomUUID().toString())
                .productEntity(productEntity)
                .invoiceEntity(invoiceEntity)
                .workOrderEntity(workOrderEntity)
                .quantity(invoiceDetail.getQuantity())
                .unitPrice(invoiceDetail.getUnitPrice())
                .tenant(tenant)
                .created(getUTCNow())
                .build();

        invoiceDetailRepository.save(entity);

        return entity;
    }
}
