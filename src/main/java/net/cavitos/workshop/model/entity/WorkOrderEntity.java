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
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
@Table(name = "work_order")
public class WorkOrderEntity {

    @Id
    @Size(max = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "car_line_id")
    private CarLineEntity carLineEntity;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private ContactEntity contactEntity;

    @NotEmpty
    @Size(max = 100)
    private String number;

    @NotEmpty
    @Size(max = 1)
    private String status;

    @NotEmpty
    @Size(max = 1)
    private String odometerMeasurement;

    @Min(0)
    @NotNull
    private double odometerValue;

    @Min(0)
    @NotNull
    private double gasAmount;

    @NotEmpty
    @Size(max = 50)
    private String tenant;

    private String notes;

    @NotNull
    @CreatedDate
    private Instant created;

    @NotNull
    private Instant updated;
}
