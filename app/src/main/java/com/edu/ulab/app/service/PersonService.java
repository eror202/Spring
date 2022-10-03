package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.PersonDto;
import org.springframework.stereotype.Repository;


public interface PersonService {
    PersonDto createPerson(PersonDto personDto);

    PersonDto updatePerson(PersonDto personDto);

    PersonDto getPersonById(Long id);

    void deletePersonById(Long id);
}
