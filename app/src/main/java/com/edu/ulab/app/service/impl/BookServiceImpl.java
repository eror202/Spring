package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    /**
     * Method save book in database
     * @param bookDto
     * @return savedBookDto
     */
    @Override
    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookToBookDto(savedBook);
    }

    /**
     * Method update book in database
     * @param bookDto
     * @return updatedBookDto
     */
    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        Book bookToUpdate = bookMapper.bookDtoToBook(bookDto);
        log.info("Book to update: {}", bookToUpdate);
        Book updatedBook = bookRepository.save(bookToUpdate);
        log.info("Updated book: {}", updatedBook);
        return bookMapper.bookToBookDto(updatedBook);
    }

    /**
     * Method get book by id in database
     * @param id
     * @return bookDto
     */
    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found with id "+id));
        return bookMapper.bookToBookDto(book);
    }

    /**
     * Method delete book by id
     * @param id
     */
    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
        log.info("Delete is completed");
    }

    /**
     * Method get all books by personId in database
     * @param personId
     * @return List<Long>
     */
    @Override
    public List<Long> getBookListByPersonId(Long personId) {
        List<Book> bookList = bookRepository.findBookByPersonId(personId);
        List<Long> longBookList = bookList.stream().map(book -> book.getId()).collect(Collectors.toList());
        log.info("Get person's book list where userId="+personId+" : {}", longBookList);
        return longBookList;
    }
}
