package com.su43berkut17.nanodegree.bakingapp.recyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.su43berkut17.nanodegree.bakingapp.R;
import com.su43berkut17.nanodegree.bakingapp.data.Steps;

import java.util.ArrayList;
import java.util.List;

public class adapterRecipeSteps extends RecyclerView.Adapter<adapterRecipeSteps.ViewHolder>{
    private List<Steps> stepList=new ArrayList<Steps>();
    private Context context;
    private stepListener mStepListener;

    public interface stepListener{
        void onStepClick(Steps steps);
    }

    public adapterRecipeSteps(List<Steps> recStep, Context recContext, stepListener recStepListener){
        this.stepList = recStep;
        this.context = recContext;
        this.mStepListener = recStepListener;
    }

    public adapterRecipeSteps.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_rv_men,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterRecipeSteps.ViewHolder holder, int position) {
        final Steps steps=stepList.get(position);

        //holder.title.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //private TextView title;

        public ViewHolder(final  View itemView){
            super(itemView);
            //title=(TextView)itemView.findViewById(R.id.tv_title);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            Steps steps=stepList.get(clickedPosition);
            mStepListener.onStepClick(steps);
        }
    }
}
