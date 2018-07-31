package com.su43berkut17.nanodegree.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    private static final String TAG="IngredientsWidget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String nameOfRecipe, List<Ingredients> ingredientsList) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        //initial state
        if (nameOfRecipe==null) {
            CharSequence widgetText = context.getString(R.string.widget_initial_instruction);

            //we hide the other stuff
            views.setViewVisibility(R.id.frame_layout_widget_recipe_title, View.GONE);
            views.setViewVisibility(R.id.list_view_widget_linear_layout,View.GONE);

            views.setTextViewText(R.id.appwidget_text, widgetText);
            views.setImageViewResource(R.id.widget_alert_icon, android.R.drawable.ic_dialog_alert);

            //we set the pending intent to launch the main app when the widget is in its default mode
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            //we set the intent in both the icon and the text
            views.setOnClickPendingIntent(R.id.widget_alert_icon, pendingIntent);
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        }else{
            //there is content we create the proper view
            //we hide the initial state
            views.setViewVisibility(R.id.empty_widget,View.GONE);

            //we set the recipe title
            views.setTextViewText(R.id.tv_wid_recipe,nameOfRecipe);

            //we set the content of the ingredients on the list
            Intent intent = new Intent(context,ingredientsWidgetRemoteViewService.class);

            ArrayList<Ingredients> sendIngredients;
            sendIngredients=new ArrayList<>(ingredientsList);

            Log.i(TAG,"The number of items in the ingredients received is  "+ingredientsList.size());
            Log.i(TAG,"The number of items in the ingredients converted to array is "+sendIngredients.size());

            //intent.putParcelableArrayListExtra("INGREDIENT_LIST", sendIngredients);
            //intent.putExtra("test","this is a test");
            views.setRemoteAdapter(R.id.list_view_widget,intent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    //we set the public void that updates all the widgets
    public static void updateAllIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                                  int[] appWidgetIds, String nameOfRecipe, List<Ingredients> ingredientsList){
        for (int appWidgetId: appWidgetIds){
            updateAppWidget(context,appWidgetManager,appWidgetId,nameOfRecipe,ingredientsList);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        /*for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
        updateIngredientsWidgetService.startActionUpdateIngredients(context,null,null);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        updateIngredientsWidgetService.startActionUpdateIngredients(context,null,null);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

