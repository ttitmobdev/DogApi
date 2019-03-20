package com.singlemind.dogAPI;

import java.util.List;

/**
 * Created by john on 9/28/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class used to "fit-in" GSON responses to a Java class that is further used to get dog breeds
 */
public class DogBreed {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private List<String> breedList = null;

    /**
     *
     * @return Returns status of GSON requests
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @return returns List of All dog's breeds
     */
    public List<String> getBreedList() {
        return breedList;
    }

}