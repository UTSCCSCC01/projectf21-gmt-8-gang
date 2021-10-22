package com.example.springbootmongodb.response;

import org.bson.types.ObjectId;
import lombok.Getter;
import lombok.Setter;


public class TransactionResponse {

    @Getter
    @Setter
    private ObjectId id;

    @Getter
    @Setter
    private long amount;

    @Getter
    @Setter
    private String sender;

    @Getter
    @Setter
    private String receiver;

    @Getter
    @Setter
    private String comment;

    public TransactionResponse(ObjectId id, long amount, String sender, String receiver, String comment) {
        this.id = id;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.comment = comment;
    }

    public TransactionResponse() {}

}
