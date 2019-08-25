package com.example.android.microinsurance.common;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.android.microinsurance.R;

public class ErrorComponent extends LinearLayout {

    protected Button retryButton;

    public ErrorComponent(Context context) {
        super(context);
        inflateLayout(context);
    }

    public ErrorComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context);
    }

    public ErrorComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);
    }

    public ErrorComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateLayout(context);
    }

    private void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_error, this, true);
        retryButton = this.findViewById(R.id.retry_button);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }

    public void setRetryButtonOnClickListener(OnClickListener onClickListener) {
        retryButton.setOnClickListener(onClickListener);
    }
}

