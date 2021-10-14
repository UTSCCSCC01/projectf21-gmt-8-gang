package com.example.springbootmongodb.response;

public class DonationGoalResponse {
    private String title;
    private String owner;
    private String description;
    private Long goal;
    private Long current;

    public DonationGoalResponse(String title, String description, Long goal , Long current) {
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.current = current;
    }

    public DonationGoalResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner(){ return owner;}

    public void setOwner(String owner){ this.owner = owner;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getCurrent(){ return current;}

    public void setCurrent(Long current){ this.current = current;}
}
