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
@Document(collection = "AppUser")
public class AppUser {

    @Id
    private ObjectId id;

    private String userName;

    private String role;
    private String profileInfo;
    private Long balance;

    public AppUser(String userName, String role, String profileInfo, Long balance) {
        this.userName = userName;
        this.role = role;
        this.profileInfo = profileInfo;
        this.balance = balance;
    }

    public AppUser() {}
    
}