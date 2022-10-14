package com.edu.ulab.app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class create messages for exceptions
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiException {
    private String message;
    private String debugMessage;
}
