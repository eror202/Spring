package com.edu.ulab.app.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonRequest {
    private String fullName;
    private String title;
    private int age;
}
