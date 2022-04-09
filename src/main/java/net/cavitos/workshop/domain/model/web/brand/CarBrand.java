package net.cavitos.workshop.domain.model.web.brand;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CarBrand {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 300)
    private String description;
}
