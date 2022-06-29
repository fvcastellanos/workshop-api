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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "user")
public class UserEntity {

    @Id
    @Size(max = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenantEntity;

    @NotNull
    @Size(max = 50)
    private String provider;

    @NotNull
    @Size(max = 150)
    private String userId;

    @NotNull
    private int active;

    @NotNull
    @CreatedDate
    private Instant created;

    private Instant updated;
}
