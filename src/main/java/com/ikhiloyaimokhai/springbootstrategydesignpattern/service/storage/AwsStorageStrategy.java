package com.ikhiloyaimokhai.springbootstrategydesignpattern.service.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Ikhiloya Imokhai on 5/7/20.
 */
@Service
public class AwsStorageStrategy implements StorageStrategy {
    private final Logger log = LoggerFactory.getLogger(AwsStorageStrategy.class);
    private final Environment environment;

    private AmazonS3 s3client;
    @Value("${cloud.aws.endpointurl}")
    private String endpointUrl;

    @Value("${cloud.aws.region.static}")
    private String region;
    //    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    public AwsStorageStrategy(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    private void initializeAmazon() {
        String accessKey = environment.getRequiredProperty("AWS_ACCESS_KEY_ID");
        String secretKey = environment.getRequiredProperty("AWS_SECRET_ACCESS_KEY");

        bucketName = environment.getRequiredProperty("S3_BUCKET_NAME");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }


    @Override
    public String[] uploadFile(MultipartFile multipartFile) throws IOException {
        log.info("AWSStorageStrategy==> uploading file");
        log.info("uploading files to S3");
        String fileUrl = "";
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        fileUrl = endpointUrl + "/" + fileName;
        uploadFileTos3bucket(fileName, file);
        boolean delete = file.delete();

        return new String[]{fileUrl, fileName};
    }

    @Override
    public ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception {
        log.info("AWSStorageStrategy==> downloading file");
        byte[] content = null;
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        S3Object resource = s3client.getObject(bucketName, fileName);
        final S3ObjectInputStream stream = resource.getObjectContent();
        content = IOUtils.toByteArray(stream);
        log.info("File downloaded successfully.");
        resource.close();


        final ByteArrayResource byteArrayResource = new ByteArrayResource(content);
        return ResponseEntity
                .ok()
                .contentLength(content.length)
                .header("Content-type", "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(byteArrayResource);
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        PutObjectResult putObjectResult = s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

}
