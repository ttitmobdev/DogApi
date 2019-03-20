package com.singlemind.dogAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by john on 9/28/17.
 */

public interface DogClient {
    @GET("/api/breeds/list")
    Call<DogBreed>  getBreednames();

    @GET("/api/breed/{breedname}/images/random")
    Call<DogImage> getBreedImage(@Path("breedname") String breedName);
}
