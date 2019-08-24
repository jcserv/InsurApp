package com.example.android.microinsurance.home.network;

import com.example.android.microinsurance.home.model.RequestResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface HomeApi {
    @GET("requests")
    Single<List<RequestResponse>> getRequests();
}
