package com.su43berkut17.nanodegree.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ingredientsWidgetRemoteViewService extends RemoteViewsService{

    public static final String TAG="WidgetRemoteViewService";

    //we set the other stuff


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent){
        return new adapterWidgetRemoteView(this.getApplicationContext(),intent);
    }

    class adapterWidgetRemoteView implements
            RemoteViewsService.RemoteViewsFactory{

        private List<Ingredients> ingredientsList;
        private Context mContext;

        public adapterWidgetRemoteView(Context context,Intent intent){

            mContext=context;
            int numIn=intent.getIntExtra("NUMBER",0);
            ArrayList<String> ingredients=new ArrayList<>();
            ArrayList<String> amount=new ArrayList<>();
            ArrayList<String> measure=new ArrayList<>();

            ingredients=intent.getStringArrayListExtra("INGREDIENTS");
            amount=intent.getStringArrayListExtra("AMOUNT");
            measure=intent.getStringArrayListExtra("MEASURE");

            ingredientsList=new ArrayList<>();

            for (int i=0;i<numIn;i++){
                Ingredients temp=new Ingredients(Double.parseDouble(amount.get(i)),measure.get(i),ingredients.get(i));
                ingredientsList.add(temp);
            }

            //Log.i(TAG,"we start the object and process the ingredients new list "+numIn);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            //we will load the ingredients in the content provider
            ingredientsList=IngredientsWidget.mIngredientsList;
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
                //Log.i(TAG,"ingredients list is null");
                return null;
            }else {
                //Log.i(TAG,"We begin feeding the list, size of object "+ingredientsList.size()+" -- "+mContext.getPackageName());
                RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.rv_ingredients_widget_list);
                Ingredients ingredient = ingredientsList.get(position);

                rv.setTextViewText(R.id.ingName, ingredient.getQuantity()+" "+ingredient.getMeasure()+" - "+ingredient.getIngredient());

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