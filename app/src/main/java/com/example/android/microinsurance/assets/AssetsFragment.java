package com.example.android.microinsurance.assets;
import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.microinsurance.R;
import com.example.android.microinsurance.common.NetworkBuilder;
import com.example.android.microinsurance.home.adapter.ResponseListAdapter;
import com.example.android.microinsurance.home.model.RequestResponse;
import com.example.android.microinsurance.home.network.HomeApi;
import com.example.android.microinsurance.home.network.HomeService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AssetsFragment extends Fragment {

    private ImageView capturePreview;
    private Toolbar cameraToolbar;
    private Button backButton;

    private RecyclerView responseList;
    private Single<List<RequestResponse>> requestListObservable;


    public static AssetsFragment newInstance() {
        AssetsFragment fragment = new AssetsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_assets, container, false);
        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        capturePreview = view.findViewById(R.id.capture_preview);
        cameraToolbar = view.findViewById(R.id.camera_toolbar);

        setupNetwork();

        setupToolbar();

        responseList = view.findViewById(R.id.assets_list);
        responseList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        decorateView();
    }

    private void setupToolbar() {
        cameraToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        cameraToolbar.setNavigationOnClickListener((view) -> {
            findNavController(this).navigate(R.id.action_assets_dest_to_home_dest);
        });
        cameraToolbar.setTitle("Assets");
    }

    private void showRequests(List<RequestResponse> requestResponseList) {
        ResponseListAdapter responseListAdapter = new ResponseListAdapter(requestResponseList);
        responseList.setAdapter(responseListAdapter);
    }

    private void setupNetwork() {
        HomeApi homeApi = new NetworkBuilder(getString(R.string.url_melonbun_api)).UseGson().useRxJava2().build(HomeApi.class);
        HomeService homeService = new HomeService(homeApi);
        requestListObservable = homeService.getResponses().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    private void decorateView() {
        requestListObservable.subscribeWith(new DisposableSingleObserver<List<RequestResponse>>() {
            @Override
            public void onSuccess(List<RequestResponse> requestResponses) {
                showRequests(requestResponses);
            }

            @Override
            public void onError(Throwable e) {
                //Error flow
            }
        });
    }


}
