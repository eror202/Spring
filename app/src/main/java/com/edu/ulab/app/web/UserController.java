package com.edu.ulab.app.web;

import com.edu.ulab.app.facade.PersonDataFacade;
import com.edu.ulab.app.web.constant.WebConstant;
import com.edu.ulab.app.web.request.PersonBookRequest;
import com.edu.ulab.app.web.request.update.BookToListRequest;
import com.edu.ulab.app.web.request.update.UpdatedPersonBookRequest;
import com.edu.ulab.app.web.response.PersonBookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

import static com.edu.ulab.app.web.constant.WebConstant.REQUEST_ID_PATTERN;
import static com.edu.ulab.app.web.constant.WebConstant.RQID;

@Slf4j
@RestController
@RequestMapping(value = WebConstant.VERSION_URL + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final PersonDataFacade personDataFacade;

    public UserController(PersonDataFacade personDataFacade) {
        this.personDataFacade = personDataFacade;
    }

    /**
     * Controller for created person with books and return response
     * @param request
     * @param requestId
     * @return response with created data
     */
    @PostMapping(value = "/create")
    @Operation(summary = "Create user book row.",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonBookResponse.class)))})
    public PersonBookResponse createUserWithBooks(@RequestBody PersonBookRequest request,
                                                  @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        PersonBookResponse response = personDataFacade.createUserWithBooks(request);
        log.info("Response with created user and his books: {}", response);
        return response;
    }

    /**
     * Controller for updated person with books and return response
     * @param request
     * @return response with updated data
     */
    @PutMapping(value = "/update")
    @Operation(summary = "update user with books. ",
            responses = {
                    @ApiResponse(description = "User book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PersonBookResponse.class)))})
    public PersonBookResponse updateUserWithBooks(@RequestBody UpdatedPersonBookRequest request) {
        PersonBookResponse response = personDataFacade.updateUserWithBooks(request);
        log.info("Response with updated user and his books: {}", response);
        return response;
    }

    /**
     * Controller for getting person with books
     * @param personId
     * @return person with his book list
     */
    @GetMapping(value = "/get/{personId}")
    @Operation(summary = "Get user with books.")
    public PersonBookResponse getUserWithBooks(@PathVariable Long personId) {
        PersonBookResponse response = personDataFacade.getUserWithBooks(personId);
        log.info("Response with user and his books: {}", response);
        return response;
    }

    /**
     * Controller for deleted person with his books
     * @param personId
     * @return HttpStatus
     */
    @DeleteMapping(value = "/delete/{personId}")
    @Operation(summary = "Delete user with books. ")
    public ResponseEntity<HttpStatus> deletePersonWithBooks(@PathVariable Long personId) {
        log.info("Delete user and his books:  userId {}", personId);
        personDataFacade.deletePersonWithBooks(personId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Controller for adding book to person
     * @param book
     * @return response with updated data
     */
    @PutMapping("/updateBookList")
    @Operation(summary = "update user's book list. ",
            responses = {
                    @ApiResponse(description = "Book",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookToListRequest.class)))})
    public PersonBookResponse addBookToBookList(@RequestBody BookToListRequest book){
        PersonBookResponse response = personDataFacade.addBookToPersonList(book);
        log.info("Response user with updated book list: {}", response);
        return response;
    }

    /**
     * Controller for deleted book
     * @param bookId
     * @return HttpStatus
     */
    @DeleteMapping("/deleteBook/{bookId}")
    @Operation(summary = "delete book")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long bookId){
        log.info("Delete book in storage: {}");
        personDataFacade.deleteBook(bookId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
