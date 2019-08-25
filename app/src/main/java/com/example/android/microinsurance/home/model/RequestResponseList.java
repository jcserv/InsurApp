package com.example.android.microinsurance.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestResponseList {
    @SerializedName("assets")
    private List<RequestResponse> assets;

    public RequestResponseList(List<RequestResponse> assets) {
        this.assets = assets;
    }

    public List<RequestResponse> getAssets() {
        return assets;
    }

    public void setAssets(List<RequestResponse> assets) {
        this.assets = assets;
    }
}
