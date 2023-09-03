package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.status.WorkOrderStatus;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WorkOrderPartial {

    @ValueOfEnum(enumType = WorkOrderStatus.class, message = "Invalid type, allowed values: IN_PROGRESS|CANCELLED|CLOSED|DELIVERED")
    private String status;
}
