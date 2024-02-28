package net.cavitos.workshop.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import net.cavitos.workshop.domain.model.web.InventoryMovementType;
import net.cavitos.workshop.security.service.UserService;
import net.cavitos.workshop.service.InventoryMovementTypeService;
import net.cavitos.workshop.transformer.InventoryMovementTypeTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static net.cavitos.workshop.web.controller.Route.INVENTORY_MOVEMENT_TYPES;

@RestController
@RequestMapping(INVENTORY_MOVEMENT_TYPES)
public class InventoryMovementTypeController extends BaseController {

    private final InventoryMovementTypeService inventoryMovementTypeService;
    public InventoryMovementTypeController(final UserService userService,
                                           final InventoryMovementTypeService inventoryMovementTypeService) {
        super(userService);
        this.inventoryMovementTypeService = inventoryMovementTypeService;
    }

    @GetMapping
    public ResponseEntity<Page<InventoryMovementType>> search(@RequestParam(defaultValue = "1") final int active,
                                                              @RequestParam(defaultValue = "%") final String type,
                                                              @RequestParam(defaultValue = "%") final String text,
                                                              @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                              @RequestParam(defaultValue = DEFAULT_SIZE) final int size,
                                                              final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var movementTypePage = inventoryMovementTypeService.search(active, type, text, tenant, page, size);

        final var movementTypes = movementTypePage.stream()
                .map(InventoryMovementTypeTransformer::toWeb)
                .toList();

        final var response = new PageImpl<>(movementTypes, Pageable.ofSize(size), movementTypePage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementType> getById(@PathVariable final String id,
                                                          final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var entity = inventoryMovementTypeService.getById(id, tenant);
        final var response = InventoryMovementTypeTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryMovementType> add(@RequestBody @Valid final InventoryMovementType inventoryMovementType,
                                                     final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var entity = inventoryMovementTypeService.add(inventoryMovementType, tenant);
        final var response = InventoryMovementTypeTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryMovementType> update(@PathVariable @NotBlank final String id,
                                                        @RequestBody @Valid final InventoryMovementType inventoryMovementType,
                                                        final Principal principal) {

        final var tenant = getUserTenant(principal);

        final var entity = inventoryMovementTypeService.update(id, inventoryMovementType, tenant);
        final var response = InventoryMovementTypeTransformer.toWeb(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
