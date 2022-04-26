package net.cavitos.workshop.domain.model.status;

public enum WorkOrderStatus {

    IN_PROGRESS("P"),
    CANCELLED("A"),
    CLOSED("C"),
    DELIVERED("D");

    private final String status;

    WorkOrderStatus(final String status) {

        this.status = status;
    }

    public String value() {

        return status;
    }
}
