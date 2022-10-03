package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.PersonDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
public class BookServiceImplTest {
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    @DisplayName("Создание книги. Должно пройти успешно.")
    void saveBook_Test() {
        //given
        PersonDto personDto  = new PersonDto();
        personDto.setId(1L);
        Person person = new Person();
        person.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setPerson(personDto);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setPerson(personDto);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.createBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
    }

    // update
    @Test
    @DisplayName("Изменение книги. Должно пройти успешно.")
    void updateBook_Test() {
        //given
        PersonDto personDto  = new PersonDto();
        personDto.setId(1L);

        Person person = new Person();
        person.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setPerson(personDto);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto bookDtoUpdate = new BookDto();
        bookDtoUpdate.setId(1L);
        bookDtoUpdate.setPerson(personDto);
        bookDtoUpdate.setAuthor("Yura");
        bookDtoUpdate.setTitle("update title");
        bookDtoUpdate.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setPerson(personDto);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        Book updateBook = new Book();
        updateBook.setId(1L);
        updateBook.setPageCount(1000);
        updateBook.setTitle("update title");
        updateBook.setAuthor("Yura");
        updateBook.setPerson(person);

        //when

        when(bookMapper.bookDtoToBook(bookDtoUpdate)).thenReturn(updateBook);
        when(bookRepository.save(updateBook)).thenReturn(updateBook);
        when(bookMapper.bookToBookDto(updateBook)).thenReturn(bookDtoUpdate);


        //then
        BookDto bookDtoResult = bookService.updateBook(bookDtoUpdate);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals("Yura", bookDtoResult.getAuthor());
        assertEquals("update book", bookDtoResult.getTitle());
    }
    // get
    @Test
    @DisplayName("Выдать книги. Должно пройти успешно.")
    void getBook_Test() {
        //given
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);

        Person person = new Person();
        person.setId(1L);

        Long bookId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setPerson(personDto);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(savedBook));
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.getBookById(bookId);
        assertEquals("test title", bookDtoResult.getTitle());
        assertEquals("test author", bookDtoResult.getAuthor());
    }
    // get all

    @Test
    @DisplayName("Выдать все книги по id юзера. Должно пройти успешно.")
    void getAllBook_Test() {
        //given
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);

        Person person = new Person();
        person.setId(1L);

        Long personId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setPerson(personDto);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        BookDto result2 = new BookDto();
        result2.setId(1L);
        result2.setPerson(personDto);
        result2.setAuthor("test author");
        result2.setTitle("test title");
        result2.setPageCount(1000);

        Book savedBook2 = new Book();
        savedBook2.setId(1L);
        savedBook2.setPageCount(1000);
        savedBook2.setTitle("test title");
        savedBook2.setAuthor("test author");
        savedBook2.setPerson(person);

        List<Book> books = new ArrayList<>();
        books.add(savedBook);
        books.add(savedBook2);
        //when

        when(bookRepository.findBookByPersonId(personId)).thenReturn(books);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);
        when(bookMapper.bookToBookDto(savedBook2)).thenReturn(result2);

        //then
        List<Long> bookDtoResult = bookService.getBookListByPersonId(personId);
        assertEquals(bookDtoResult.size(), 2);
        assertEquals(bookDtoResult.get(0), result);
    }
    // delete
    @Test
    @DisplayName("Удалить книгу. Должно пройти успешно.")
    void deleteBook_Test() {
        //given

        Long bookId = 1L;

        //when

        doNothing().when(bookRepository).deleteById(bookId);

        //then
        bookService.deleteBookById(bookId);
    }
    // * failed

    @Test
    @DisplayName("Ошибка при выдачи книги. Должно пройти успешно.")
    void failedGetBook_Test() {
        //given
        Person person  = new Person();
        person.setId(1L);

        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        Long bookId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setPerson(personDto);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookRepository.findById(bookId)).thenReturn(null);


        //then
        assertThatThrownBy(() -> bookService.getBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book with id: 1 not found");
    }
    // update
    // get
    // get all
    // delete

    // * failed
}
