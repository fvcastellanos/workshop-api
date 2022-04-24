package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.web.common.CommonCarLine;
import net.cavitos.workshop.domain.model.web.common.CommonContact;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WorkOrder extends RepresentationModel<WorkOrder> {

    @NotEmpty
    @Size(max = 100)
    private String number;

    @Size(max = 1)
    @Pattern(regexp = "[A|P|I|C]", message = "Invalid type, allowed values: A, P, I or C")
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
    private CommonCarLine carLine;

    @NotNull
    private CommonContact contact;
}
