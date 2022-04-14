package net.cavitos.workshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "car_line")
public class CarLineEntity {

    @Id
    @Size(max = 50)
    private String id;

    @ManyToOne
    private CarBrandEntity carBrand;

    @NotEmpty
    @Size(max = 100)
    private String name;

    @Size(max = 300)
    private String description;

    private int active;

    @NotEmpty
    @Size(max = 50)
    private String tenant;

    @CreatedDate
    private Instant created;
}
