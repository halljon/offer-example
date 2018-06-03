package io.halljon.offerexample.offer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {DataIntegrityViolationException.class, RuntimeException.class})
    protected ResponseEntity<Object> handleExceptions(RuntimeException e) {
        LOGGER.error("Exception occurred, but caught and suppressed from returning: {}", e.getMessage(), e);

        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }
}
