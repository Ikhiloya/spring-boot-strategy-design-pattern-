package com.ikhiloyaimokhai.springbootstrategydesignpattern.error;

/**
 * Created by Ikhiloya Imokhai on 2019-12-18.
 */
public class GlobalException extends Exception {

    protected String code;
    protected String message;

    public GlobalException(final String message) {
        super(message);
        this.message = message;
    }

    public GlobalException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }


    public GlobalException(ResponseEnum responseEnum) {
//        super(message);
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
