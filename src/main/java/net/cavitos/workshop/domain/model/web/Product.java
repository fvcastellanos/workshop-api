package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Product extends RepresentationModel<Product> {

    @NotEmpty
    @Size(max = 50)
    private String code;

    @NotEmpty
    @Pattern(regexp = "[P|S]", message = "Invalid type, allowed values: P or S")
    private String type;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @Size(max = 300)
    private String description;

    @NotNull
    private double minimalQuantity;

    @ValueOfEnum(enumType = ActiveStatus.class, message = "Invalid type, allowed values: ACTIVE|INACTIVE")
    private String active;
}
