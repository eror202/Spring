package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.PersonDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.PersonMapper;
import com.edu.ulab.app.repository.PersonRepository;
import com.edu.ulab.app.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link PersonServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing user functionality.")
public class PersonServiceImplTest {
    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    PersonRepository personRepository;

    @Mock
    PersonMapper personMapper;

    @Test
    @DisplayName("Создание пользователя. Должно пройти успешно.")
    void savePerson_Test() {
        //given

        PersonDto personDto = new PersonDto();
        personDto.setAge(11);
        personDto.setFullName("test name");
        personDto.setTitle("test title");

        Person person  = new Person();
        person.setFullName("test name");
        person.setAge(11);
        person.setTitle("test title");

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        PersonDto result = new PersonDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");


        //when

        when(personMapper.personDtoToPerson(personDto)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(savedPerson);
        when(personMapper.personToPersonDto(savedPerson)).thenReturn(result);


        //then

        PersonDto personDtoResult = personService.createPerson(personDto);
        assertEquals(1L, personDtoResult.getId());
    }

    // update

    @Test
    @DisplayName("Изменить пользователя. Должно пройти успешно.")
    void updatePerson_Test() {
        //given

        PersonDto userUpdateDto = new PersonDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setAge(18);
        userUpdateDto.setFullName("Yura");
        userUpdateDto.setTitle("reader293");

        Person updatePerson  = new Person();
        updatePerson.setId(1L);
        updatePerson.setFullName("Yura");
        updatePerson.setAge(18);
        updatePerson.setTitle("reader293");

        PersonDto resultUpdate = new PersonDto();
        resultUpdate.setId(1L);
        resultUpdate.setFullName("Yura");
        resultUpdate.setAge(18);
        resultUpdate.setTitle("reader293");

        //when

        when(personMapper.personDtoToPerson(userUpdateDto)).thenReturn(updatePerson);
        when(personRepository.findById(updatePerson.getId())).thenReturn(Optional.of(updatePerson));
        when(personRepository.save(updatePerson)).thenReturn(updatePerson);
        when(personMapper.personToPersonDto(updatePerson)).thenReturn(resultUpdate);


        //then
        PersonDto personDtoResult = personService.updatePerson(userUpdateDto);
        assertEquals(1L, personDtoResult.getId());
        assertEquals("Yura", personDtoResult.getFullName());
        assertEquals("reader293", personDtoResult.getTitle());
    }
    // get
    @Test
    @DisplayName("Выдать пользователя. Должно пройти успешно.")
    void getPerson_Test() {
        //given
        Long userId = 1L;

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        PersonDto result = new PersonDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");


        //when

        when(personRepository.findById(userId)).thenReturn(Optional.of(savedPerson));
        when(personMapper.personToPersonDto(savedPerson)).thenReturn(result);


        //then

        PersonDto userDtoResult = personService.getPersonById(userId);
        assertEquals(1L, userDtoResult.getId());
        assertEquals(11, userDtoResult.getAge());
        assertEquals("test name", userDtoResult.getFullName());
    }
    // delete
    @Test
    @DisplayName("Удалить пользователя. Должно пройти успешно.")
    void deletePerson_Test() {
        //given

        Long personId = 1L;

        //when
        doNothing().when(personRepository).deleteById(personId);

        //then
        personService.deletePersonById(personId);
    }

    // * failed

    @Test
    @DisplayName("Ошибка при выдаче пользователя. Должно пройти успешно.")
    void failedGetPerson_Test() {
        //given
        Long userId = 1L;

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        PersonDto result = new PersonDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");


        //when

        when(personRepository.findById(userId)).thenReturn(Optional.empty());
        when(personMapper.personToPersonDto(savedPerson)).thenReturn(result);


        //then

        assertThatThrownBy(() -> personService.getPersonById(userId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Person not found with id "+savedPerson.getId());
    }

    @Test
    @DisplayName("Ошибка при изменении пользователя. Должно пройти успешно.")
    void failedUpdatePerson_Test() {
        //given

        PersonDto userUpdateDto = new PersonDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setAge(18);
        userUpdateDto.setFullName("Yura");
        userUpdateDto.setTitle("reader293");

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        Person updatePerson  = new Person();
        updatePerson.setId(1L);
        updatePerson.setFullName("Yura");
        updatePerson.setAge(18);
        updatePerson.setTitle("reader293");

        PersonDto resultUpdate = new PersonDto();
        resultUpdate.setId(1L);
        resultUpdate.setFullName("Yura");
        resultUpdate.setAge(18);
        resultUpdate.setTitle("reader293");

        //when

        when(personMapper.personDtoToPerson(userUpdateDto)).thenReturn(updatePerson);
        when(personRepository.findById(updatePerson.getId())).thenReturn(Optional.empty());
        when(personRepository.save(updatePerson)).thenReturn(updatePerson);
        when(personMapper.personToPersonDto(updatePerson)).thenReturn(resultUpdate);


        //then
        assertThatThrownBy(() -> personService.updatePerson(userUpdateDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Person not found with id "+savedPerson.getId());
    }
}
