package com.su43berkut17.nanodegree.bakingapp.recyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.su43berkut17.nanodegree.bakingapp.R;
import com.su43berkut17.nanodegree.bakingapp.data.Ingredients;

import java.util.ArrayList;
import java.util.List;

public class adapterIngredients extends RecyclerView.Adapter<adapterIngredients.ViewHolder>{
    private List<Ingredients> ingredientsList=new ArrayList<>();
    private Context context;

    public adapterIngredients(List<Ingredients> recIngredients, Context recContext){
        this.ingredientsList=recIngredients;
        this.context=recContext;
    }

    public adapterIngredients.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_rv_ingre,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterIngredients.ViewHolder holder, int position) {
        final Ingredients recipeIng=ingredientsList.get(position);

        holder.ingredient.setText(String.valueOf(position+1)+" - "+recipeIng.getIngredient());
        holder.portion.setText(String.valueOf(recipeIng.getMeasure()));
        holder.quantity.setText(String.valueOf(recipeIng.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView ingredient;
        private TextView portion;
        private TextView quantity;

        public ViewHolder(final View itemView){
            super(itemView);
            ingredient=(TextView)itemView.findViewById(R.id.tv_ingredient);
            portion=(TextView)itemView.findViewById(R.id.tv_measure);
            quantity=(TextView)itemView.findViewById(R.id.tv_quantity);
        }
    }
}
