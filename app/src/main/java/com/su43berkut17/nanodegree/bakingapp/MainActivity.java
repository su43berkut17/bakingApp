package com.su43berkut17.nanodegree.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.data.StepMenuContainer;
import com.su43berkut17.nanodegree.bakingapp.liveData.JsonViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        noInternetError.OnRetryClickListener,
        stepList.onStepClickInterface,
        mainMenuFragment.OnMainFragmentInteractionListener,
        fragment_detail.OnStepDetailClick {

    //values for the panel states
    private static final String POS_MAIN_MENU="main_menu";
    private static final String POS_STEP_MENU="step_menu";
    private static final String POS_STEP_CONTENT="step_content";
    private static final String POS_INGREDIENT_CONTENT="ingredient_content";
    private static final String POS_PLACEHOLDER="placeholder";

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
    public String panelState1;
    private String panelState2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null) {
            panelState1 = POS_MAIN_MENU;
        }

            //we check if we are in 1 panel or 2 panel
            if (findViewById(R.id.fragContent) != null) {
                mTwoPanel = true;
            } else {
                mTwoPanel = false;
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
            if (mTwoPanel == false) {
                //it is only 1 panel
                fragmentManager.beginTransaction()
                        .add(R.id.mainActi, errorFragment)
                        .commit();
            } else {
                //it is 2 panel
                //we load the main menu on the 1st panel
                fragmentManager.beginTransaction()
                        .add(R.id.mainActi, errorFragment)
                        .commit();

                //we load the placeholder fragment on the other panel
                //fragmentManager.beginTransaction()                    .add(R.id.fragContent, loadPlaceHolder)                    .commit();
            }

            //we load the json first
            viewModel = ViewModelProviders.of(this).get(JsonViewModel.class);
            viewModel.getData().observe(this, mainMenuObserver);
    }

    final Observer<List<Recipe>> mainMenuObserver= new Observer<List<Recipe>>(){

        @Override
        public void onChanged(@Nullable List<Recipe> recipeList) {
            //we check if we are in the main menu

            //if (panelState1==POS_MAIN_MENU) {
                Log.i(TAG, "Changed: observe inside the view model");

                if (recipeList == null) {
                    Log.i(TAG, "It's null we show the other layout");

                    getSupportFragmentManager().beginTransaction()
                            .remove(mainFragment)
                            .replace(R.id.mainActi, errorFragment)
                            .commit();
                } else {
                    Log.i(TAG, "There's content we show the rv layout");
                    mainFragment.setAdapter(recipeList);

                    //we update the fragment ui
                    getSupportFragmentManager().beginTransaction()
                            .remove(errorFragment)
                            .replace(R.id.mainActi, mainFragment)
                            .commit();
                }
            //}
        }
    };

    //listener of error fragment button
    @Override
    public void onClickRetry() {
        Toast.makeText(this,"We retry to load the json", Toast.LENGTH_SHORT).show();
        viewModel.getData();
    }

    //when we click on a recipe
    @Override
    public void mainMenuClick(Recipe recipe) {
        //we send the steps
        //we change the position
        if (mTwoPanel==true){
            panelState1=POS_STEP_MENU;
            panelState2=POS_PLACEHOLDER;
        }else{
            panelState1=POS_STEP_MENU;
        }

        //we update the fragment ui
        stepFragment.setAdapter(recipe.getSteps(),recipe.getIngredients());

        getSupportFragmentManager().beginTransaction()
                .remove(mainFragment)
                .replace(R.id.mainActi,stepFragment)
                .addToBackStack("menuStep")
                .commit();
    }

    //when whe click on a step
    @Override
    public void onOpenStep(StepMenuContainer steps, int currentStep, int stepSize) {
        //we check if it is 1 or 2 panels
        if (mTwoPanel==false) {
            //1 panel
            //depends on the type of button
            //it it is an ingredient
            if (steps.getType() == StepMenuContainer.TYPE_INGREDIENT) {
                //we change the current panels
                panelState1=POS_INGREDIENT_CONTENT;

                ingredientFragment = new ingredientList();

                ingredientFragment.setAdapter(steps.getIngredient());

                ingredientFragment = ingredientList.newInstance(
                        steps.getIngredient()
                );

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainActi, ingredientFragment)
                        .addToBackStack("ingredientStep")
                        .commit();
            }

            //if it is a step
            if (steps.getType() == StepMenuContainer.TYPE_STEP) {
                //we change the current panels
                panelState1=POS_STEP_CONTENT;

                detailFragment = new fragment_detail();

                detailFragment = fragment_detail.newInstance(steps.getId(),
                        steps.getStep().get(0).getVideoURL(),
                        steps.getStep().get(0).getThumbnailURL(),
                        steps.getStep().get(0).getDescription(),
                        currentStep,
                        stepSize);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainActi, detailFragment)
                        .addToBackStack("detailStep")
                        .commit();
            }
        }else{
            //2 panels
            //it it is an ingredient
            if (steps.getType() == StepMenuContainer.TYPE_INGREDIENT) {
                //we change the current panels
                panelState1=POS_STEP_MENU;
                panelState2=POS_INGREDIENT_CONTENT;

                ingredientFragment = new ingredientList();
                ingredientFragment.setAdapter(steps.getIngredient());

                ingredientFragment = ingredientList.newInstance(
                        steps.getIngredient()
                );

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragContent, ingredientFragment)
                        .addToBackStack("ingredientStep")
                        .commit();
            }

            //if it is a step
            if (steps.getType() == StepMenuContainer.TYPE_STEP) {
                //we change the current panels
                panelState1=POS_STEP_MENU;
                panelState2=POS_STEP_CONTENT;

                detailFragment = new fragment_detail();

                detailFragment = fragment_detail.newInstance(steps.getId(),
                        steps.getStep().get(0).getVideoURL(),
                        steps.getStep().get(0).getThumbnailURL(),
                        steps.getStep().get(0).getDescription(),
                        currentStep,
                        stepSize);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragContent, detailFragment)
                        .addToBackStack("detailStep")
                        .commit();
            }
        }
    }

    //when we click the next or previous buttons inside the step
    @Override
    public void OnStepDetail(String typeOfButton, int currentStep, int totalStep) {
        Log.i(TAG,"We clicked the button: "+typeOfButton+" the current step is: "+currentStep+" the total steps is: "+totalStep);

        //we get the step list
        List<StepMenuContainer> stepList;
        stepList=stepFragment.getStepList();

        StepMenuContainer steps;

        if (typeOfButton==fragment_detail.BTN_NEXT){
            //we go to the next fragment
            currentStep++;
        }else{
            //we go to the previous step
            currentStep--;

        }
        steps=stepList.get(currentStep);

        if (steps.getType() == StepMenuContainer.TYPE_INGREDIENT) {
            //we change the current panels
            panelState1=POS_INGREDIENT_CONTENT;

            ingredientFragment = new ingredientList();

            ingredientFragment.setAdapter(steps.getIngredient());

            ingredientFragment = ingredientList.newInstance(
                    steps.getIngredient()
            );

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainActi, ingredientFragment)
                    .addToBackStack("ingredientStep"+String.valueOf(currentStep))
                    .commit();
        }

        //if it is a step
        if (steps.getType() == StepMenuContainer.TYPE_STEP) {
            //we change the current panels
            panelState1=POS_STEP_CONTENT;

            detailFragment = new fragment_detail();

            detailFragment = fragment_detail.newInstance(steps.getId(),
                    steps.getStep().get(0).getVideoURL(),
                    steps.getStep().get(0).getThumbnailURL(),
                    steps.getStep().get(0).getDescription(),
                    currentStep,
                    totalStep);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainActi, detailFragment)
                    .addToBackStack("detailStep"+String.valueOf(currentStep))
                    .commit();
        }
    }
}
