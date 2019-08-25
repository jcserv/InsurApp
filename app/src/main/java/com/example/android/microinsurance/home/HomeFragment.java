package com.example.android.microinsurance.home;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.microinsurance.R;
import com.example.android.microinsurance.common.NetworkBuilder;
import com.example.android.microinsurance.home.model.InsuranceResponseList;
import com.example.android.microinsurance.home.model.RequestResponseList;
import com.example.android.microinsurance.home.network.HomeApi;
import com.example.android.microinsurance.home.network.HomeService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class HomeFragment extends Fragment {

    private Single<RequestResponseList> assetObservable;
    private Single<InsuranceResponseList> recommendationsObservable;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView homeToolbarBackground;
    private AppBarLayout appBarLayout;
    private FloatingActionButton fab;


    private TextView title;
    private TextView category;
    private TextView value;
    private TextView date;

    private TextView title1;
    private TextView category1;
    private TextView value1;
    private TextView date1;

    private TextView title2;
    private TextView category2;
    private TextView value2;
    private TextView date2;

    private TextView title3;
    private TextView category3;
    private TextView value3;
    private TextView date3;

    MaterialCardView materialCard4;
    private TextView title4;
    private TextView category4; //Description
    private TextView value4;
    private TextView date4;

    MaterialCardView materialCard5;
    private TextView title5;
    private TextView category5; //Description
    private TextView value5;
    private TextView date5;

    MaterialCardView materialCard6;
    private TextView title6;
    private TextView category6; //Description
    private TextView value6;
    private TextView date6;

    MaterialCardView materialCard7;
    private TextView title7;
    private TextView category7; //Description
    private TextView value7;
    private TextView date7;

    private TextView seeMoreInsurance;

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

        title = view.findViewById(R.id.item_card_title);
        category = view.findViewById(R.id.item_card_category);
        value = view.findViewById(R.id.item_card_value);
        date = view.findViewById(R.id.item_card_date);

        title1 = view.findViewById(R.id.item_card_title1);
        category1 = view.findViewById(R.id.item_card_category1);
        value1 = view.findViewById(R.id.item_card_value1);
        date1 = view.findViewById(R.id.item_card_date1);

        title2 = view.findViewById(R.id.item_card_title2);
        category2 = view.findViewById(R.id.item_card_category2);
        value2 = view.findViewById(R.id.item_card_value2);
        date2 = view.findViewById(R.id.item_card_date2);

        title3 = view.findViewById(R.id.item_card_title3);
        category3 = view.findViewById(R.id.item_card_category3);
        value3 = view.findViewById(R.id.item_card_value3);
        date3 = view.findViewById(R.id.item_card_date3);

        materialCard4 = view.findViewById(R.id.material_card_4);
        title4 = view.findViewById(R.id.item_card_title4);
        category4 = view.findViewById(R.id.item_card_category4);
        value4 = view.findViewById(R.id.item_card_value4);
        date4 = view.findViewById(R.id.item_card_date4);
        materialCard4.setVisibility(View.GONE);

        materialCard5 = view.findViewById(R.id.material_card_5);
        title5 = view.findViewById(R.id.item_card_title5);
        category5 = view.findViewById(R.id.item_card_category5);
        value5 = view.findViewById(R.id.item_card_value5);
        date5 = view.findViewById(R.id.item_card_date5);
        materialCard5.setVisibility(View.GONE);

        materialCard6 = view.findViewById(R.id.material_card_6);
        title6 = view.findViewById(R.id.item_card_title6);
        category6 = view.findViewById(R.id.item_card_category6);
        value6 = view.findViewById(R.id.item_card_value6);
        date6 = view.findViewById(R.id.item_card_date6);
        materialCard6.setVisibility(View.GONE);

        materialCard7 = view.findViewById(R.id.material_card_7);
        title7 = view.findViewById(R.id.item_card_title7);
        category7 = view.findViewById(R.id.item_card_category7);
        value7 = view.findViewById(R.id.item_card_value7);
        date7 = view.findViewById(R.id.item_card_date7);
        materialCard7.setVisibility(View.GONE);

        seeMoreInsurance = view.findViewById(R.id.see_more_insurance);
        seeMoreInsurance.setVisibility(View.GONE);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(view1 -> findNavController(this).navigate(R.id.action_home_dest_to_camera_dest));

        setupNetwork();

        setupAppBar();

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

    // TODO: Clean up
    private void showAssetRequests(RequestResponseList requestResponseList) {
        title.setText(requestResponseList.getAssets().get(0).getTitle());
        category.setText(requestResponseList.getAssets().get(0).getCategory());
        value.setText("$" + Integer.toString(requestResponseList.getAssets().get(0).getValue()));
        date.setText(requestResponseList.getAssets().get(0).getPurchaseDate());

        title1.setText(requestResponseList.getAssets().get(1).getTitle());
        category1.setText(requestResponseList.getAssets().get(1).getCategory());
        value1.setText("$" + Integer.toString(requestResponseList.getAssets().get(1).getValue()));
        date1.setText(requestResponseList.getAssets().get(1).getPurchaseDate());

        title2.setText(requestResponseList.getAssets().get(2).getTitle());
        category2.setText(requestResponseList.getAssets().get(2).getCategory());
        value2.setText("$" + Integer.toString(requestResponseList.getAssets().get(2).getValue()));
        date2.setText(requestResponseList.getAssets().get(2).getPurchaseDate());

        title3.setText(requestResponseList.getAssets().get(3).getTitle());
        category3.setText(requestResponseList.getAssets().get(3).getCategory());
        value3.setText("$" + Integer.toString(requestResponseList.getAssets().get(3).getValue()));
        date3.setText(requestResponseList.getAssets().get(3).getPurchaseDate());
    }

    // TODO: Clean up
    private void showRecInsurance(InsuranceResponseList insuranceResponseList) {

        if (insuranceResponseList.getPolicies().size() >= 1) {
            materialCard4.setVisibility(View.VISIBLE);
            title4.setText(insuranceResponseList.getPolicies().get(0).getName());
            category4.setText(insuranceResponseList.getPolicies().get(0).getDescription());
            value4.setText("$" + Integer.toString(insuranceResponseList.getPolicies().get(0).getPremium()));

        } else if (insuranceResponseList.getPolicies().size() >= 2) {
            materialCard5.setVisibility(View.VISIBLE);
            title5.setText(insuranceResponseList.getPolicies().get(0).getName());
            category5.setText(insuranceResponseList.getPolicies().get(0).getDescription());
            value5.setText("$" + Integer.toString(insuranceResponseList.getPolicies().get(0).getPremium()));

        } else if (insuranceResponseList.getPolicies().size() >= 3) {
            materialCard6.setVisibility(View.VISIBLE);
            title6.setText(insuranceResponseList.getPolicies().get(0).getName());
            category6.setText(insuranceResponseList.getPolicies().get(0).getDescription());
            value6.setText("$" + Integer.toString(insuranceResponseList.getPolicies().get(0).getPremium()));

        } else if (insuranceResponseList.getPolicies().size() >= 4) {
            materialCard7.setVisibility(View.VISIBLE);
            title7.setText(insuranceResponseList.getPolicies().get(0).getName());
            category7.setText(insuranceResponseList.getPolicies().get(0).getDescription());
            value7.setText("$" + Integer.toString(insuranceResponseList.getPolicies().get(0).getPremium()));
            seeMoreInsurance.setVisibility(View.VISIBLE);
        }
    }

    private void setupNetwork() {
        HomeApi homeApi = new NetworkBuilder(getString(R.string.url_image_upload_endpoint)).UseGson().useRxJava2().build(HomeApi.class);
        HomeService homeService = new HomeService(homeApi);
        assetObservable = homeService.getResponses().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        recommendationsObservable = homeService.getRecommendations().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    private void decorateView() {
        assetObservable.subscribeWith(new DisposableSingleObserver<RequestResponseList>() {
            @Override
            public void onSuccess(RequestResponseList requestResponseList) {
                showAssetRequests(requestResponseList);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT);
                //Error flow
            }
        });
        recommendationsObservable.subscribeWith(new DisposableSingleObserver<InsuranceResponseList>() {
            @Override
            public void onSuccess(InsuranceResponseList insuranceResponseList) {
                showRecInsurance(insuranceResponseList);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT);
                //Error flow
            }
        });
    }
}
