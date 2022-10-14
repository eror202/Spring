package com.edu.ulab.app.web.request.update;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UpdatedPersonRequest {
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
