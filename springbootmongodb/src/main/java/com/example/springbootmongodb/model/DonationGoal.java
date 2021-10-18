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
// @NoArgsConstructor
@Document(collection = "DonationGoals")
public class DonationGoal {

    @Id
    private ObjectId id;

    private String title;
    private String username;
    private String description;
    private Long goal;
    private Long current;

    public DonationGoal() {
    }

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername(){ return username;}

    public void setUsername(String username){ this.username = username;}

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