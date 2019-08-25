package com.example.android.microinsurance.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InsuranceResponseList {
    @SerializedName("policies")
    private List<InsuranceResponse> policies;

    public InsuranceResponseList(List<InsuranceResponse> policies) {
        this.policies = policies;
    }

    public List<InsuranceResponse> getPolicies() {
        return policies;
    }

    public void setPolicies(List<InsuranceResponse> policies) {
        this.policies = policies;
    }
}
