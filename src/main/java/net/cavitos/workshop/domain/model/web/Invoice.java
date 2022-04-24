package net.cavitos.workshop.domain.model.web;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.cavitos.workshop.domain.model.type.InvoiceStatus;
import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cavitos.workshop.domain.model.web.common.CommonContact;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Invoice extends RepresentationModel<Invoice> {
    
    @NotBlank
    @Size(max = 1)
    @Pattern(regexp = "[P|C]", message = "Invalid type, allowed values: P or C")
    private String type;

    @NotBlank
    @Size(max = 30)
    private String suffix;

    @NotBlank
    @Size(max = 100)
    private String number;

    private String imageUrl;

    @NotNull
    private Instant invoiceDate;

    private Instant effectiveDate;

//    @Pattern(regexp = "[ACTIVE|CLOSED|CANCELLED]", message = "Invalid type, allowed values: ACTIVE, CLOSED or CANCELLED")
//    private String status;

    @Pattern(regexp = "[ACTIVE|CLOSED|CANCELLED]", message = "Invalid type, allowed values: ACTIVE, CLOSED or CANCELLED")
    private InvoiceStatus status;

    @NotNull
    private CommonContact contact;
}
