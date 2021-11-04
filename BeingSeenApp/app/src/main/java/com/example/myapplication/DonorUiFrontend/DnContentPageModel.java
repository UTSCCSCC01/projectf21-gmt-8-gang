package com.example.myapplication.DonorUiFrontend;

public class DnContentPageModel {
    private String name, title, description, progress, percentage;
    private int current;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

//    public int getImg() { return img; }
//
//    public void setImg(int img) {
//        this.img = img;
//    }

//    public String getImg() { return img; }
//
//    public void setImg(String profileImage) {
//        this.img = profileImage;
//    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) { this.progress = progress; }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) { this.percentage = percentage; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

}
