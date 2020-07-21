package com.ikhiloyaimokhai.springbootstrategydesignpattern.model;

public class FirebaseCredential {
    private String type;
    private String project_id;
    private String private_key_id;
    private String private_key;
    private String client_email;
    private String client_id;
    private String auth_uri;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_x509_cert_url;


    public String getType() {
        return type;
    }

    public String getProject_id() {
        return project_id;
    }

    public String getPrivate_key_id() {
        return private_key_id;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public String getClient_email() {
        return client_email;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getAuth_uri() {
        return auth_uri;
    }

    public String getToken_uri() {
        return token_uri;
    }

    public String getAuth_provider_x509_cert_url() {
        return auth_provider_x509_cert_url;
    }

    public String getClient_x509_cert_url() {
        return client_x509_cert_url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public void setPrivate_key_id(String private_key_id) {
        this.private_key_id = private_key_id;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setAuth_uri(String auth_uri) {
        this.auth_uri = auth_uri;
    }

    public void setToken_uri(String token_uri) {
        this.token_uri = token_uri;
    }

    public void setAuth_provider_x509_cert_url(String auth_provider_x509_cert_url) {
        this.auth_provider_x509_cert_url = auth_provider_x509_cert_url;
    }

    public void setClient_x509_cert_url(String client_x509_cert_url) {
        this.client_x509_cert_url = client_x509_cert_url;
    }
}
