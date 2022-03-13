package com.cli.dynoadmin;

public class Post {
    private String title;
    private String description;
    private String completeNews;
    private String imageUrl;

    public Post() {
    }

    public Post(String title, String description, String completeNews, String imageUrl) {
        this.title = title;
        this.description = description;
        this.completeNews = completeNews;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompleteNews() {
        return completeNews;
    }

    public void setCompleteNews(String completeNews) {
        this.completeNews = completeNews;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
