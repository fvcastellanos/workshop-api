package net.cavitos.workshop.domain.model.web.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommonProduct {

    @NotBlank
    @Size(max = 50)
    private String code;

    @Size(max = 150)
    private String name;

    @Size(max = 50)
    private String type;
}
