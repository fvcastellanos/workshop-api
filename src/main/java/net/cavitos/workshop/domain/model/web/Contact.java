package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.enumeration.ActiveStatus;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Contact extends RepresentationModel<Contact> {

    @NotEmpty
    @Size(max = 50)
    private String code;

    @NotEmpty
    @Pattern(regexp = "[P|C]", message = "Invalid type, allowed values: P or C")
    private String type;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @Size(max = 300)
    private String description;

    @Size(max = 150)
    private String contact;

    @Size(max = 50)
    private String taxId;

    @ValueOfEnum(enumType = ActiveStatus.class, message = "Invalid type, allowed values: ACTIVE|INACTIVE")
    private String active;
}
