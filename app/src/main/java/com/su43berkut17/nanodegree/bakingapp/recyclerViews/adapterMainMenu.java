package com.su43berkut17.nanodegree.bakingapp.recyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.su43berkut17.nanodegree.bakingapp.R;
import com.su43berkut17.nanodegree.bakingapp.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class adapterMainMenu extends RecyclerView.Adapter<adapterMainMenu.ViewHolder> {
    private List<Recipe> recipeList=new ArrayList<Recipe>();
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

       holder.title.setText(recipe.getName());
       holder.ingredients.setText(String.valueOf(recipe.getIngredients().size()));
       holder.portions.setText(String.valueOf(recipe.getServings()));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView portions;
        private TextView ingredients;

        public ViewHolder(final  View itemView){
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_title);
            portions=(TextView)itemView.findViewById(R.id.tv_amountPortions);
            ingredients=(TextView)itemView.findViewById(R.id.tv_amountIngredients);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            Recipe recipe=recipeList.get(clickedPosition);
            mClickListener.onRecipeClick(recipe);
        }
    }
}