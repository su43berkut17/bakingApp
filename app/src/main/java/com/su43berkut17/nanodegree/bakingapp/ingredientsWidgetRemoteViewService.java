package com.su43berkut17.nanodegree.bakingapp;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.su43berkut17.nanodegree.bakingapp.recyclerViews.adapterWidgetRemoteView;

public class ingredientsWidgetRemoteViewService extends RemoteViewsService{

    private static final String TAG="WidgetRemViewService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent){
        Log.i(TAG,"we get the intent to start the adapter");
        return new adapterWidgetRemoteView(this.getApplicationContext(),intent);
    }
}
