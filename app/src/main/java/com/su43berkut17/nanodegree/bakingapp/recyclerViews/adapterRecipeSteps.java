package com.su43berkut17.nanodegree.bakingapp.recyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.su43berkut17.nanodegree.bakingapp.R;
import com.su43berkut17.nanodegree.bakingapp.data.Steps;

import java.util.ArrayList;
import java.util.List;

public class adapterRecipeSteps extends RecyclerView.Adapter<adapterRecipeSteps.ViewHolder>{
    private final static String TAG="AdapSteps";

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
        Log.i(TAG,"position is: "+position);

        holder.title.setText(steps.getShortDescription());
        holder.number.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView number;
        private TextView title;

        public ViewHolder(final  View itemView){
            super(itemView);
            number=(TextView)itemView.findViewById(R.id.tv_step_number);
            title=(TextView)itemView.findViewById(R.id.tv_step_title);

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
