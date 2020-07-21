package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.storage;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.config.FileStorageProperties;
import com.ikhiloyaimokhai.springbootstrategydesignpattern.error.FileStorageException;
import com.ikhiloyaimokhai.springbootstrategydesignpattern.error.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;


/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
@Service
public class FileStorageStrategy implements StorageStrategy {
    private final Logger log = LoggerFactory.getLogger(FileStorageStrategy.class);
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageStrategy(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        Files.createDirectories(fileStorageLocation);
    }


    @Override
    public String[] uploadFile(MultipartFile multipartFile) throws Exception {
        log.info("FileStorageStrategy==> uploading file");
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        // Check if the file's name contains invalid characters
        if (fileName.contains(".."))
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);

        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();

        return new String[]{fileUrl, fileName};
    }

    public ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception {
        log.info("FileStorageStrategy==> downloading file");
//        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        Path filePath = this.fileStorageLocation.resolve(fileUrl).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists())
            throw new ResourceNotFoundException("File not found " + fileUrl);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
