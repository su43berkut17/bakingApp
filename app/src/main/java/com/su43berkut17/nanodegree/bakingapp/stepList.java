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
            //Recipe recipe=savedInstanceState.getParcelable("fullRecipe");
            //List<Steps> steps=recipe.getSteps();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener=(onStepClickInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View stepView = inflater.inflate(R.layout.fragment_step_list, container, false);

        //we get the recyclerview
        rvStepMenu=stepView.findViewById(R.id.rvRecipeSteps);
        rvStepMenu.setLayoutManager(new LinearLayoutManager(getContext()));

        if (finalAdapter!=null) {
            adapter=new adapterRecipeSteps(finalAdapter,getContext(),this);
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

        //we add the ingredient one
        finalAdapter.add(new StepMenuContainer(0,StepMenuContainer.TYPE_INGREDIENT, recIngredients,null));

        //we cycle through the steps to create the buttons
        for (int j=0;j<steps.size();j++){
            List<Steps> stepSend=new ArrayList<>();
            stepSend.add(steps.get(j));
            Log.i(TAG,"value of step "+stepSend.size()+" - "+stepSend.get(0).getId());
            finalAdapter.add(new StepMenuContainer(j+1,StepMenuContainer.TYPE_STEP,null, stepSend));
        }

        Log.i(TAG,"the full list length is "+finalAdapter.size());
    }

    public List<StepMenuContainer> getStepList(){
        return finalAdapter;
    }

    //listener buttons
    public void onButtonPressed(StepMenuContainer steps, int currentStep, int totalSteps) {
        if (mListener != null) {
            mListener.onOpenStep(steps,currentStep,totalSteps);
        }
    }

    //interface for interaction with activity
    @Override
    public void onStepClick(StepMenuContainer steps, int currentStep, int stepSize) {
        mListener.onOpenStep(steps,currentStep,stepSize);
    }

    public interface onStepClickInterface {
        void onOpenStep(StepMenuContainer steps, int currentStep, int stepSize);
    }
}
