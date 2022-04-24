package net.cavitos.workshop.domain.model.web.workorder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WorkOrderContact {

    @NotEmpty
    @Size(max = 50)
    private String code;

    private String name;
    private String type;
}
