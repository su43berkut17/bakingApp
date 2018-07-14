package com.su43berkut17.nanodegree.bakingapp.recyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.su43berkut17.nanodegree.bakingapp.R;
import com.su43berkut17.nanodegree.bakingapp.data.Recipe;

import java.util.List;

public class adapterMainMenu extends RecyclerView.Adapter<adapterMainMenu.ViewHolder> {
    private List<Recipe> recipeList;
    private Context context;
    final private recipeListener mClickListener;

    public interface recipeListener{
        void onRecipeClick(Recipe recipe);
    }

    public adapterMainMenu(List<Recipe> recipeListRec, Context context, recipeListener listener){
        this.recipeList=recipeListRec;
        this.context=context;
        this.mClickListener=listener;
    }

    public adapterMainMenu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_rv_men,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterMainMenu.ViewHolder holder, int position) {
        final Recipe recipe=recipeList.get(position);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(final  View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

        }
    }
}