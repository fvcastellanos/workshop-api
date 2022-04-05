package net.cavitos.workshop.web.model.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ResourceResponse<T> {
    
    private T content;
    private LinkResponse links;
}
