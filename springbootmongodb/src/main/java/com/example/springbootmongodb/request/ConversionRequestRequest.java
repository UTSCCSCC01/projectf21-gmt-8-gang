package com.example.springbootmongodb.request;

public class ConversionRequestRequest {
    private String email;
    private Long amount;

    public ConversionRequestRequest() {
    }

    public ConversionRequestRequest(String email, Long amount) {
        this.email = email;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
