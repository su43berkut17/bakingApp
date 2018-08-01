package com.su43berkut17.nanodegree.bakingapp.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;
import com.su43berkut17.nanodegree.bakingapp.data.Recipe;
import com.su43berkut17.nanodegree.bakingapp.data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonParser extends ViewModel{
    private MutableLiveData<List<Recipe>> data;
    private List<Recipe> recipes=new ArrayList<Recipe>();
    private Context context;
    private String TAG="JsParser";

    public JsonParser(Context contextSent){
        context=contextSent;
    }

    public MutableLiveData<List<Recipe>> getData(){
        if(data==null){
            data=new MutableLiveData<List<Recipe>>();
            loadData();
            Log.i(TAG,"data is null, we load the data");
            return data;
        }else {
            Log.i(TAG,"data is not null, we return same data");
            return data;
        }
    }

    private void loadData(){
        new AsyncTask<Void,Void,List<Recipe>>(){

            @Override
            protected List<Recipe> doInBackground(Void... voids) {
                //we check if there's internet
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected){
                    //we load
                    try {
                        String recipesString = JsonUtils.loadJson();

                        return parse(recipesString);
                    }catch (IOException e){
                        e.printStackTrace();
                        return null;
                    }
                }else{
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Recipe> recipess) {
                super.onPostExecute(recipess);
                //recipes=recipess;
                data.setValue(recipess);
            }
        }.execute();
    }

    private List<Recipe> parse(String data){
        //we parse the recipes accordingly
        try {
            //create the array
            JSONArray jsonArray = new JSONArray(data);

            JSONObject jsonObject;
            recipes.clear();

            //cycle through the recipe
            for (int i=0;i<jsonArray.length();i++){
                jsonObject= jsonArray.getJSONObject(i);

                //initialize the ingredients
                List<Ingredients> ingredients=new ArrayList<Ingredients>();
                JSONArray jsonIngredients=jsonObject.getJSONArray("ingredients");

                for (int j=0;j<jsonIngredients.length();j++){
                    Ingredients ingredient=new Ingredients(
                            jsonIngredients.getJSONObject(j).getDouble("quantity"),
                            jsonIngredients.getJSONObject(j).getString("measure"),
                            jsonIngredients.getJSONObject(j).getString("ingredient")
                    );
                    ingredients.add(ingredient);
                    Log.i(TAG,"the ingredient is "+ingredient.getIngredient()+"-"+ingredient.getMeasure()+" "+ingredient.getQuantity());
                }

                //initialize the steps
                List<Steps> steps=new ArrayList<Steps>();
                JSONArray jsonSteps=jsonObject.getJSONArray("steps");

                for (int j=0;j<jsonSteps.length();j++){
                    Steps step=new Steps(
                            jsonSteps.getJSONObject(j).getInt("id"),
                            jsonSteps.getJSONObject(j).getString("shortDescription"),
                            jsonSteps.getJSONObject(j).getString("description"),
                            jsonSteps.getJSONObject(j).getString("videoURL"),
                            jsonSteps.getJSONObject(j).getString("thumbnailURL")
                    );

                    steps.add(step);
                }

                //we get the main recipe values
                //initialize an whole recipe object
                Recipe recipe=new Recipe(jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        ingredients,
                        steps,
                        jsonObject.getInt("servings"),
                        jsonObject.getString("image")
                );

                //add it to the final list
                recipes.add(recipe);
            }

            return recipes;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
