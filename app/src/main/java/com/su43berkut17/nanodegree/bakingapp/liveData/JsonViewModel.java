package com.su43berkut17.nanodegree.bakingapp.liveData;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.v4.app.LoaderManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.utils.JsonParser;

import java.util.List;

public class JsonViewModel extends AndroidViewModel{
    private final MutableLiveData<List<Recipe>> data;
    private String TAG = "JsViMod";

    public JsonViewModel(Application application){
        super(application);
        JsonParser parser = new JsonParser(application);
        data=parser.getData();
        Log.i(TAG,"inside the view model");
        Log.i(TAG,"data = "+data.getValue());
    }

    public LiveData<List<Recipe>> getData(){

        Log.i(TAG,"we get the data inside the view model");
        return data;
    }
}
