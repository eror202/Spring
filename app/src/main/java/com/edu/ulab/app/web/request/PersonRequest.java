package com.edu.ulab.app.web.request;

import lombok.Data;

@Data
public class PersonRequest {
    private String fullName;
    private String title;
    private int age;
}
