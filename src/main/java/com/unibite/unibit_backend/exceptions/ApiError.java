package com.unibite.unibit_backend.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
}
