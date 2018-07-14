package com.su43berkut17.nanodegree.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Steps (int recId, String recShortDescription, String recDescription, String recVideoURL, String recThumbnailURL){
        id=recId;
        shortDescription=recShortDescription;
        description=recDescription;
        videoURL=recVideoURL;
        thumbnailURL=recThumbnailURL;
    }

    private Steps (Parcel out){
        id=out.readInt();
        shortDescription=out.readString();
        description=out.readString();
        videoURL=out.readString();
        thumbnailURL=out.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>(){
        @Override
        public Steps createFromParcel(Parcel out){
            return new Steps(out);
        }

        @Override
        public Steps[] newArray(int size){
            return new Steps[size];
        }
    };
}
