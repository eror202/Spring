package com.edu.ulab.app.web.request.update;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UpdatedPersonBookRequest {
    private UpdatedPersonRequest personRequest;
    private List<UpdatedBookRequest> bookRequests;
}
