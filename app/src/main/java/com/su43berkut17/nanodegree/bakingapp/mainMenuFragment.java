package com.su43berkut17.nanodegree.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.recyclerViews.adapterMainMenu;

import java.util.List;

public class mainMenuFragment extends Fragment implements adapterMainMenu.recipeListener{
    //recycler view
    private RecyclerView rvMainMenu;
    private RecyclerView.Adapter adapter;
    private List<Recipe> recipeList;

    public mainMenuFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //we get the view
        View mainView=inflater.inflate(R.layout.fragment_main_menu,container,false);
        rvMainMenu=mainView.findViewById(R.id.rvRecipes);
        rvMainMenu.setLayoutManager(new LinearLayoutManager(getContext()));

        return mainView;
    }

    public void setAdapter(List<Recipe> recRecipe){
        Log.i("MaMenFrag","We are going to create the adapter with this many items "+recRecipe.size());
        adapter=new adapterMainMenu(recRecipe,getContext(),this);
        rvMainMenu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        //we decide what to do
    }
}
