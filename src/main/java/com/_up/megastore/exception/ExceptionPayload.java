package com._up.megastore.exception;

import java.time.LocalDateTime;

public record ExceptionPayload(
        String message,
        Integer status,
        LocalDateTime timestamp,
        StackTraceElement[] stackTrace
) {}