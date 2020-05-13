package com.ikhiloyaimokhai.springbootstrategydesignpattern.error;

/**
 * Created by Ikhiloya Imokhai on 2019-12-18.
 */
public class ResourceNotFoundException extends GlobalException implements ErrorHandler {

    public ResourceNotFoundException(final String message) {
        super(message);
        this.message = message;
    }

    public ResourceNotFoundException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ResourceNotFoundException(ResponseEnum responseEnum) {
        super(responseEnum);
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    @Override
    public ResponseEnum getResponseEnum() {
        return ResponseEnum.RESOURCE_NOT_FOUND;
    }
}
