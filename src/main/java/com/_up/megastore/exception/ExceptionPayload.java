package com._up.megastore.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;


public record ExceptionPayload(
        String message,
        Integer status,
        LocalDateTime timestamp,
        StackTraceElement[] stackTrace
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionPayload that = (ExceptionPayload) o;
        return Objects.equals(message, that.message) && Objects.equals(status, that.status) && Objects.equals(timestamp, that.timestamp) && Objects.deepEquals(stackTrace, that.stackTrace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, status, timestamp, Arrays.hashCode(stackTrace));
    }

    @Override
    public String toString() {
        return "ExceptionPayload{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", stackTrace=" + Arrays.toString(stackTrace) +
                '}';
    }
}