package com.cleantemplate.base.infrastructure.Security;

public class EncryptedResponse {

    private String data;

    public EncryptedResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}