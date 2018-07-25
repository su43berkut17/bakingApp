package com.su43berkut17.nanodegree.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;
import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.data.StepMenuContainer;
import com.su43berkut17.nanodegree.bakingapp.data.Steps;
import com.su43berkut17.nanodegree.bakingapp.recyclerViews.adapterRecipeSteps;

import java.util.ArrayList;
import java.util.List;


public class stepList extends Fragment implements adapterRecipeSteps.stepListener{
    private static final String TAG="stepList";

    //tags for the type of adapter
    private static final String TYPE_INGREDIENT="ingredient";
    private static final String TYPE_STEP="step";

    //recycler view
    private RecyclerView rvStepMenu;
    private RecyclerView.Adapter adapter;
    private List<StepMenuContainer> finalAdapter;

    private onStepClickInterface mListener;

    public stepList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            Recipe recipe=savedInstanceState.getParcelable("fullRecipe");
            steps=recipe.getSteps();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View stepView = inflater.inflate(R.layout.fragment_step_list, container, false);

        //we get the recyclerview
        rvStepMenu=stepView.findViewById(R.id.rvRecipeSteps);
        rvStepMenu.setLayoutManager(new LinearLayoutManager(getContext()));

        if (steps!=null) {
            adapter=new adapterRecipeSteps(steps,getContext(),this);
            rvStepMenu.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        return stepView;
    }

    public void setAdapter(List<Steps> recStep, List<Ingredients> recIngredients){
        //we need to figure out a way to add 2 types of data to the adapter
        List<Steps> steps = recStep;



        //we join the values in a parcelable
        finalAdapter=new ArrayList<>();

        //we cycle through the contents of the steps
        finalAdapter.add(new StepMenuContainer(0,TYPE_INGREDIENT, recIngredients,null));




        Log.i(TAG,"the step list is "+steps.toString());
        for (int i=0;i<steps.size();i++){
            Log.i(TAG,"step "+i+"--"+steps.get(i).getDescription());
        }
    }

    //listeners buttons
    public void onButtonPressed(Steps steps, int currentStep, int totalSteps) {
        if (mListener != null) {
            mListener.onOpenStep(steps,currentStep,totalSteps);
        }
    }

    //interface for interaction with activity
    @Override
    public void onStepClick(Steps steps, int currentStep, int stepSize) {
        mListener.onOpenStep(steps,currentStep,stepSize);
    }

    public interface onStepClickInterface {
        void onOpenStep(Steps steps, int currentStep, int stepSize);
    }
}
