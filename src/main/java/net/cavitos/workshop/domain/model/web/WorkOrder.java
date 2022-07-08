package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.status.WorkOrderStatus;
import net.cavitos.workshop.domain.model.validator.Date;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;
import net.cavitos.workshop.domain.model.web.common.CommonCarLine;
import net.cavitos.workshop.domain.model.web.common.CommonContact;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WorkOrder extends RepresentationModel<WorkOrder> {

    @NotEmpty
    @Size(max = 100)
    private String number;

    @ValueOfEnum(enumType = WorkOrderStatus.class, message = "Invalid type, allowed values: IN_PROGRESS|CANCELLED|CLOSED|DELIVERED")
    private String status;

    @NotEmpty
    @Size(max = 1)
    private String odometerMeasurement;

    @Min(0)
    private double odometerValue;

    @Min(0)
    private double gasAmount;

    @Size(max = 1024)
    private String notes;

    @NotEmpty
    @Size(max = 30)
    private String plateNumber;

    @Date
    @NotBlank
    private String orderDate;

    @NotNull
    private CommonCarLine carLine;

    @NotNull
    private CommonContact contact;
}
