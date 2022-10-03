package com.edu.ulab.app.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class BookRequest {
    private String title;
    private String author;
    private long pageCount;
}
