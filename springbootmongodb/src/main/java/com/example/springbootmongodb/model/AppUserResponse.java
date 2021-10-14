package com.example.springbootmongodb.model;

public class AppUserResponse {
    private String userName;

    private String role;
    private String profileInfo;
    private Long balance;

    public AppUserResponse(String userName, String role, String profileInfo, Long balance) {
        this.userName = userName;
        this.role = role;
        this.profileInfo = profileInfo;
        this.balance = balance;
    }

    public AppUserResponse() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void increaseBalance(long amount){this.balance+=amount;}
}
