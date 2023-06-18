package net.cavitos.workshop.domain.model.web.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommonContact {

    @NotEmpty
    @Size(max = 50)
    private String id;

    private String name;
    private String type;
    private String taxId;
}
