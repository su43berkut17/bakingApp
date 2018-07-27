package com.su43berkut17.nanodegree.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.data.StepMenuContainer;
import com.su43berkut17.nanodegree.bakingapp.data.Steps;
import com.su43berkut17.nanodegree.bakingapp.liveData.JsonViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        noInternetError.OnRetryClickListener,
        stepList.onStepClickInterface,
        mainMenuFragment.OnMainFragmentInteractionListener,
        fragment_detail.OnFragmentInteractionListener{

    //values for the panel states
    private static final String MAIN_MENU_POS="main_menu";
    private static final String STEP_MENU_POC="step_menu";
    private static final String STEP_CONTENT="step_content";

    mainMenuFragment mainFragment;
    noInternetError errorFragment;
    stepList stepFragment;
    fragment_detail detailFragment;
    ingredientList ingredientFragment;
    JsonViewModel viewModel;

    String TAG="Main menu";

    //2 panel
    private boolean mTwoPanel;

    //variables that remember the state of the app, what is in which panel
    private String pane11State;
    private String panel2State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we check if we are in 1 panel or 2 panel
        if (findViewById(R.id.fragContent)!=null){
            mTwoPanel=true;
        }else{
            mTwoPanel=false;
        }

        //we get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //menu fragment
        mainFragment = new mainMenuFragment();
        errorFragment = new noInternetError();
        stepFragment = new stepList();
        detailFragment = new fragment_detail();
        ingredientFragment = new ingredientList();
        errorFragment.setmCallback(this);

        //we check if it is 1 or 2 panel
        if (mTwoPanel==false) {
            //it is only 1 panel
            fragmentManager.beginTransaction()
                    .add(R.id.mainActi, errorFragment)
                    .commit();
        }else{
            //it is 2 panel
            //we load the main menu on the 1st panel
            fragmentManager.beginTransaction()
                    .add(R.id.mainActi, errorFragment)
                    .commit();

            //we load the placeholder fragment on the other panel
            //fragmentManager.beginTransaction()                    .add(R.id.fragContent, loadPlaceHolder)                    .commit();
        }

        //we load the json first
        viewModel= ViewModelProviders.of(this).get(JsonViewModel.class);
        viewModel.getData().observe(this,mainMenuObserver);

    }

    final Observer<List<Recipe>> mainMenuObserver= new Observer<List<Recipe>>(){

        @Override
        public void onChanged(@Nullable List<Recipe> recipeList) {
            Log.i(TAG,"Changed: observe inside the view model");

            if (recipeList==null) {
                Log.i(TAG,"It's null we show the other layout");

                getSupportFragmentManager().beginTransaction()
                        .remove(mainFragment)
                        .replace(R.id.mainActi,errorFragment)
                        .commit();
            }else{
                Log.i(TAG,"There's content we show the rv layout");
                mainFragment.setAdapter(recipeList);

                //we update the fragment ui
                getSupportFragmentManager().beginTransaction()
                        .remove(errorFragment)
                        .replace(R.id.mainActi,mainFragment)
                        .commit();
            }
        }
    };

    @Override
    public void onClickRetry() {
        Toast.makeText(this,"We retry to load the json", Toast.LENGTH_SHORT).show();
        viewModel.getData();
    }

    //when whe click on a step
    @Override
    public void onOpenStep(StepMenuContainer steps, int currentStep, int stepSize) {
        //we set the bundle to be sent


        //depends on the type of button
        //it it is an ingredient
        if(steps.getType()==StepMenuContainer.TYPE_INGREDIENT){
            ingredientFragment = new ingredientList();

            ingredientFragment.setAdapter(steps.getIngredient());

            ingredientFragment = ingredientList.newInstance(
                    steps.getIngredient()
            );

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainActi,ingredientFragment)
                    .addToBackStack("ingredientStep")
                    .commit();
        }

        //if it is a step
        if (steps.getType()==StepMenuContainer.TYPE_STEP){
            detailFragment=new fragment_detail();

            detailFragment=fragment_detail.newInstance(steps.getId(),
                    steps.getStep().get(0).getVideoURL(),
                    steps.getStep().get(0).getThumbnailURL(),
                    steps.getStep().get(0).getDescription(),
                    currentStep,
                    stepSize);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainActi,detailFragment)
                    .addToBackStack("detailStep")
                    .commit();
        }
    }

    //when we click on a recipe
    @Override
    public void mainMenuClick(Recipe recipe) {
        //we send the steps
        //we update the fragment ui
        stepFragment.setAdapter(recipe.getSteps(),recipe.getIngredients());

        getSupportFragmentManager().beginTransaction()
                .remove(mainFragment)
                .replace(R.id.mainActi,stepFragment)
                .addToBackStack("menuStep")
                .commit();

    }

    @Override
    public void onFragmentInteraction(String typeOfButton, int currentStep, int totalStep) {
        Log.i(TAG,"We clicked the button: "+typeOfButton);
    }
}
