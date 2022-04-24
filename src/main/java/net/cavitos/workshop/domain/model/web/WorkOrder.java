package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.web.workorder.WorkOrderCarLine;
import net.cavitos.workshop.domain.model.web.workorder.WorkOrderContact;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
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

    @Size(max = 1)
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

    @NotNull
    private WorkOrderCarLine carLine;

    @NotNull
    private WorkOrderContact contact;
}
