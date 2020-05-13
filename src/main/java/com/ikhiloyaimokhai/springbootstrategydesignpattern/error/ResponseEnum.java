package com.ikhiloyaimokhai.springbootstrategydesignpattern.error;

/**
 * Created by Ikhiloya Imokhai on 2019-12-18.
 */
public enum ResponseEnum {

    INTERNAL_SERVER_ERROR("96"),
    EMAIL_NOT_FOUND("92"),
    FILE_STORAGE_EXCEPTION("93"),
    INVALID_PASSWORD("94"),
    EMAIL_ALREADY_USED("93"),
    BAD_REQUEST("97"),
    RESOURCE_NOT_FOUND("98"),
    DUPLICATE_REQUEST("95"),
    ALL_EXCEPTION("99");

    private String code;
    private String message;


    ResponseEnum(String code) {
        this.code = code;
    }

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
