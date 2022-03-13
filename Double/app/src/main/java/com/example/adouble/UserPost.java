package com.example.adouble;

import java.util.ArrayList;

public class UserPost {
    private String Name;
    private ArrayList<Post> userPost;

    public UserPost() {
    }

    public UserPost(String name, ArrayList<Post> userPost) {
        Name = name;
        this.userPost = userPost;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Post> getUserPost() {
        return userPost;
    }

    public void setUserPost(ArrayList<Post> userPost) {
        this.userPost = userPost;
    }
}
