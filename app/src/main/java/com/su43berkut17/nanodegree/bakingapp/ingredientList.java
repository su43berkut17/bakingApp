package com.su43berkut17.nanodegree.bakingapp;


import android.os.Bundle;
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
        if (getArguments() != null ){
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

    public void setAdapter(List<Ingredients> recIngredients){
        ingredients=recIngredients;
    }

}
