package com.example.springbootmongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;

@Setter
@Getter
@AllArgsConstructor
@Document(collection = "Transaction")
public class Transaction {
    @Id
    private ObjectId id;

    private String sender;
    private String receiver;
    private long amount;
    private String comment;

    public Transaction(long amount, String sender, String receiver, String comment) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.comment = comment;
    }

    public Transaction() {

    }
}




