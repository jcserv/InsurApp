package com.example.android.microinsurance.home.network;

import com.example.android.microinsurance.home.model.InsuranceResponseList;
import com.example.android.microinsurance.home.model.RequestResponseList;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface HomeApi {
    @GET("getTop4Assets")
    Single<RequestResponseList> getRequests();

    @GET("get4Recommendations")
    Single<InsuranceResponseList> getRecommendations();
}
