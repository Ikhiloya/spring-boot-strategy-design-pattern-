package com.ikhiloyaimokhai.springbootstrategydesignpattern.controller;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.model.FileDTO;
import com.ikhiloyaimokhai.springbootstrategydesignpattern.service.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class StorageController {

    private final Logger log = LoggerFactory.getLogger(StorageController.class);
    private StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping("/upload-file")
    @Transactional
    public ResponseEntity<FileDTO> uploadInvestigation(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("REST request to upload file");
        //upload files
        FileDTO fileDTO = storageService.uploadFile(file);
        return new ResponseEntity<>(fileDTO, null, HttpStatus.OK);
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        return storageService.downloadFile(fileName, request);
    }
}