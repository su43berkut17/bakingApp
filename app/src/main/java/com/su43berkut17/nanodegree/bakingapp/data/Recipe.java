package com.su43berkut17.nanodegree.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Recipe implements Parcelable{
    //the details
    private int id;
    private String name;
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Recipe (int recId,String recName, List<Ingredients> recIngredients, List<Steps> recSteps,int recServings, String recImage){
        id=recId;
        name=recName;
        ingredients=recIngredients;
        steps=recSteps;
        servings=recServings;
        image=recImage;
    }

    private Recipe(Parcel out){
        id=out.readInt();
        name=out.readString();
        out.readTypedList(ingredients,Ingredients.CREATOR);
        out.readTypedList(steps,Steps.CREATOR);
        servings=out.readInt();
        image=out.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){
        @Override
        public Recipe createFromParcel(Parcel out){
            return new Recipe(out);
        }

        @Override
        public Recipe[] newArray(int size){
            return new Recipe[size];
        }
    };
}
