package com.su43berkut17.nanodegree.bakingapp;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class mainMenuFragment extends Fragment implements adapterMainMenu.recipeListener {
    private String TAG = "MainFrag";
    private static final String SAVED_RECIPE = "saved_recipes";

    //recycler view
    private RecyclerView rvMainMenu;
    private RecyclerView.Adapter adapter;
    private static List<Recipe> recipe;

    //text
    private Boolean isConnected = false;

    //listener for click
    private OnMainFragmentInteractionListener mMainListener;
    private ChangeActionBarNameListener mActionListener;
    private SendRecipeListener mRecipeListener;

    public mainMenuFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate, we will check if the saved instance state");
        if (savedInstanceState != null) {
            Log.i(TAG, "Fragment state exists so we load the content we saved");
            //we saved some content
            ArrayList<Recipe> receiveList = new ArrayList<Recipe>();
            receiveList = savedInstanceState.getParcelableArrayList(SAVED_RECIPE);
            recipe = new ArrayList<>();

            for (int i = 0; i < receiveList.size(); i++) {
                recipe.add(receiveList.get(i));
            }
        } else {
            //it is null now we need to load the recipe directly from the main activity variable
            mRecipeListener.sendRecipeListenerToFragment();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainListener = (OnMainFragmentInteractionListener) context;
        mActionListener = (ChangeActionBarNameListener) context;
        mRecipeListener = (SendRecipeListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "We are going to inflate the xml");
        //we change the action bar to baking app
        mActionListener.changeActionBarName("BakingApp");

        //we check which view to inflate
        View mainView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        rvMainMenu = mainView.findViewById(R.id.rvRecipes);
        rvMainMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        if (recipe != null) {
            adapter = new adapterMainMenu(recipe, getContext(), this);

            //we set the adapter
            rvMainMenu.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "We are saving the instance state");
        //we save the stuff we need
        ArrayList<Recipe> sendList = new ArrayList<Recipe>();

        for (int i = 0; i < recipe.size(); i++) {
            sendList.add(recipe.get(i));
        }

        outState.putParcelableArrayList(SAVED_RECIPE, sendList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void setAdapter(List<Recipe> recRecipe) {
        recipe = recRecipe;
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        //we decide what to do
        mMainListener.mainMenuClick(recipe);
    }

    //interface to main activity
    public interface OnMainFragmentInteractionListener {
        void mainMenuClick(Recipe recipe);
    }

    public interface ChangeActionBarNameListener {
        void changeActionBarName(String newTitle);
    }

    public interface SendRecipeListener {
        void sendRecipeListenerToFragment();
    }
}