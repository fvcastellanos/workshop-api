package net.cavitos.workshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @Size(max = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private ContactEntity contactEntity;

    @NotEmpty
    @Size(max = 1)
    private String type;

    @Size(max = 30)
    private String suffix;

    @NotEmpty
    @Size(max = 100)
    private String number;

    @Size(max = 250)
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "invoice_date")
    private Instant invoiceDate;

    @Column(name = "effective_date")
    private Instant effectiveDate;

    @NotEmpty
    @Size(max = 50)
    private String tenant;

    @NotEmpty
    @Size(max = 1)
    private String status;

    @NotNull
    @CreatedDate
    private Instant created;

    @NotNull
    private Instant updated;
}
