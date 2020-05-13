package com.ikhiloyaimokhai.springbootstrategydesignpattern.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Ikhiloya Imokhai on 2019-12-18.
 */
@Service
public class ResponseUtil {

    public ResponseEntity<?> getError(String errorCode, String errorMessage, HttpStatus httpStatus) {
        ApiError error = new ApiError(errorCode, errorMessage);
        return new ResponseEntity<>(error, httpStatus);
    }

    public ResponseEntity<?> getError(ResponseEnum responseEnum, HttpStatus httpStatus) {
        ApiError error = new ApiError(responseEnum.getCode(), responseEnum.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }


}
