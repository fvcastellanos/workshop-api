package net.cavitos.workshop.domain.model.web;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CarLine extends RepresentationModel<CarLine> {

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 300)
    private String description;

    @Min(value = 0)
    @Max(value = 1)
    private int active;
}
