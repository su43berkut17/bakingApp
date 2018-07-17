package com.su43berkut17.nanodegree.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class noInternetError extends Fragment {
    private String TAG="ErrorFrag";

    OnRetryClickListener mCallback;

    public interface OnRetryClickListener{
        void onClickRetry();
    }

    public noInternetError() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.fragment_no_internet_error, container, false);

        //we get the button
        Button bRetry=(Button) mainView.findViewById(R.id.bt_retry);
        bRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClickRetry();
            }
        });

        return mainView;
        /*Log.i(TAG,"before inflating");
        return inflater.inflate(R.layout.fragment_no_internet_error, container, false);*/
    }

    public void setmCallback(OnRetryClickListener recLlistener){
        mCallback=recLlistener;
    }
}
