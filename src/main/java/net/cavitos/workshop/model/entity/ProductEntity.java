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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
@Table(name = "product")
public class ProductEntity {

    @Id
    @Size(max = 50)
    private String id;

    @NotEmpty
    @Size(max = 50)
    private String code;

    @NotEmpty
    @Size(max = 1)
    private String type;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @Size(max = 300)
    private String description;

    @Column(name = "minimal_quantity")
    private double minimalQuantity;

    @Column(name = "sale_price")
    private double salePrice;

    @CreatedDate
    private Instant created;

    private Instant updated;

    private int active;

    @NotEmpty
    @Size(max = 50)
    private String tenant;
}
