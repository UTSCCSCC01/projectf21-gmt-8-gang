package com.example.springbootmongodb.request;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ConversionRequestRequest {
    private String email;
    private Long amount;
    private ObjectId conversionRequestId;
    private Boolean isDone;

    public ConversionRequestRequest() {
    }

    public ConversionRequestRequest(String email, Long amount) {
        this.email = email;
        this.amount = amount;
    }

    public ObjectId getConversionRequestId() {
        return conversionRequestId;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
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
