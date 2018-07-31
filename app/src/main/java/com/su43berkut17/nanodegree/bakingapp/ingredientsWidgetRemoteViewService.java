package com.su43berkut17.nanodegree.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;

import java.util.ArrayList;
import java.util.List;

public class ingredientsWidgetRemoteViewService extends RemoteViewsService{

    public static final String TAG="WidgetRemoteViewService";

    //we set the other stuff
    public static List<Ingredients> ingredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent){
        Log.i(TAG,"we get the intent to start the adapter");
        ingredientsList=intent.getParcelableArrayListExtra("INGREDIENT_LIST");

        return new adapterWidgetRemoteView(this.getApplicationContext(),intent,"hello");
    }

    class adapterWidgetRemoteView implements
            RemoteViewsService.RemoteViewsFactory{

        private static final String TAG="adapWidRemVi";
        private Context mContext;
        private String textTest;
        //private List<Ingredients> ingredientsList=new ArrayList<>();
        //we might have to use a cursor


        public adapterWidgetRemoteView(Context context,Intent intent,String test){
            mContext=context;
            ingredientsList=intent.getParcelableArrayListExtra("INGREDIENT_LIST");
            textTest=test;
            Log.i(TAG,"the test received in a string is "+textTest);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            //we will load the ingredients in the content provider
            Log.i(TAG,"On Data set changed but we already set the ingredients list as static");
            Log.i(TAG,"we print the test data we received as a parameter "+textTest);
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if (ingredientsList==null){
                return 0;
            }else{
                return  ingredientsList.size();
            }
        }

        @Override
        public RemoteViews getViewAt(int position){
            if (ingredientsList==null){
                return null;
            }else {
                RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.rv_ingredients_widget_list);
                Ingredients ingredient = ingredientsList.get(position);

                rv.setTextViewText(R.id.ingName, ingredient.getIngredient());

                return rv;
            }
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
