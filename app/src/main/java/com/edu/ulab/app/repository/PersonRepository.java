package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}
