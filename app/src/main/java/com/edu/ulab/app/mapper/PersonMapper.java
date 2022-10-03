package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.PersonDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.web.request.PersonRequest;
import com.edu.ulab.app.web.request.update.UpdatedPersonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Component
public interface PersonMapper {
    PersonDto personRequestToPersonDto(PersonRequest personRequest);

    PersonRequest personDtoToPersonRequest(PersonDto personDto);

    Person personDtoToPerson(PersonDto personDto);

    PersonDto personToPersonDto(Person person);

    PersonDto updatedPersonRequestToPersonDto(UpdatedPersonRequest personRequest);

    Person updatePersonToPerson(@MappingTarget Person person, PersonDto personDto);
}
