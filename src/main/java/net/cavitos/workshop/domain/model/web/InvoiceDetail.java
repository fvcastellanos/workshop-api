package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.web.common.CommonProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InvoiceDetail {

    @Size(max = 50)
    private String id;

    @NotNull
    private CommonProduct product;

    @Size(max = 100)
    private String workOrderNumber;

    @NotNull
    @Min(value = 0)
    private double quantity;

    @NotNull
    @Min(value = 0)
    private double unitPrice;

    private double total;
}
