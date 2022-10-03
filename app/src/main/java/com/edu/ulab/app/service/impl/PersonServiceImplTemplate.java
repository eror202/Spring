package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.PersonDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@Service
public class PersonServiceImplTemplate implements PersonService {
    private final JdbcTemplate jdbcTemplate;

    public PersonServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        log.info("Save person to storage: {}", personDto);
        final String INSERT_SQL = "INSERT INTO ULAB_EDU.PERSON(FULL_NAME, TITLE, AGE, COUNT) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, personDto.getFullName());
                    ps.setString(2, personDto.getTitle());
                    ps.setLong(3, personDto.getAge());
                    ps.setInt(4, 0);
                    return ps;
                }, keyHolder);

        personDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Save is complete");
        return personDto;
    }

    @Override
    public PersonDto updatePerson(PersonDto personDto) {
        final String UPDATE_SQL = "UPDATE ULAB_EDU.Person SET full_name=?, title = ?, age = ? where id = ?";
        PersonDto personToUpdate = getPersonById(personDto.getId());
        log.info("Person to update: {}", personToUpdate);
        jdbcTemplate.update(UPDATE_SQL,
                personDto.getFullName(), personDto.getTitle(), personDto.getAge(), personDto.getId());
        log.info("Update is complete");
        return personDto;
    }

    @Override
    public PersonDto getPersonById(Long id) {
        final String SELECT_SQL = "select * from ULAB_EDU.Person where id=?";
        PersonDto person = jdbcTemplate.query(SELECT_SQL,new BeanPropertyRowMapper<>(PersonDto.class), new Object[]{id} )
                .stream().findAny().orElse(null);
        if (person == null){
            throw new NotFoundException("Person not found with id: "+id);
        }
        log.info("Get person in storage with id "+id+" : {}", person);
        return person;
    }

    @Override
    public void deletePersonById(Long id) {
        final String DELETE_SQL = "DELETE FROM ULAB_EDU.Person where id = ?";
        jdbcTemplate.update(DELETE_SQL, id);
        log.info("Delete completed: {}");
    }
}
