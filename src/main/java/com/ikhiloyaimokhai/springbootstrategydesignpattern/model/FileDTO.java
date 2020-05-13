package com.ikhiloyaimokhai.springbootstrategydesignpattern.model;

public class FileDTO {
    private String fileName;
    private String contentType;
    private String fileDownloadUri;
    private Long fileSize;

    public FileDTO() {
    }

    public FileDTO(String fileName, String contentType, String fileDownloadUri, Long fileSize) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileDownloadUri = fileDownloadUri;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileDownloadUri='" + fileDownloadUri + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
