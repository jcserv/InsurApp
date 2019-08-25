package com.example.android.microinsurance.home.model;

import com.google.gson.annotations.SerializedName;

public class InsuranceResponse {
    @SerializedName("premium")
    private int premium;

    @SerializedName("description")
    private String description;

    @SerializedName("name")
    private String name;

    public InsuranceResponse(int premium, String description, String name) {
        this.premium = premium;
        this.description = description;
        this.name = name;
    }

    public int getPremium() {
        return premium;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
