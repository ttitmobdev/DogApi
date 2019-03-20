package com.singlemind.dogAPI;

/**
 * Created by john on 9/29/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Class used to "fit-in" GSON responses to a Java class that is further used to get dog's imges URLs
 */
public class DogImage  {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String breedImageUrl;
    /**
     *
     * @return Returns status of GSON requests
     */
    public String getStatus() {
        return status;
    }


    /**
     *
     * @return String value of a dog image URL
     */
    public String getBreedImageUrl() {
        return breedImageUrl;
    }


}