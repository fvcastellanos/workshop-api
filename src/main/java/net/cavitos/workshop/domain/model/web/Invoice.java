package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.status.InvoiceStatus;
import net.cavitos.workshop.domain.model.type.InvoiceType;
import net.cavitos.workshop.domain.model.validator.Date;
import net.cavitos.workshop.domain.model.validator.ValueOfEnum;
import net.cavitos.workshop.domain.model.web.common.CommonContact;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Invoice extends RepresentationModel<Invoice> {
    
    @NotBlank
    @ValueOfEnum(enumType = InvoiceType.class, message = "Invalid type, allowed values: CUSTOMER|PROVIDER")
    private String type;

    @NotBlank
    @Size(max = 30)
    private String suffix;

    @NotBlank
    @Size(max = 100)
    private String number;

    private String imageUrl;

    @Date
    @NotNull
    private String invoiceDate;

    private String effectiveDate;

    @ValueOfEnum(enumType = InvoiceStatus.class, message = "Invalid type, allowed values: ACTIVE|CLOSED|CANCELLED")
    private String status;

    @NotNull
    private CommonContact contact;
}
