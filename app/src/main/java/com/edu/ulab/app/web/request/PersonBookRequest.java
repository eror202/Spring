package com.edu.ulab.app.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonBookRequest {
    private PersonRequest personRequest;
    private List<BookRequest> bookRequests;
}
