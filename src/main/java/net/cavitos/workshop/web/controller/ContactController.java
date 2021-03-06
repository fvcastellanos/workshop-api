package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.domain.model.web.Contact;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.ContactService;
import net.cavitos.workshop.transformer.ContactTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.CONTACTS_RESOURCE;

@RestController
@RequestMapping(CONTACTS_RESOURCE)
public class ContactController extends BaseController {

    private final ContactService contactService;

    @Autowired
    public ContactController(final ContactService contactService, final UserService userService) {

        super(userService);
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getByTenant(@RequestParam(defaultValue = "") @Size(max = 50) final String code,
                                                     @RequestParam(defaultValue = "P") @Size(max = 1) final String type,
                                                     @RequestParam(defaultValue = "") @Size(max = 150) final String name,
                                                     @RequestParam(defaultValue = "1") final int active,
                                                     @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                     @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                     final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var contactPage = contactService.getAll(tenant, code, type, name, active, page, size);

        final var providers = contactPage.stream()
                .map(ContactTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(providers, Pageable.ofSize(size), contactPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(@PathVariable @NotEmpty final String id,
                                           final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var providerEntity = contactService.getById(tenant, id);

        final var resource = ContactTransformer.toWeb(providerEntity);
        return  new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contact> add(@RequestBody @Valid final Contact contact,
                                       final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = contactService.add(tenant, contact);

        final var resource = ContactTransformer.toWeb(entity);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Contact> update(@PathVariable @NotEmpty final String id,
                                           @RequestBody @Valid final Contact contact,
                                           final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = contactService.update(tenant, id, contact);

        final var resource = ContactTransformer.toWeb(entity);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
