package net.cavitos.workshop.domain.model.web.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ResourceResponse<T> {
    
    private T entity;
    private LinkResponse links;
}
