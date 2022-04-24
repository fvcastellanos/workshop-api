package net.cavitos.workshop.web.controller;

import net.cavitos.workshop.domain.model.web.WorkOrder;
import net.cavitos.workshop.service.WorkOrderService;
import net.cavitos.workshop.transformer.WorkOrderTransformer;
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
import java.util.stream.Collectors;

import static net.cavitos.workshop.web.controller.Route.WORK_ORDERS_RESOURCE;

@RestController
@RequestMapping(WORK_ORDERS_RESOURCE)
public class WorkOrderController extends BaseController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(final WorkOrderService workOrderService) {

        this.workOrderService = workOrderService;
    }

    @GetMapping
    public ResponseEntity<Page<WorkOrder>> search(@RequestParam(defaultValue = "") final String number,
                                                  @RequestParam(defaultValue = "") final String contactName,
                                                  @RequestParam(defaultValue = "P") final String status,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                                                  @RequestParam(defaultValue = DEFAULT_SIZE) final int size) {

        final var workOrderPage = workOrderService.search(DEFAULT_TENANT, number, contactName, status,
                page, size);

        final var workOrders = workOrderPage.stream()
                .map(WorkOrderTransformer::toWeb)
                .collect(Collectors.toList());

        final var response = new PageImpl<>(workOrders, Pageable.ofSize(size), workOrderPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getById(@PathVariable @NotEmpty final String id) {

        final var entity = workOrderService.findById(DEFAULT_TENANT, id);

        final var response = WorkOrderTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkOrder> add(@RequestBody @Valid final WorkOrder workOrder) {

        final var entity = workOrderService.add(DEFAULT_TENANT, workOrder);

        final var response = WorkOrderTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> update(@PathVariable @NotEmpty final String id,
                                            @RequestBody @Valid final WorkOrder workOrder) {

        final var entity = workOrderService.update(DEFAULT_TENANT, id, workOrder);

        final var response = WorkOrderTransformer.toWeb(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
