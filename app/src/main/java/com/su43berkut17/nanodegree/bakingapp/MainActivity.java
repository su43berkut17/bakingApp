package com.su43berkut17.nanodegree.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.data.StepMenuContainer;
import com.su43berkut17.nanodegree.bakingapp.liveData.JsonViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        noInternetError.OnRetryClickListener,
        mainMenuFragment.OnMainFragmentInteractionListener,
        mainMenuFragment.ChangeActionBarNameListener,
        mainMenuFragment.SendRecipeListener,
        mainMenuFragment.HideTopNavigation,
        stepList.onStepClickInterface,
        stepList.onShowSteps,
        fragment_detail.OnStepDetailClick,
        fragment_detail.OnSavePlayerTime{

    //values for the panel states
    private static final String POS_MAIN_MENU="main_menu";
    private static final String POS_STEP_MENU="step_menu";
    private static final String POS_STEP_CONTENT="step_content";
    private static final String POS_INGREDIENT_CONTENT="ingredient_content";
    private static final String POS_PLACEHOLDER="placeholder";

    private static final String STEP_LIST_SAVED="step_list_saved";
    private static final String RECIPE_SAVED="recipe_saved";
    private static final String POSITION_PLAYER_SAVED="player_saved";

    mainMenuFragment mainFragment;
    noInternetError errorFragment;
    stepList stepFragment;
    fragment_detail detailFragment;
    ingredientList ingredientFragment;
    JsonViewModel viewModel;

    String TAG="Main menu";

    //2 panel
    private boolean mTwoPanel;

    //variable to store the step list for the previous and next buttons
    private static List<StepMenuContainer> mStepList;
    private static List<Recipe> mRecipe;

    //variable to store the video time
    private static long mPlayerTime;

    //variables that remember the state of the app, what is in which panel
    public String panelState1;
    private String panelState2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //we check what instance is loaded
        if (savedInstanceState==null) {
            //it the 1st load of the app
            panelState1 = POS_MAIN_MENU;
            panelState2 = POS_PLACEHOLDER;

            loadMainMenu(fragmentManager);
        }else{
            //it was created, we check the options
            //if it is just the main menu
            if (savedInstanceState.getString("PANEL_1")==POS_MAIN_MENU){
                loadMainMenu(fragmentManager);
            }

            Log.i(TAG,"there is already a saved instance state");

            //we check if the detail view
            detailFragment.setVideoPosition(mPlayerTime);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //we save the items needed
        outState.putString("PANEL_1",panelState1);
        outState.putString("PANEL_2",panelState2);
        Log.i(TAG,"on save instance state");

        //save the step list if it is not null
        if (mStepList!=null){
            Log.i(TAG,"we are SAVING the step list");
            ArrayList<StepMenuContainer> stepListSend= new ArrayList<StepMenuContainer>();
            for (int i=0;i<mStepList.size();i++){
                stepListSend.add(mStepList.get(i));
            }

            outState.putParcelableArrayList(STEP_LIST_SAVED,stepListSend);
        }

        //save the recipe list if it is not null
        if (mRecipe!=null){
            Log.i(TAG,"we are SAVING the recipe list");
            ArrayList<Recipe> recipeListSend = new ArrayList<>();
            for (int i=0;i<mRecipe.size();i++){
                recipeListSend.add(mRecipe.get(i));
            }

            outState.putParcelableArrayList(RECIPE_SAVED,recipeListSend);
        }

        //save the video time
        Log.i(TAG,"We are saving the player time "+mPlayerTime);
        outState.putLong(POSITION_PLAYER_SAVED,mPlayerTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //we assign the items
        panelState1=savedInstanceState.getString("PANEL_1");
        panelState2=savedInstanceState.getString("PANEL_2");

        Log.i(TAG,"on Restore instance state");
        //load the step list if it is not null
        if (savedInstanceState.getParcelableArrayList(STEP_LIST_SAVED)!=null){
            Log.i(TAG,"we are LOADING the step list");
            ArrayList<StepMenuContainer> stepListReceive=new ArrayList<>();
            stepListReceive=savedInstanceState.getParcelableArrayList(STEP_LIST_SAVED);
            mStepList=new ArrayList<>();
            for (int i=0;i<stepListReceive.size();i++){
                mStepList.add(stepListReceive.get(i));
            }
        }
        //load the recipe if it is not null
        if (savedInstanceState.getParcelableArrayList(RECIPE_SAVED)!=null){
            Log.i(TAG,"we are LOADING the recipe");
            ArrayList<Recipe> recipeListReceive=new ArrayList<>();
            recipeListReceive=savedInstanceState.getParcelableArrayList(RECIPE_SAVED);
            mRecipe=new ArrayList<>();
            for (int i=0;i<recipeListReceive.size();i++){
                mRecipe.add(recipeListReceive.get(i));
            }
        }

        //load the time
        mPlayerTime=savedInstanceState.getLong(POSITION_PLAYER_SAVED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableTopBackButton(true);
    }

    //lifecycle loaders
    private void loadMainMenu(FragmentManager fragmentManager){
        Log.i(TAG,"loading main menu");

        //we load the main menu in either instance
        fragmentManager.beginTransaction()
                .add(R.id.mainActi, errorFragment)
                .commit();

        //we load the json first
        viewModel = ViewModelProviders.of(this).get(JsonViewModel.class);
        viewModel.getData().observe(this, mainMenuObserver);

        //we change the action bar title
        changeActionBarName("BakingApp");

        //we hide the top button
        enableTopBackButton(true);
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
                    mRecipe=recipeList;

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
        //Toast.makeText(this,"We retry to load the json", Toast.LENGTH_SHORT).show();
        viewModel.getData();
    }

    //we change the actionBar
    @Override
    public void changeActionBarName(String newTitle) {
        ActionBar titleUp=getSupportActionBar();
        titleUp.setTitle(newTitle);
    }

    @Override
    public void sendRecipeListenerToFragment() {
        mainFragment.setAdapter(mRecipe);
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

        //we save the steps
        mStepList=stepFragment.getStepList();

        //we update in the widget
        updateIngredientsWidgetService.startActionUpdateIngredients(this,recipe.getName(),recipe.getIngredients());

        //we change the action bar title
        changeActionBarName(recipe.getName());
    }

    private void enableTopBackButton(Boolean activationStatus){
        ActionBar actionBar=getSupportActionBar();

        //we activate the button
        actionBar.setDisplayHomeAsUpEnabled(activationStatus);
    }

    //when whe click on a step
    @Override
    public void onOpenStep(StepMenuContainer steps, int currentStep, int stepSize) {
        //we reset the player time
        mPlayerTime=0;

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

                //we reset it
                detailFragment.setVideoPosition(0);

                detailFragment = fragment_detail.newInstance(steps.getId(),
                        steps.getStep().get(0).getVideoURL(),
                        steps.getStep().get(0).getThumbnailURL(),
                        steps.getStep().get(0).getDescription(),
                        currentStep,
                        stepSize,
                        mPlayerTime);

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

                detailFragment.setVideoPosition(0);

                detailFragment = fragment_detail.newInstance(steps.getId(),
                        steps.getStep().get(0).getVideoURL(),
                        steps.getStep().get(0).getThumbnailURL(),
                        steps.getStep().get(0).getDescription(),
                        currentStep,
                        stepSize,
                        mPlayerTime);

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
        Log.i(TAG, "We clicked the button: " + typeOfButton + " the current step is: " + currentStep + " the total steps is: " + totalStep);
        Log.i(TAG, "the step list saved is " + mStepList);
        Log.i(TAG, "the step list length is " + mStepList.size());
        //we get the step list
        //mStepList=stepFragment.getStepList();

        StepMenuContainer steps;

        if (typeOfButton == fragment_detail.BTN_NEXT) {
            //we go to the next fragment
            currentStep++;
        } else {
            //we go to the previous step
            currentStep--;
        }

        steps = mStepList.get(currentStep);

        if (steps.getType() == StepMenuContainer.TYPE_INGREDIENT) {
            //we change the current panels
            panelState1 = POS_INGREDIENT_CONTENT;

            ingredientFragment = new ingredientList();

            ingredientFragment.setAdapter(steps.getIngredient());

            ingredientFragment = ingredientList.newInstance(
                    steps.getIngredient()
            );

            if (mTwoPanel==true) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragContent, ingredientFragment)
                        .addToBackStack("ingredientStep" + String.valueOf(currentStep))
                        .commit();
            }else{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainActi, ingredientFragment)
                        .addToBackStack("ingredientStep" + String.valueOf(currentStep))
                        .commit();
            }
        }

        //if it is a step
        if (steps.getType() == StepMenuContainer.TYPE_STEP) {
            //we change the current panels
            panelState1 = POS_STEP_CONTENT;

            detailFragment = new fragment_detail();

            detailFragment = fragment_detail.newInstance(steps.getId(),
                    steps.getStep().get(0).getVideoURL(),
                    steps.getStep().get(0).getThumbnailURL(),
                    steps.getStep().get(0).getDescription(),
                    currentStep,
                    totalStep,
                    0);

            detailFragment.setVideoPosition(0);

            //we check if it is 1 or 2 panels
            if (mTwoPanel==true) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragContent, detailFragment)
                        .addToBackStack("detailStep" + String.valueOf(currentStep))
                        .commit();
            }else{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainActi, detailFragment)
                        .addToBackStack("detailStep" + String.valueOf(currentStep))
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(TAG,"BACK BUTTON on optionsItemSelected");
                //we load the main menu
                mainFragment.setAdapter(mRecipe);

                FragmentManager fm = getSupportFragmentManager();
                //we clear the backstack
                int count = fm.getBackStackEntryCount();
                for(int i = 0; i < count; ++i) {
                    fm.popBackStack();
                }

                //we update the fragment of the main menu
                fm.beginTransaction()
                        .replace(R.id.mainActi, mainFragment)
                        .commit();

                //we check if it is 2 panels to make sure we go back
                if (mTwoPanel){
                    //cycle through all the sub fragments
                    for (Fragment fragment:fm.getFragments()){
                        fm.beginTransaction().
                                remove(fragment).commit();
                    }
                }

                //we hide the back button on top
                enableTopBackButton(false);

                //we reset the video value
                mPlayerTime=0;

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnSavePlayerTimeActivity(long timeSaved) {
        //we save the time of the player in the main activity
        mPlayerTime=timeSaved;
    }

    @Override
    public void hideTopNavigationFromFragment() {
        enableTopBackButton(false);
    }

    @Override
    public void onStepResume() {
        enableTopBackButton(true);
        mPlayerTime=0;
    }
}