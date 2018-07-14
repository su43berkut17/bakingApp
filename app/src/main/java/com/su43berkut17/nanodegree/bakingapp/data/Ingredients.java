package com.su43berkut17.nanodegree.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {
    private int quantity;
    private String measure;
    private String ingredient;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Ingredients(int recQuantity, String recMeasure, String recIngredient){
        quantity=recQuantity;
        measure=recMeasure;
        ingredient=recIngredient;
    }

    private Ingredients(Parcel out){
        quantity=out.readInt();
        measure=out.readString();
        ingredient=out.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>(){
        @Override
        public Ingredients createFromParcel(Parcel out){
            return new Ingredients(out);
        }

        @Override
        public Ingredients[] newArray(int size){
            return new Ingredients[size];
        }
    };

}
