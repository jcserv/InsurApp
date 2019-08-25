package com.example.android.microinsurance.camera.network;

import com.example.android.microinsurance.camera.model.ImageRequest;
import com.example.android.microinsurance.camera.model.ImageResponse;

import retrofit2.Call;

public class UploadService {
    private final UploadApi uploadApi;

    public UploadService(UploadApi uploadApi) {
        this.uploadApi = uploadApi;
    }

    public Call<ImageResponse> uploadImage(String encoded) {
        return uploadApi.uploadImage(encoded);
    }

    public Call<String> uploadImageUpdate(ImageRequest imageRequest) {
        return uploadApi.uploadImageUpdate(imageRequest);
    }
}
