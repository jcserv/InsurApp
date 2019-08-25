package com.example.android.microinsurance.home.network;

import com.example.android.microinsurance.home.model.InsuranceResponseList;
import com.example.android.microinsurance.home.model.RequestResponse;
import com.example.android.microinsurance.home.model.RequestResponseList;

import java.util.List;

import io.reactivex.Single;

public class HomeService {

    private final HomeApi homeApi;

    public HomeService(HomeApi homeApi) {
        this.homeApi = homeApi;
    }

    public Single<RequestResponseList> getResponses() {
        return homeApi.getRequests();
    }

    public Single<InsuranceResponseList> getRecommendations() {
        return homeApi.getRecommendations();
    }
}
