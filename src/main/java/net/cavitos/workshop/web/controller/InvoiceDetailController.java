package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.domain.model.web.InvoiceDetail;
import net.cavitos.workshop.service.InvoiceDetailService;
import net.cavitos.workshop.transformer.InvoiceDetailTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.INVOICES_RESOURCE;
import static net.cavitos.workshop.web.controller.Route.INVOICE_DETAILS_RESOURCE;

@RestController
@RequestMapping(INVOICES_RESOURCE + "/{invoiceId}" + INVOICE_DETAILS_RESOURCE)
public class InvoiceDetailController extends BaseController {

    private final InvoiceDetailService invoiceDetailService;

    public InvoiceDetailController(final InvoiceDetailService invoiceDetailService) {

        this.invoiceDetailService = invoiceDetailService;
    }

    @GetMapping
    public ResponseEntity<Page<InvoiceDetail>> details(@PathVariable @NotEmpty final String invoiceId,
                                                       @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                       @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var detailPage = invoiceDetailService.getInvoiceDetails(invoiceId, DEFAULT_TENANT,
                page, size);

        final var details = detailPage.stream()
                .map(InvoiceDetailTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(details, Pageable.ofSize(size), detailPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InvoiceDetail> add(@PathVariable @NotEmpty final String invoiceId,
                                             @RequestBody @Valid final InvoiceDetail invoiceDetail) {

        final var entity = invoiceDetailService.add(DEFAULT_TENANT, invoiceId, invoiceDetail);

        final var response = InvoiceDetailTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    public ResponseEntity delete(@PathVariable @NotEmpty final String invoiceId,)
}
