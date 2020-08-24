package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UsersServiceException.class})
    protected ResponseEntity<Object> handleUsersServiceException(UsersServiceException ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.toString(), HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleEx(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage("Invalid Params Passed",
                ex.getLocalizedMessage()==null?ex.getMessage():ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/

}
