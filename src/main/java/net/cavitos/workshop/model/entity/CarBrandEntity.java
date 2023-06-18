package net.cavitos.workshop.model.entity;

import java.time.Instant;

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
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "car_brand")
public class CarBrandEntity {

    @Id
    @Size(max = 50)
    private String id;

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
