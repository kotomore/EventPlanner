package ru.kotomore.EventPlanner.advices;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kotomore.EventPlanner.dto.MessageResponse;
import ru.kotomore.EventPlanner.exceptions.ContractNotFoundException;
import ru.kotomore.EventPlanner.exceptions.ContractRequestAlreadySentException;
import ru.kotomore.EventPlanner.exceptions.EventNotFoundException;

@ControllerAdvice
public class UserExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ContractNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> contractNotFoundException(ContractNotFoundException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> eventNotFoundException(EventNotFoundException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ContractRequestAlreadySentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> contractRequestAlreadySentException(ContractRequestAlreadySentException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT);
    }
}