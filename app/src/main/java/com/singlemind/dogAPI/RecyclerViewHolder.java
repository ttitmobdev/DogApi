package com.singlemind.dogAPI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class that processes clicks on individual cards of RecyclerView, Also binds ui elements of a card
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {//implements View.OnClickListener, Parcelable
    @BindView(R.id.text)
    TextView title;
    //@BindView(R.id.image)
    //ImageView image;

    String imageUrl;

    ModelItem modelItem;

    static View myView;
    android.app.FragmentManager fragmentManager;

    /**
     * Constructor
     * @param itemView view that it was called from
     * @param fragmentManager Passes fragment manager to enable going to details on click on a card
     */
    public RecyclerViewHolder(View itemView, android.app.FragmentManager fragmentManager) {
        super(itemView);
        myView = itemView;
        this.fragmentManager = fragmentManager;
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, myView);
    }

    /**
     * Binds data from single model item to  card's ui element
     * @param modelItem item to bind
     */
    public void bind(ModelItem modelItem) {
        //   image.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), modelItem.getImgUrl()));
        //Picasso.with(myView.getContext()).load(modelItem.getImgUrl()).into(image);
        //imageUrl = modelItem.getImgUrl();
        title.setText(modelItem.getBreed());

        this.modelItem = modelItem;
    }

    /**
     * Processes user clicks on specific card from RecyclerView
     * @param view that this method was called from
     */
    @Override
    public void onClick(View view) {
        Fragment fragment = new DetailFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelable("ModelItem", modelItem);

        fragment.setArguments(arguments);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }



}