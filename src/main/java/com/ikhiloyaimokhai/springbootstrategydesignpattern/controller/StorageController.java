package com.ikhiloyaimokhai.springbootstrategydesignpattern.controller;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.model.FileDTO;
import com.ikhiloyaimokhai.springbootstrategydesignpattern.service.storage.FileStorageService;
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
    private FileStorageService fileStorageService;

    public StorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }


    @PostMapping("/upload-file")
    @Transactional
    public ResponseEntity<FileDTO> uploadInvestigation(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("REST request to upload file");
        //upload files
        FileDTO fileDTO = fileStorageService.uploadFile(file);
        return new ResponseEntity<>(fileDTO, null, HttpStatus.OK);
    }


    @PostMapping("/download-file")
    public ResponseEntity<Object> downloadFile(@RequestBody FileDTO fileDTO, HttpServletRequest request) throws Exception {
        return fileStorageService.downloadFile(fileDTO.getFileDownloadUri(), request);
    }

}