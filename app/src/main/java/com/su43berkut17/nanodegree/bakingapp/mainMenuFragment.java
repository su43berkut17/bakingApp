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
import android.widget.TextView;
import android.widget.Toast;

import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.recyclerViews.adapterMainMenu;

import java.util.List;

public class mainMenuFragment extends Fragment implements adapterMainMenu.recipeListener{
    private String TAG="MainFrag";

    //recycler view
    private RecyclerView rvMainMenu;
    private RecyclerView.Adapter adapter;
    private List<Recipe> recipe;

    //text
    private Boolean isConnected=false;

    public mainMenuFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"We are going to inflate the xml");
        //we check which view to inflate
        View mainView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        rvMainMenu = mainView.findViewById(R.id.rvRecipes);
        rvMainMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        if (recipe!=null) {
            adapter = new adapterMainMenu(recipe, getContext(), this);

            //we set the adapter
            rvMainMenu.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


        return mainView;
    }

    public void setAdapter(List<Recipe> recRecipe){
        recipe=recRecipe;
        //Log.i(TAG,"We are going to update the adapter with this many items "+recipe.size());

        //adapter=new adapterMainMenu(recRecipe,getContext(),this);

        //we set the adapter
        //rvMainMenu.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    public void changeConnection(Boolean show){
        isConnected=show;
        Log.i(TAG,"connection status "+show.toString());
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        //we decide what to do
        Toast.makeText(getContext(),"Item selected"+recipe.getName(), Toast.LENGTH_SHORT).show();
    }
}
