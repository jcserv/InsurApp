package com.example.android.microinsurance.camera.network;

import com.example.android.microinsurance.camera.model.ImageRequest;
import com.example.android.microinsurance.camera.model.ImageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadApi {

    @POST("sendImage")
    Call<ImageResponse> uploadImage(@Body String encoded);

    @POST("addAsset")
    Call<String> uploadImageUpdate(@Body ImageRequest imageRequest);
}
