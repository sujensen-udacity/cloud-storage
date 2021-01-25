package com.udacity.jwdnd.course1.cloudstorage.model;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;

public class CredForm {

    private String credentialId;
    private String credUrl;
    private String credUsername;
    private String credPassword;
    private String credKey;
    private Integer userId;

    public CredForm(String credId, String credUrl, String credUsername, String credKey, String credPassword, Integer userId) {
        this.credentialId = credId;
        this.credUrl = credUrl;
        this.credUsername = credUsername;
        this.credPassword = credPassword;
        this.credKey = credKey;
         this.userId = userId;
    }


    public String getCredKey() {
        return credKey;
    }

    public void setCredKey(String credKey) {
        this.credKey = credKey;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredUrl() {
        return credUrl;
    }

    public void setCredUrl(String credUrl) {
        this.credUrl = credUrl;
    }

    public String getCredUsername() {
        return credUsername;
    }

    public void setCredUsername(String credUsername) {
        this.credUsername = credUsername;
    }

    public String getCredPassword() {
        return credPassword;
    }

    public void setCredPassword(String credPassword) {
        this.credPassword = credPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
