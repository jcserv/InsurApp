package com.example.android.microinsurance.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.microinsurance.R;
import com.example.android.microinsurance.home.model.RequestResponse;

import java.util.List;

public class ResponseListAdapter extends RecyclerView.Adapter<ResponseListAdapter.ViewHolder> {

    private List<RequestResponse> requestResponseList;

    public ResponseListAdapter(List<RequestResponse> requestResponseList) {
        this.requestResponseList = requestResponseList;
    }

    @NonNull
    @Override
    public ResponseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout contentFrame = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(contentFrame);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView1.setText(requestResponseList.get(i).getId());
        viewHolder.textView2.setText(requestResponseList.get(i).getBody());
        //set some click listeners
    }

    @Override
    public int getItemCount() {
        return requestResponseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contentFrame;
        TextView textView1;
        TextView textView2;

        ViewHolder(LinearLayout contentFrame) {
            super(contentFrame);

            this.contentFrame = contentFrame;
            textView1 = contentFrame.findViewById(R.id.text1);
            textView2 = contentFrame.findViewById(R.id.text2);
        }
    }
}
