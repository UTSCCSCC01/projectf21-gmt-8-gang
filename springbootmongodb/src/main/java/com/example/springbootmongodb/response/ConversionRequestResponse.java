package com.example.springbootmongodb.response;

import org.bson.types.ObjectId;

public class ConversionRequestResponse {

    private String requestId;
    private String username;
    private String email;
    private Long amount;
    private Boolean isDone; // 1 if done, 0 if pending

    public ConversionRequestResponse(String requestId, String username, String email, Long amount, Boolean isDone) {
        this.requestId = requestId;
        this.username = username;
        this.email = email;
        this.amount = amount;
        this.isDone = isDone;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    public void setIsDone(Boolean done) {
        isDone = done;
    }

    public boolean getIsDone() {
        return this.isDone;
    }
}
