package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.InvoiceDetailService;
import net.cavitos.workshop.service.InvoiceService;
import net.cavitos.workshop.transformer.InvoiceDetailTransformer;
import net.cavitos.workshop.transformer.InvoiceTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.cavitos.workshop.domain.model.web.Invoice;
import net.cavitos.workshop.domain.model.web.InvoiceDetail;

import static net.cavitos.workshop.web.controller.Route.INVOICES_RESOURCE;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(INVOICES_RESOURCE)
public class InvoiceController extends BaseController {

    private final InvoiceService invoiceService;
    private final InvoiceDetailService invoiceDetailService;


    public InvoiceController(final InvoiceService invoiceService, 
                             final InvoiceDetailService invoiceDetailService,
                             final UserService userService) {

        super(userService);
        this.invoiceService = invoiceService;
        this.invoiceDetailService = invoiceDetailService;
    }

    @GetMapping
    public ResponseEntity<Page<Invoice>> search(@RequestParam(defaultValue = "P") final String type,
                                                @RequestParam(defaultValue = "") final String text,
                                                @RequestParam(defaultValue = "A") final String status,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                Principal principal) {

        final var tenant = getUserTenant(principal);
        final var invoicePage = invoiceService.search(tenant, type, status, text, page, size);

        final var invoices = invoicePage.stream()
                .map(InvoiceTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(invoices, Pageable.ofSize(size), invoicePage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable @NotBlank final String id,
                                           final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = invoiceService.findById(tenant, id);

        final var response = InvoiceTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Invoice> add(@RequestBody @Valid final Invoice invoice,
                                       final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = invoiceService.add(tenant, invoice);

        final var response = InvoiceTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> update(@PathVariable @NotBlank final String id,
                                          @RequestBody @Valid final Invoice invoice,
                                          final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = invoiceService.update(tenant, id, invoice);

        final var response = InvoiceTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // --------------------------------------------------------------------------------------------

    @GetMapping("/{id}/details")
    public ResponseEntity<List<InvoiceDetail>> getDetails(@PathVariable @NotEmpty final String id,
                                                       @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                       @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                       final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var details = invoiceDetailService.getInvoiceDetails(id, tenant);

        final var response = details.stream()
                .map(InvoiceDetailTransformer::toWeb)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<InvoiceDetail> addDetail(@PathVariable @NotEmpty final String id,
                                             @RequestBody @Valid final InvoiceDetail invoiceDetail,
                                             final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = invoiceDetailService.add(tenant, id, invoiceDetail);

        final var response = InvoiceDetailTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/details/{detailId}")
    public ResponseEntity<InvoiceDetail> updateDetail(@PathVariable @NotEmpty final String id,
                                                @PathVariable @NotEmpty final String detailId,
                                                @RequestBody @Valid final InvoiceDetail invoiceDetail,
                                                final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = invoiceDetailService.update(tenant, id, detailId, invoiceDetail);

        final var response = InvoiceDetailTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/details/{detailId}")
    public ResponseEntity<Void> deleteDetail(@PathVariable @NotEmpty final String id,
                                       @PathVariable @NotEmpty final String detailId,
                                       final Principal principal) {

        final var tenant = getUserTenant(principal);
        invoiceDetailService.delete(tenant, id, detailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
