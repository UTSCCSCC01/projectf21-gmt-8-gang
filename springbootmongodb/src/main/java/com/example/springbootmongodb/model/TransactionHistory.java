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
@Document(collection = "TransactionHistory")
public class TransactionHistory {
    @Id
    private ObjectId id;

    private String transactionId;

    private long amount;
    private String type;
    private String state;
    private String sender;
    private String receiver;
    private String senderNickname;
    private String receiverNickname;
    private Time time;
    private String message;

    public TransactionHistory(String transactionId, long amount, String type, String sender, String receiver,
                              String senderNickname, String receiverNickname, Time time, String message){
        this.transactionId=transactionId;
        this.amount=amount;
        this.type=type;
        this.sender=sender;
        this.receiver=receiver;
        this.senderNickname=senderNickname;
        this.receiverNickname=receiverNickname;
        this.time=time;
        this.message=message;
    }





    public TransactionHistory() {}
}
