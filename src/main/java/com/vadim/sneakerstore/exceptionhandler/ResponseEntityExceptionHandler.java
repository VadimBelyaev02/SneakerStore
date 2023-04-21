package com.vadim.sneakerstore.exceptionhandler;

import com.vadim.sneakerstore.exception.AccessDeniedException;
import com.vadim.sneakerstore.exception.AlreadyExistsException;
import com.vadim.sneakerstore.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleNotValidatedException(MethodArgumentNotValidException e) {
        ExceptionInfo info = new ExceptionInfo(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        ExceptionInfo info = new ExceptionInfo(e.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException e) {
        ExceptionInfo info = new ExceptionInfo(e.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(info, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        ExceptionInfo info = new ExceptionInfo(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleEverything(RuntimeException e) {
        String infoS = e.getMessage() + "\n" + e.getCause() + "\n" + e.getLocalizedMessage()
                + "\n" + e.getCause() + "\n" + e.getCause().getMessage();
        ExceptionInfo info = new ExceptionInfo(infoS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
    }
}
