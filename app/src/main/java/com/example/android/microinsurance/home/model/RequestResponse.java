package com.example.android.microinsurance.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RequestResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String title;
    @SerializedName("description")
    private String body;
    @SerializedName("created_at")
    private String date;
    @SerializedName("created_by")
    private String userName;
    @SerializedName("status")
    private String status;
    @SerializedName("tags")
    private List<String> tags;
    @SerializedName("fav_state")
    private Boolean favState;

    public RequestResponse(String id, String title, String body, String date, String userName, String status, List<String> tags, Boolean favState) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.userName = userName;
        this.status = status;
        this.tags = tags;
        this.favState = favState;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getTags() {
        return tags == null ? new ArrayList<>() : tags;
    }

    public Boolean getFavState() {
        return favState == null ? false : favState;
    }

    public void setFavState(Boolean favState) {
        this.favState = favState;
    }
}

