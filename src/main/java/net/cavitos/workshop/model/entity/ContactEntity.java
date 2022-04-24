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
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "contact")
public class ContactEntity {

    @Id
    @Size(max = 50)
    private String id;

    @NotEmpty
    @Size(max = 1)
    private String type;

    @NotEmpty
    @Size(max = 50)
    private String code;

    @NotEmpty
    @Size(max = 50)
    private String name;

    @Size(max = 150)
    private String contact;

    @Size(max = 50)
    private String taxId;

    @Size(max = 300)
    private String description;

    @Min(value = 0)
    @Max(value = 1)
    private int active;

    @CreatedDate
    private Instant created;
    private Instant updated;

    @NotEmpty
    @Size(max = 50)
    private String tenant;
}
