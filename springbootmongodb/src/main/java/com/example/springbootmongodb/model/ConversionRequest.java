package com.example.springbootmongodb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@Document(collection = "ConversionRequest")
public class ConversionRequest {
    @Id
    private ObjectId id;

    private String username;
    private String email;
    private Long amount;
    private Boolean isDone; // 1 if done, 0 if pending

    public ConversionRequest() {
    }

    public ConversionRequest(String username, String email, Long amount, Boolean isDone) {
        this.username = username;
        this.email = email;
        this.amount = amount;
        this.isDone = isDone;
    }

    public ObjectId getId() {
        return id;
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
