package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.status.WorkOrderStatus;
import net.cavitos.workshop.domain.model.web.WorkOrder;
import net.cavitos.workshop.domain.model.web.common.CommonCarLine;
import net.cavitos.workshop.domain.model.web.common.CommonContact;
import net.cavitos.workshop.model.entity.CarLineEntity;
import net.cavitos.workshop.model.entity.ContactEntity;
import net.cavitos.workshop.model.entity.WorkOrderEntity;
import net.cavitos.workshop.web.controller.WorkOrderController;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class WorkOrderTransformer {

    private WorkOrderTransformer() {
    }

    public static WorkOrder toWeb(WorkOrderEntity entity) {

        final var selfLink = linkTo(methodOn(WorkOrderController.class)
                .getById(entity.getId()))
                .withSelfRel();

        final var status = WorkOrderStatus.of(entity.getStatus())
                .name();

        final var workOrder = new WorkOrder();
        workOrder.setNumber(entity.getNumber());
        workOrder.setStatus(status);
        workOrder.setOdometerMeasurement(entity.getOdometerMeasurement());
        workOrder.setOdometerValue(entity.getOdometerValue());
        workOrder.setGasAmount(entity.getGasAmount());
        workOrder.setNotes(entity.getNotes());
        workOrder.setPlateNumber(entity.getPlateNumber());

        if (nonNull(entity.getContactEntity())) {

            workOrder.setContact(buildWorkOrderContact(entity.getContactEntity()));
        }

        if (nonNull(entity.getCarLineEntity())) {

            workOrder.setCarLine(buildWorkOrderCarLine(entity.getCarLineEntity()));
        }

        workOrder.add(selfLink);

        return workOrder;
    }

    // ------------------------------------------------------------------------------------------------------

    private static CommonContact buildWorkOrderContact(final ContactEntity contactEntity) {

        final var contact = new CommonContact();
        contact.setCode(contactEntity.getCode());
        contact.setType(contactEntity.getType());
        contact.setName(contactEntity.getName());

        return contact;
    }

    private static CommonCarLine buildWorkOrderCarLine(final CarLineEntity entity) {

        final var carLine = new CommonCarLine();
        carLine.setId(entity.getId());
        carLine.setName(entity.getName());

        return carLine;
    }
}
