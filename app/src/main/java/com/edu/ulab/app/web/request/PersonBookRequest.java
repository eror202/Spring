package com.edu.ulab.app.web.request;

import lombok.Data;

import java.util.List;

@Data
public class PersonBookRequest {
    private PersonRequest personRequest;
    private List<BookRequest> bookRequests;
}
