package net.cavitos.workshop.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @Size(max = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "invoice_detail_id")
    private InvoiceDetailEntity invoiceDetailEntity;

    @NotBlank
    @Size(max = 1)
    @Column(name = "operation_type")
    private String operationType;

    @Size(max = 200)
    private String description;

    @NotNull
    @Min(value = 0)
    private double quantity;

    @NotNull
    @Min(value = 0)
    @Column(name = "unit_price")
    private double unitPrice;

    @NotNull
    @Min(value = 0)
    private double total;

    @NotNull
    @CreatedDate
    private Instant created;

    @NotNull
    @LastModifiedDate
    private Instant updated;

    @NotBlank
    @Size(max = 50)
    private String tenant;
}
