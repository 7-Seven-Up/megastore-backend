package com._up.megastore.data.enums;

import java.util.Collections;
import java.util.List;

public enum State {

    IN_PROGRESS(Collections.emptyList(), " has been created.", ""),
    FINISHED(List.of(IN_PROGRESS), " has been finished.", "Order is not in progress."),
    IN_DELIVERY(List.of(FINISHED), " is now in delivery.", "Order is not finished."),
    DELIVERED(List.of(IN_DELIVERY), " has been delivered.", "Order is not in delivery."),
    CANCELLED(List.of(IN_PROGRESS, FINISHED), " has been cancelled.", "Order is not in progress or finished.");

    public final List<State> previousStates;
    public final String stateMessage;
    public final String exceptionMessage;

    State(List<State> previousStates, String stateMessage, String exceptionMessage) {
        this.previousStates = previousStates;
        this.stateMessage = stateMessage;
        this.exceptionMessage = exceptionMessage;
    }
}
