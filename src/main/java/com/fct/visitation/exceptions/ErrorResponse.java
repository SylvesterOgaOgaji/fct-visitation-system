// File: src/main/java/com/fct/visitation/exceptions/ErrorResponse.java
package com.fct.visitation.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public record ErrorResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    Instant timestamp,
    int status,
    String error,
    String code,
    String message,
    String path
) {
    public ErrorResponse(int status, String error, String code, String message, String path) {
        this(Instant.now(), status, error, code, message, path);
    }
}