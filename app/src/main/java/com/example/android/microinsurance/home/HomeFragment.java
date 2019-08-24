package com.example.android.microinsurance.home;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.microinsurance.R;
import com.example.android.microinsurance.common.NetworkBuilder;
import com.example.android.microinsurance.home.adapter.ResponseListAdapter;
import com.example.android.microinsurance.home.model.RequestResponse;
import com.example.android.microinsurance.home.network.HomeApi;
import com.example.android.microinsurance.home.network.HomeService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private RecyclerView responseList;
    private Single<List<RequestResponse>> requestListObservable;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView homeToolbarBackground;
    private AppBarLayout appBarLayout;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_home, container, false);
        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        homeToolbarBackground = (ImageView) view.findViewById(R.id.home_toolbar_background);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appBar_layout);

        setupNetwork();

        setupAppBar();

        responseList = view.findViewById(R.id.home_list);
        responseList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        decorateView();
    }

    private void setupAppBar() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.title_home));
                    isShow = true;
                } else if (isShow) {
                    int timeStamp = Integer.parseInt(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()));

                    String greetingMessage = "";
                    Drawable drawable = null;
                    if (timeStamp >= 0 && timeStamp < 5) {
                        greetingMessage = getString(R.string.greeting_evening);
                        drawable = getResources().getDrawable(R.drawable.midnight);
                    } else if (timeStamp >= 5 && timeStamp < 13) {
                        greetingMessage = getString(R.string.greeting_morning);
                        drawable = getResources().getDrawable(R.drawable.morning);
                    } else if (timeStamp >= 13 && timeStamp < 19) {
                        greetingMessage = getString(R.string.greeting_afternoon);
                        drawable = getResources().getDrawable(R.drawable.afternoon);
                    } else if (timeStamp >= 19 && timeStamp <= 23) {
                        greetingMessage = getString(R.string.greeting_evening);
                        drawable = getResources().getDrawable(R.drawable.night);
                    }

                    collapsingToolbarLayout.setTitle(greetingMessage + " Bob");// use " " even if you want empty
                    homeToolbarBackground.setImageDrawable(drawable);
                    isShow = false;
                }
            }
        });

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
