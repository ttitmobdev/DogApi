package com.singlemind.dogAPI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by john on 9/29/17.
 */

/**
 * Details class used to see more pictures of a chosen breed
 */
public class DetailFragment extends Fragment  {

    private static String URL = "https://dog.ceo";

    View myView;


    

    Retrofit retrofit;
    DogClient dogClient;
    Call<DogImage> callImage;

    /**
     * Creates view and binds UI elements
     * @param inflater tool used to create view
     * @param container Current view group
     * @param savedInstanceState Bundle that is used to pass data between fragments
     * @return View with binded ui elements ready to work
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myView = inflater.inflate(R.layout.detail_fragment, null, false);
        ButterKnife.bind(this, myView);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ModelItem modelItem = bundle.getParcelable("ModelItem");
            tvMore.setText(modelItem.getBreed());
            Picasso.with(myView.getContext()).load(modelItem.getImgUrl()).into(ivMore);
        }

        Retrofit.Builder builder = new Retrofit
                .Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        dogClient = retrofit.create(DogClient.class);
        callImage = dogClient.getBreedImage(tvMore.getText().toString());

        return myView;
    }

    /**
     * Uses ButterKinfe to process clicks on button
     * @param view from which this method was called from
     */
    @OnClick(R.id.btnMore)
    /**
     * fetches new image URLs of a user selected breed, using Retrofit
     * switches images of a ImageView "ivMore" using Picasso library
     */
    void clickButton(View view) {
        if (view.getId() == R.id.btnMore) {

            callImage.enqueue(new Callback<DogImage>() {
                @Override
                public void onResponse(Call<DogImage> call, Response<DogImage> response) {
                    DogImage dogImage = response.body();
                    Picasso.with(myView.getContext()).load(dogImage.getBreedImageUrl()).into(ivMore);
                }

                @Override
                public void onFailure(Call<DogImage> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

}
