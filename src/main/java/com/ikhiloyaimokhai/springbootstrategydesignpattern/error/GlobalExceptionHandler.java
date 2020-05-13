package com.ikhiloyaimokhai.springbootstrategydesignpattern.error;

/**
 * Created by Ikhiloya Imokhai on 2019-12-18.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //    @Autowired
    private final ResponseUtil responseUtil;

    public GlobalExceptionHandler(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        return responseUtil.getError(ex.getResponseEnum().getCode(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleFileStorageException(FileStorageException ex) {
        return responseUtil.getError(ResponseEnum.FILE_STORAGE_EXCEPTION.getCode(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleAll(Exception ex) {
        String exceptionMessage = "";

        //Handle All Field Validation Errors
        if (ex instanceof MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
                sb.append(";");
            }
            exceptionMessage = sb.toString();
        } else {
            exceptionMessage = ex.getLocalizedMessage();
        }


        return responseUtil.getError(ResponseEnum.ALL_EXCEPTION.getCode(), exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
