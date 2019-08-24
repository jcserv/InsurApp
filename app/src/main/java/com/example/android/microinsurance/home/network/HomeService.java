package com.example.android.microinsurance.home.network;

import com.example.android.microinsurance.home.model.RequestResponse;

import java.util.List;

import io.reactivex.Single;

public class HomeService {

    private final HomeApi homeApi;

    public HomeService(HomeApi homeApi) {
        this.homeApi = homeApi;
    }

    public Single<List<RequestResponse>> getResponses() {
        return homeApi.getRequests();
    }
}
