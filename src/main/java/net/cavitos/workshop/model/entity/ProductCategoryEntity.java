package net.cavitos.workshop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_category")
public class ProductCategoryEntity {

    @Id
    @Size(max = 50)
    private String id;

    @NotEmpty
    @Size(max = 50)
    private String code;

    @NotEmpty
    @Size(max = 150)
    private String name;

    @Size(max = 300)
    private String description;

    private int active;

    private Instant created;

    private Instant updated;

    @NotEmpty
    @Size(max = 50)
    private String tenant;
}
