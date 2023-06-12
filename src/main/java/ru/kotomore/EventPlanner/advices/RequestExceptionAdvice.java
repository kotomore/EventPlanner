package ru.kotomore.EventPlanner.advices;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kotomore.EventPlanner.dto.MessageResponse;
import ru.kotomore.EventPlanner.exceptions.UserAlreadyExistException;
import ru.kotomore.EventPlanner.exceptions.UserRoleNotExistException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RequestExceptionAdvice {

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Object> requestNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> userAlreadyExistException(UserAlreadyExistException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UserRoleNotExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> userRoleNotExistException(UserRoleNotExistException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<MessageResponse> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            String message = "Поле " + fieldName + " - " + errorMessage;
            errorMessages.add(new MessageResponse(message));
        });
        return new ResponseEntity<>(errorMessages, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }
}
