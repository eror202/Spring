package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.PersonDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.info("Save book to storage: {}", bookDto);
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, PERSON_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getPersonId());
                    return ps;
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Save is complete");
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        final String UPDATE_SQL = "Update Book set title=?, author = ?, PAGE_COUNT = ? where id =?";
        BookDto bookToUpdate = getBookById(bookDto.getId());
        log.info("Book to update: {}", bookToUpdate);
        jdbcTemplate.update(UPDATE_SQL, bookDto.getTitle(), bookDto.getAuthor(), bookDto.getPageCount(), bookDto.getId());
        log.info("Update is complete");
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        final String SELECT_SQL = "Select * from Book where id = ?";
        BookDto bookDto = jdbcTemplate.query(SELECT_SQL, new BeanPropertyRowMapper<>(BookDto.class), new Object[]{id})
                .stream().findAny().orElse(null);
        if (bookDto == null) {
            throw new NotFoundException("Book not found with id " + id);
        }
        log.info("Get book in storage with id " + id + " : {}", bookDto);
        return bookDto;
    }

    @Override
    public void deleteBookById(Long id) {
        final String DELETE_SQL = "DELETE FROM Book where id = ?";
        BookDto bookToDelete = getBookById(id);
        log.info("Book to delete: {}", bookToDelete);
        jdbcTemplate.update(DELETE_SQL, id);
        log.info("Delete completed: {}");
    }

    @Override
    public List<Long> getBookListByPersonId(Long id) {
        final String SELECT_SQL = "select * from Book where person_id=?";
        List<Book> bookList = jdbcTemplate.query(SELECT_SQL
                , new BeanPropertyRowMapper<>(Book.class), new Object[]{id}).stream().toList();
        List<Long> idBookList = bookList.stream().map(book -> book.getId()).collect(Collectors.toList());
        log.info("Get user's book list where userId=" + id + " : {}", bookList);
        return idBookList;
    }
}
