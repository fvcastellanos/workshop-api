package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.web.common.CommonInvoice;
import net.cavitos.workshop.domain.model.web.common.CommonProduct;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WorkOrderDetail {

    @NotBlank
    @Size(max = 50)
    private String id;

    private CommonInvoice invoice;

    private CommonProduct product;

    @Min(0)
    @NotNull
    private double quantity;

    @Min(0)
    @NotNull
    private double unitPrice;
}
