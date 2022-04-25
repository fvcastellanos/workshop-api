package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.enumeration.ActiveStatus;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CarBrand extends RepresentationModel<CarBrand> {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 300)
    private String description;

    @ValueOfEnum(enumType = ActiveStatus.class, message = "Invalid type, allowed values: ACTIVE|INACTIVE")
    private String active;
}
