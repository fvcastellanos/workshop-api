package net.cavitos.workshop.web.controller.ui.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchRequest {

    private String text;
    private int active;
    private String type;
    private String code;
    private int page;
    private int size;
}
