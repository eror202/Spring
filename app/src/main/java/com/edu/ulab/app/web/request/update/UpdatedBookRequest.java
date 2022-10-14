package com.edu.ulab.app.web.request.update;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UpdatedBookRequest {
    private Long id;
    private String title;
    private String author;
    private long pageCount;
}
