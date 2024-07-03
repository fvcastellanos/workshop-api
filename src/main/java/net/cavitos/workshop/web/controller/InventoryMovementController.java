package net.cavitos.workshop.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import net.cavitos.workshop.domain.model.validator.Date;
import net.cavitos.workshop.domain.model.web.InventoryMovement;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.InventoryMovementService;
import net.cavitos.workshop.transformer.InventoryMovementTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static net.cavitos.workshop.web.controller.Route.INVENTORY_MOVEMENTS_RESOURCE;

@RestController
@RequestMapping(INVENTORY_MOVEMENTS_RESOURCE)
public class InventoryMovementController extends BaseController {

    private final InventoryMovementService inventoryMovementService;

    public InventoryMovementController(final UserService userService,
                                       final InventoryMovementService inventoryMovementService) {

        super(userService);
        this.inventoryMovementService = inventoryMovementService;
    }

    @GetMapping
    public ResponseEntity<Page<InventoryMovement>> search(@RequestParam(defaultValue = "%") final String type,
                                                          @RequestParam(defaultValue = "%") final String operationTypeCode,
                                                          @Valid @Date @RequestParam final String initialDate,
                                                          @Valid @Date @RequestParam final String finalDate,
                                                          @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                          @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                          final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var movementPage = inventoryMovementService.search(type, operationTypeCode, parseISODate(initialDate),
                parseISODate(finalDate), tenant, page, size);

        final var movements = movementPage.stream()
                .map(InventoryMovementTransformer::toWeb)
                .toList();

        final var response = new PageImpl<>(movements, Pageable.ofSize(size), movementPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovement> getById(@PathVariable @NotBlank final String id,
                                                     final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = inventoryMovementService.findById(id, tenant);

        final var response = InventoryMovementTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryMovement> add(@RequestBody @Valid final InventoryMovement inventoryMovement,
                                                 final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = inventoryMovementService.add(tenant, inventoryMovement);

        final var response = InventoryMovementTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryMovement> update(@PathVariable @NotBlank final String id,
                                                    @RequestBody @Valid final InventoryMovement inventoryMovement,
                                                    final Principal principal) {

        final var tenant = getUserTenant(principal);
        final var entity = inventoryMovementService.update(tenant, id, inventoryMovement);

        final var response = InventoryMovementTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable @NotEmpty final String id,
                                       final Principal principal) {

        final var tenant = getUserTenant(principal);
        inventoryMovementService.delete(id, tenant);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --------------------------------------------------------------------------------------------

    private Instant parseISODate(final String isoDate) {

        final var localDate = LocalDate.parse(isoDate, DateTimeFormatter.ISO_DATE);

        final var zoneId = ZoneId.systemDefault();

        return localDate.atStartOfDay(zoneId)
                .toInstant();
    }
}
