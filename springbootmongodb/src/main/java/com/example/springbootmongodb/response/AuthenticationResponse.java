package com.example.springbootmongodb.response;

public class AuthenticationResponse {
    private String response;
    private String code;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String code, String response) {
        this.response = response;
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
