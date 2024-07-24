package net.cavitos.workshop.domain.model.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.type.InventoryOperationType;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class InventoryMovementType extends RepresentationModel<InventoryMovementType> {

    @Size(max = 50)
    private String code;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 300)
    private String description;

    @NotBlank
    @ValueOfEnum(enumType = InventoryOperationType.class, message = "Invalid type, allowed values: INPUT|OUTPUT")
    private String type;

    @NotBlank
    @ValueOfEnum(enumType = ActiveStatus.class, message = "Invalid type, allowed values: ACTIVE|INACTIVE")
    private String active;
}
