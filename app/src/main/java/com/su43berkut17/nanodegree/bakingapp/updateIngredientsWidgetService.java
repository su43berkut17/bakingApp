package com.su43berkut17.nanodegree.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;

import java.util.List;

public class updateIngredientsWidgetService extends IntentService{
    public static final String TAG="WidgetUpdateService";
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
            Log.i(TAG,"ingredients received on the service "+ingredientsList.size());
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
        //data has been sent, we update the widget!
        //we get the widget manager
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,IngredientsWidget.class));

        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetsIds,R.id.list_view_widget);
        IngredientsWidget.updateAllIngredientWidgets(this,appWidgetManager,appWidgetsIds, nameOfRecipe, ingredientsList);
    }
}
