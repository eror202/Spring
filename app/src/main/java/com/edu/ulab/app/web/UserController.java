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

    @GetMapping(value = "/get/{userId}")
    @Operation(summary = "Get user with books.")
    public PersonBookResponse updateUserWithBooks(@PathVariable Long userId) {
        PersonBookResponse response = personDataFacade.getUserWithBooks(userId);
        log.info("Response with user and his books: {}", response);
        return response;
    }

    @DeleteMapping(value = "/delete/{userId}")
    @Operation(summary = "Delete user with books. ")
    public void deletePersonWithBooks(@PathVariable Long userId) {
        log.info("Delete user and his books:  userId {}", userId);
        personDataFacade.deletePersonWithBooks(userId);
    }
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

    @DeleteMapping("/deleteBook/{bookId}")
    @Operation(summary = "delete book")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long bookId){
        log.info("Delete book in storage: {}");
        personDataFacade.deleteBook(bookId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
