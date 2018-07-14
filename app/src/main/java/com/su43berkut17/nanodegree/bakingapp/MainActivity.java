package com.su43berkut17.nanodegree.bakingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.liveData.JsonViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG="MainAct";
    mainMenuFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we get the fragment manager
        FragmentManager fragmentManager=getSupportFragmentManager();

        //menu fragment
        mainFragment  = new mainMenuFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fl_main_menu,mainFragment)
        .commit();

        //we load the json first
        JsonViewModel viewModel= ViewModelProviders.of(this).get(JsonViewModel.class);
        viewModel.getData().observe(this,mainMenuObserver);
    }

    final Observer<List<Recipe>> mainMenuObserver= new Observer<List<Recipe>>(){

        @Override
        public void onChanged(@Nullable List<Recipe> recipeList) {
            Log.i(TAG,"observe inside the view model");
            if (recipeList==null){

            }else{
                mainFragment.setAdapter(recipeList);
            }
        }
    };
}
