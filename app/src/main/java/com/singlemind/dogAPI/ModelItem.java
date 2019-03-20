package com.singlemind.dogAPI;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 9/28/17.
 */

/**
 *  Object that contains information that is required to build up a single card
 */
class ModelItem implements Parcelable {
    private String breed;
    private String imgURL;
    private static String URL = "https://dog.ceo";

    /**
     *
     * @param breed String of a breed Name  displayed in card's texView
     * @param imgURL String that contains URL to an image displayed in a card
     */

    public ModelItem(String breed, String imgURL) {
        this.breed = breed;
        this.imgURL = imgURL;
    }

    /**
     *
     * @return String value of a breed name
     */
    public String getBreed() {
        return breed;
    }
    /**
     *
     * @return String value of a image URL for current breed
     */
    public String getImgUrl() {
        return imgURL;
    }

    /**
     *
     * @param dogBreed name of a dog breed
     * @param imageUrl link to a dog breed image
     * @return List of all ModelItems
     */
    public static List<ModelItem> createItems(String dogBreed, String imageUrl) {
        ArrayList<ModelItem> itemsList = new ArrayList<>();
        itemsList.add(new ModelItem(dogBreed, imageUrl));
        return itemsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param dest parcel used to write data
     * @param flags flag to identify your parsed object
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.breed);
        dest.writeString(this.imgURL);
    }

    protected ModelItem(Parcel in) {
        this.breed = in.readString();
        this.imgURL = in.readString();
    }

    public static final Parcelable.Creator<ModelItem> CREATOR = new Parcelable.Creator<ModelItem>() {
        @Override
        public ModelItem createFromParcel(Parcel source) {
            return new ModelItem(source);
        }

        @Override
        public ModelItem[] newArray(int size) {
            return new ModelItem[size];
        }
    };
}
