package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.service.InvoiceService;
import net.cavitos.workshop.transformer.InvoiceTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.cavitos.workshop.domain.model.web.Invoice;

import static net.cavitos.workshop.web.controller.Route.INVOICES_RESOURCE;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.stream.Collectors;

@RestController
@RequestMapping(INVOICES_RESOURCE)
public class InvoiceController extends BaseController {

    private final InvoiceService invoiceService;

    public InvoiceController(final InvoiceService invoiceService) {

        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<Page<Invoice>> search(@RequestParam(defaultValue = "P") final String type,
                                                @RequestParam(defaultValue = "") final String contactName,
                                                @RequestParam(defaultValue = "") final String number,
                                                @RequestParam(defaultValue = "A") final String status,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var invoicePage = invoiceService.search(DEFAULT_TENANT, type, contactName, number, status,
                page, size);

        final var invoices = invoicePage.stream()
                .map(InvoiceTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(invoices, Pageable.ofSize(size), invoicePage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable @NotBlank final String id) {

        final var entity = invoiceService.findById(DEFAULT_TENANT, id);

        final var response = InvoiceTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Invoice> add(@RequestBody @Valid final Invoice invoice) {

        final var entity = invoiceService.add(DEFAULT_TENANT, invoice);

        final var response = InvoiceTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> update(@PathVariable @NotBlank final String id,
                                          @RequestBody @Valid final Invoice invoice) {

        final var entity = invoiceService.update(DEFAULT_TENANT, id, invoice);

        final var response = InvoiceTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
