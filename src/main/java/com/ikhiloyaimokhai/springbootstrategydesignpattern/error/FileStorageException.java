package com.ikhiloyaimokhai.springbootstrategydesignpattern.error;

/**
 * Created by Ikhiloya Imokhai on 1/9/20.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
