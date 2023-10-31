package net.cavitos.workshop.domain.model.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.type.InventoryMovementType;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;
import net.cavitos.workshop.domain.model.web.common.CommonProduct;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class InventoryMovement extends RepresentationModel<InventoryMovement> {

    @Size(max = 50)
    private String id;

    @NotNull
    private CommonProduct product;

    @NotEmpty
    @ValueOfEnum(enumType = InventoryMovementType.class, message = "Invalid type, allowed values: INPUT|OUTPUT")
    private String operationType;

    @Size(max = 50)
    private String invoiceDetailId;

    @Min(0)
    @NotNull
    private double quantity;

    @Min(0)
    @NotNull
    private double unitPrice;

    @Min(0)
    private double discountAmount;

    @Size(max = 200)
    private String description;
}
