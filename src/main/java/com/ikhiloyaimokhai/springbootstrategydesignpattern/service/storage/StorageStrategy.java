package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
public interface StorageStrategy {

    String[] uploadFile(MultipartFile multipartFile) throws Exception;

    ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception;

}
