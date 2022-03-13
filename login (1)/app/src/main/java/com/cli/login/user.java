package com.cli.login;

public class user {

    public String fullName, email, password, bPost;

    public user(String bPost) {
        this.bPost = bPost;
    }

    public user(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public user() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getbPost() {
        return bPost;
    }

    public void setbPost(String bPost) {
        this.bPost = bPost;
    }

    public user(String fullName, String email, String password, String bPost) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.bPost = bPost;

    }

}
