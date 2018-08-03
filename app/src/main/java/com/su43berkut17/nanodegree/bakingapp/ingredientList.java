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

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;
import com.su43berkut17.nanodegree.bakingapp.recyclerViews.adapterIngredients;

import java.util.ArrayList;
import java.util.List;

public class ingredientList extends Fragment {
     private static final String TAG="IngredientList";

     private static final String INGREDIENTS_LIST="ingredientsList";
     private static final String SAVED_INGREDIENTS="saved_ingredient_list";

    //recycler view
    private RecyclerView rvIngredients;
    private RecyclerView.Adapter ingredientAdapter;
    private List<Ingredients> ingredients;


    public ingredientList() {
        // Required empty public constructor
    }

    public static ingredientList newInstance(List<Ingredients> recIngredients) {
        ingredientList fragment = new ingredientList();

        fragment.setAdapter(recIngredients);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            Log.i(TAG,"Fragment state exists so we load the content we saved in the ingredients");
            //we saved some content
            ArrayList<Ingredients> receiveList=new ArrayList<Ingredients>();
            receiveList=savedInstanceState.getParcelableArrayList(SAVED_INGREDIENTS);
            ingredients=new ArrayList<>();

            for (int i=0;i<receiveList.size();i++){
                ingredients.add(receiveList.get(i));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get the view
        View ingredientsView=inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        //get the rv
        rvIngredients=ingredientsView.findViewById(R.id.rvIngredientList);
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));

        //we set the adapter
        if (ingredients!=null) {
            ingredientAdapter=new adapterIngredients(ingredients,getContext());
            rvIngredients.setAdapter(ingredientAdapter);
            ingredientAdapter.notifyDataSetChanged();
        }

        // Inflate the layout for this fragment
        return ingredientsView;
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

        //we save the stuff we need
        ArrayList<Ingredients> sendList=new ArrayList<Ingredients>();
        //sendList=finalAdapter.toArray();
        for (int i=0;i<ingredients.size();i++){
            sendList.add(ingredients.get(i));
        }

        outState.putParcelableArrayList(SAVED_INGREDIENTS,sendList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void setAdapter(List<Ingredients> recIngredients){
        ingredients=recIngredients;
    }

}
