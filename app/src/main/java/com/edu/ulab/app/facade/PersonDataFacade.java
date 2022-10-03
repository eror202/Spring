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
                .peek(bookDto -> bookDto.setPersonId(createdPerson.getId()))
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
                .peek(bookDto -> bookDto.setPersonId(updatedPerson.getId()))
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

    public PersonBookResponse getUserWithBooks(Long userId) {
        PersonDto userDto = personService.getPersonById(userId);
        List<Long> bookIdList = bookService.getBookListByPersonId(userId).stream().filter(Objects::nonNull).toList();
        log.info("Collected book ids: {}", bookIdList);
        return PersonBookResponse.builder()
                .personId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deletePersonWithBooks(Long userId) {
        log.info("Deleted person with books: {}");
        personService.deletePersonById(userId);
        bookService.getBookListByPersonId(userId).forEach(bookService::deleteBookById);
        log.info("Delete is completed: {}");
    }
    public PersonBookResponse addBookToPersonList(BookToListRequest bookToListRequest){
        BookDto bookDto = bookService.createBook(bookMapper.bookRequestToBookDto(bookToListRequest));
        log.info("New book in person's list: {}", bookDto);
        PersonDto personDto = personService.getPersonById(bookToListRequest.getPersonId());
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
    public void deleteBook(Long bookId){
        log.info("Delete book in book's storage");
        bookService.deleteBookById(bookId);
    }
}
