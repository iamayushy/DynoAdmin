package com.cli.login;

import java.util.ArrayList;

public class list {

    private String status;
    private int totalResults;
    private ArrayList<articles> articles;

    public list(String status, int totalResults, ArrayList<com.cli.login.articles> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<com.cli.login.articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<com.cli.login.articles> articles) {
        this.articles = articles;
    }
}
