package net.cavitos.workshop.domain.model.type;

import java.util.Locale;

public enum InventoryMovementType {

    INPUT("I"),
    OUTPUT("O"),
    UNKNOWN("U")
    ;

    private final String type;

    InventoryMovementType(final String type) {

        this.type = type;
    }

    public String type() {

        return type;
    }

    public static InventoryMovementType of(final String type) {

        return "I".equalsIgnoreCase(type) ? INPUT
                : OUTPUT;
    }
}
