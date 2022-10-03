package com.edu.ulab.app.validation;

import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.facade.PersonDataFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDataFacade personDataFacade;
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
