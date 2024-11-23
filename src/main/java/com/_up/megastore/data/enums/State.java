package com._up.megastore.data.enums;

import java.util.Collections;
import java.util.List;

public enum State {

    IN_PROGRESS(
            Collections.emptyList(),
            " has been created.",
            "",
            "Order Created"
    ),

    FINISHED(
            List.of(IN_PROGRESS),
            " has been finished.",
            "Order is not in progress.",
            "Order Finished"
    ),

    IN_DELIVERY(
            List.of(FINISHED),
            " is now in delivery.",
            "Order is not finished.",
            "Order In Delivery"
    ),

    DELIVERED(
            List.of(IN_DELIVERY),
            " has been delivered.",
            "Order is not in delivery.",
            "Order Delivered"
    ),

    CANCELLED(
            List.of(IN_PROGRESS, FINISHED),
            " has been cancelled.",
            "Order is not in progress or finished.",
            "Order Cancelled"
    );

    private final List<State> previousStates;
    private final String stateMessage;
    private final String exceptionMessage;
    private final String subject;

    State(
            List<State> previousStates,
            String stateMessage,
            String exceptionMessage,
            String subject
    ) {
        this.previousStates = previousStates;
        this.stateMessage = stateMessage;
        this.exceptionMessage = exceptionMessage;
        this.subject = subject;
    }
}
