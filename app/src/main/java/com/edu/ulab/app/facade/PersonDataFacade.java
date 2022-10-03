package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.PersonDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.PersonMapper;

import com.edu.ulab.app.service.impl.BookServiceImpl;
import com.edu.ulab.app.service.impl.BookServiceImplTemplate;
import com.edu.ulab.app.service.impl.PersonServiceImpl;
import com.edu.ulab.app.service.impl.PersonServiceImplTemplate;
import com.edu.ulab.app.web.request.PersonBookRequest;
import com.edu.ulab.app.web.request.update.BookToListRequest;
import com.edu.ulab.app.web.request.update.UpdatedPersonBookRequest;
import com.edu.ulab.app.web.response.PersonBookResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class PersonDataFacade {
    private final PersonServiceImplTemplate personService;
    private final BookServiceImplTemplate bookService;
    private final PersonMapper personMapper;
    private final BookMapper bookMapper;

    /**
     * Method create person with books and return response with created data
     * @param personBookRequest
     * @return PersonBookResponse
     */
    public PersonBookResponse createUserWithBooks(PersonBookRequest personBookRequest) {
        log.info("Got user book create request: {}", personBookRequest);
        PersonDto personDto = personMapper.personRequestToPersonDto(personBookRequest.getPersonRequest());
        log.info("Mapped user request: {}", personDto);

        PersonDto createdPerson = personService.createPerson(personDto);
        log.info("Created user: {}", createdPerson);

        List<Long> bookIdList = personBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setPerson(createdPerson))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return PersonBookResponse.builder()
                .personId(createdPerson.getId())
                .booksIdList(bookIdList)
                .build();
    }

    /**
     * Method update person with books and response with updated data
     * @param personBookRequest
     * @return PersonBookResponse
     */
    public PersonBookResponse updateUserWithBooks(UpdatedPersonBookRequest personBookRequest) {
        PersonDto personDto = personMapper.updatedPersonRequestToPersonDto(personBookRequest.getPersonRequest());
        PersonDto updatedPerson = personService.updatePerson(personDto);
        log.info("Updated user: {}", updatedPerson);

        if (personBookRequest.getBookRequests() == null){
            List<Long> bookIdList = bookService.getBookListByPersonId(updatedPerson.getId());
            return PersonBookResponse.builder()
                    .personId(personDto.getId())
                    .booksIdList(bookIdList)
                    .build();
        }
        List<Long> bookIdList = personBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::updatedBookRequestToBookDto)
                .peek(bookDto -> bookDto.setPerson(updatedPerson))
                .peek(mappedBookDto -> log.info("mapped Book: {}", mappedBookDto))
                .map(bookService::updateBook)
                .peek(updateBook -> log.info("update Book: {}", updateBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return PersonBookResponse.builder()
                .personId(personDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    /**
     * Method create response with person and his books
     * @param userId
     * @return PersonBookResponse
     */
    public PersonBookResponse getUserWithBooks(Long userId) {
        PersonDto userDto = personService.getPersonById(userId);
        List<Long> bookIdList = bookService.getBookListByPersonId(userId).stream().filter(Objects::nonNull).toList();
        log.info("Collected book ids: {}", bookIdList);
        return PersonBookResponse.builder()
                .personId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    /**
     * Method delete person with books by person id
     * @param personId
     */
    public void deletePersonWithBooks(Long personId) {
        log.info("Deleted person with books: {}");
        bookService.getBookListByPersonId(personId).forEach(bookService::deleteBookById);
        personService.deletePersonById(personId);
        log.info("Delete is completed: {}");
    }

    /**
     * Method ad book to person list by personId and create response with updated data
     * @param bookToListRequest
     * @return PersonBookResponse
     */
    public PersonBookResponse addBookToPersonList(BookToListRequest bookToListRequest){
        BookDto bookToCreate = bookMapper.bookRequestToBookDto(bookToListRequest);
        PersonDto personDto = personService.getPersonById(bookToListRequest.getPersonId());
        bookToCreate.setPerson(personDto);
        BookDto createdBook = bookService.createBook(bookToCreate);
        log.info("New book in person's list: {}", createdBook);
        List<Long> bookIdList = bookService.getBookListByPersonId(bookToListRequest.getPersonId())
                .stream()
                .filter(Objects::nonNull)
                .toList();
        log.info("Collected updated user's list: {}", bookIdList);
        return PersonBookResponse.builder()
                .personId(personDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    /**
     * Method delete book by id
     * @param bookId
     */
    public void deleteBook(Long bookId){
        log.info("Delete book in book's storage");
        bookService.deleteBookById(bookId);
    }
}
