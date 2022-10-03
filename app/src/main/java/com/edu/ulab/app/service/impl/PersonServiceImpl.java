package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.PersonDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.PersonMapper;
import com.edu.ulab.app.repository.PersonRepository;
import com.edu.ulab.app.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public PersonDto createPerson(PersonDto personDto) {
        Person person = personMapper.personDtoToPerson(personDto);
        log.info("Mapped user: {}", person);
        Person savedUser = personRepository.save(person);
        log.info("Saved user: {}", savedUser);
        return personMapper.personToPersonDto(savedUser);
    }

    @Override
    @Transactional
    public PersonDto updatePerson(PersonDto personDto) {
        Person personToUpdate = personMapper.personDtoToPerson(getPersonById(personDto.getId()));
        log.info("Person to update: {}", personToUpdate);
        Person updatedPerson = personRepository.save(personMapper.updatePersonToPerson(personToUpdate, personDto));
        log.info("Updated person: {}", updatedPerson);
        return personMapper.personToPersonDto(updatedPerson);
    }

    @Override
    public PersonDto getPersonById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found with id "+id));
        log.info("Get person in storage with id "+id+" : {}", person);
        return personMapper.personToPersonDto(person);
    }

    @Override
    @Transactional
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
        log.info("Delete completed: {}");
    }
}
