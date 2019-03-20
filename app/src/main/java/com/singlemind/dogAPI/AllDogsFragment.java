package com.singlemind.dogAPI;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by john on 9/27/17.
 */

public class AllDogsFragment extends Fragment {

    private static String URL = "https://dog.ceo";

    static View myView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private static LinearLayoutManager verticalLayoutManager;

    private static RecyclerAdapter adapter;

    static Retrofit retrofit;
    static DogClient dogClient;
    static DogClient dogImgClient;
    static Call<DogImage> callImage;
    static Call<DogBreed> dogBreedCall;
    @Nullable
    @Override
    /**
     * This method is called when Fragment first created.
     * Using Butterknife it binds all views to respectable ui elements
     *
     *  @return View
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.all_dogs, container, false);
        ButterKnife.bind(this, myView);
        verticalLayoutManager = new LinearLayoutManager(myView.getContext());


        adapter = new RecyclerAdapter();

        recyclerView.setLayoutManager(verticalLayoutManager);

        recyclerView.setAdapter(adapter);

        Retrofit.Builder builder = new Retrofit
                .Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        dogImgClient = retrofit.create(DogClient.class);
        dogClient = retrofit.create(DogClient.class);

        dogBreedCall = dogClient.getBreednames();


        getBreedsAndImages();

        return myView;
    }

    /***
     * This method uses retrofit to fetch list of all breeds
     * then it fetches random image for each dog breed in a list
     * finally it calls for RecyclerAdapter .all to set up Recycler view
     */
    private void getBreedsAndImages() {

        dogBreedCall.enqueue(new Callback<DogBreed>() {
            @Override
            public void onResponse(Call<DogBreed> call, Response<DogBreed> response) {
                DogBreed breeds = response.body();

                /** Fetching  IMAGE URLS for each dog breed in the list*/
                for (final String breed : breeds.getBreedList()) {
                    callImage = dogImgClient.getBreedImage(breed);
                    callImage.enqueue(new Callback<DogImage>() {
                    @Override
                    public void onResponse(Call<DogImage> call, Response<DogImage> response) {
                        DogImage dogImage = response.body();
                        adapter.addAll(ModelItem.createItems(breed,dogImage.getBreedImageUrl()));
                    }

                    @Override
                    public void onFailure(Call<DogImage> call, Throwable t) {
                        t.printStackTrace();
                    }
                } );
                }

            }

            @Override
            public void onFailure(Call<DogBreed> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(myView.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Private class that extends RecyclerView.Adapter<RecyclerViewHolder> used to bind items to RecyclerView
     */
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private ArrayList<ModelItem> items = new ArrayList<>();

        /**
         *
         * @param modelItems ArrayList<ModelItem> list to fill used as data for RecyclerView
         */
        public void addAll(List<ModelItem> modelItems) {
            int position = getItemCount();
            this.items.addAll(modelItems);
            notifyItemRangeInserted(position, this.items.size());
        }

        /**
         *
         * @param parent CureentViewgroup
         * @param viewType Current View type
         * @return singe entity of RecyclerView
         */
        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new RecyclerViewHolder(view,getFragmentManager());
        }

        /**
         * Binds entities to RecyclerView
         * @param holder single entity to bind
         * @param position its position in a list
         */
        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        /**
         * Used to monitor number of element in RecyclerView
         * @return number of items in RecyclerView
         */
        @Override
        public int getItemCount() {
            return items.size();
        }
    }



}
