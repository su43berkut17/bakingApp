package com.su43berkut17.nanodegree.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class StepMenuContainer implements Parcelable{
    //contents
    private int id;
    private String type;
    private List<Ingredients> ingredient;
    private List<Steps> step;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<Ingredients> getIngredient() {
        return ingredient;
    }

    public List<Steps> getStep() {
        return step;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIngredient(List<Ingredients> ingredient) {
        this.ingredient = ingredient;
    }

    public void setStep(List<Steps> step) {
        this.step = step;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public StepMenuContainer(int recId, String recType, List<Ingredients> recIngredients, List<Steps> recStep){
        id=recId;
        type=recType;
        ingredient=recIngredients;
        step=recStep;
    }

    private StepMenuContainer(Parcel out){
        id=out.readInt();
        type=out.readString();
        out.readTypedList(ingredient,Ingredients.CREATOR);
        out.readTypedList(step,Steps.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeTypedList(ingredient);
        parcel.writeTypedList(step);
    }

    public static final Parcelable.Creator<StepMenuContainer> CREATOR = new Parcelable.Creator<StepMenuContainer>(){
        @Override
        public StepMenuContainer createFromParcel(Parcel out){
            return new StepMenuContainer(out);
        }

        @Override
        public StepMenuContainer[] newArray(int size){
            return new StepMenuContainer[size];
        }
    };
}
