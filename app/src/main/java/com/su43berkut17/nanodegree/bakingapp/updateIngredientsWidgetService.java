package com.su43berkut17.nanodegree.bakingapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;

import java.util.List;

public class updateIngredientsWidgetService extends IntentService{
    public static final String ACTION_UPDATE_WIDGET="com.su43berkut17.nanodegree.bakingapp.action.update_widget";
    private static String nameOfRecipe;
    private static List<Ingredients> ingredientsList;

    public updateIngredientsWidgetService(){
        super("updateIngredientsWidgetService");
    }

    public static void startActionUpdateIngredients(Context context, String recNameOfRecipe, List<Ingredients> recIngredientsList){
        //we store the data if it is sent, if it is not the widget doesnt need to update
        if (recNameOfRecipe!=null) {
            nameOfRecipe = recNameOfRecipe;
            ingredientsList = recIngredientsList;
        }

        //we start the intent
        Intent intent=new Intent(context,updateIngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            final String action=intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)){
                updateIngredientsWidget();
            }
        }
    }

    //method that updates the ingredients
    private void updateIngredientsWidget(){
        //we check if the data is not null
        if (ingredientsList!=null){
            //data has been sent, we update the widget!

        }
    }
}
