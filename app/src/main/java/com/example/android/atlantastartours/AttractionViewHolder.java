/**
 * Tips and Guidance for this Class were obtained from the online article:
 * "Your ViewHolders are Dumb. Make ’em Not Dumb" at
 * https://jonfhancock.com/your-viewholders-are-dumb-make-em-not-dumb-82e6f73f630c and
 * Android Horizontal List View Tutorial
 * https://www.androidtutorialpoint.com/material-design/android-horizontal-listview-tutorial/
 */
package com.example.android.atlantastartours;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AttractionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView attractionNameTextView;
    public TextView attractionDescriptionTextView;
    public ImageView attractionImageView;
    public ImageView likeImageView;

    public Attraction attractionItem;

    public Context context;

    AttractionRecyclerViewAdapter attractionRecyclerViewAdapter;

    AttractionListener listener;

    //SharedPreferences to retrieve and store user preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;

    public static final String MY_PREFERENCES = "mypref";

    public interface AttractionListener {
        void onLikeButtonClicked(Attraction attractionItem, int position, boolean wasLiked);

        void onAttractionClicked(Attraction attractionItem, int position,
                                 AttractionRecyclerViewAdapter adapter);
    }

    /**
     * Creates a new custom view holder to hold the view to display
     * in the RecyclerView.
     *
     * @param v        The view in which to display the data.
     * @param vContext The adapter that manages the the data and
     *                 views for the RecyclerView.
     * @param listener The listener for the AttractionViewHolder
     * @param adapter  A copy of the AttractionRecyclerViewAdapter which is needed to call update
     *                 notifyItemChanged from MainActivity
     */
    public AttractionViewHolder(View v, Context vContext, final AttractionListener listener,
                                AttractionRecyclerViewAdapter adapter) {
        super(v);

        attractionRecyclerViewAdapter = adapter;

        context = vContext;

        this.listener = listener;
        // Find Item List Layout views
        attractionNameTextView = v.findViewById(R.id.attraction_name);
        attractionDescriptionTextView = v.findViewById(R.id.attraction_snippet);
        attractionImageView = v.findViewById(R.id.attraction_image);
        likeImageView = v.findViewById(R.id.like_button);

        //Set a Click Listener on Attraction Name, Description, and Image
        //If user clicks on either of those things, they will be taken to the Attraction
        //Detail Screen. Example and tips found at blog:
        // https://androidessence.com/android/recyclerview-vs-listview
        attractionNameTextView.setOnClickListener(this);
        attractionDescriptionTextView.setOnClickListener(this);
        attractionImageView.setOnClickListener(this);

        //Set a Click Listener on Like button to keep track of user favorites
        // Example and tips found at
        // https://www.androidtutorialpoint.com/material-design/android-horizontal-listview-tutorial
        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get adapter position
                int position = getAdapterPosition();

                int id = (int) likeImageView.getTag();
                if (id == R.drawable.like) {

                    likeImageView.setTag(R.drawable.liked);
                    likeImageView.setImageResource(R.drawable.liked);
                    DrawableCompat.setTint(likeImageView.getDrawable(), ContextCompat.getColor(context,
                            R.color.secondaryLightColor));

                    //If user presses the like icon, the "heart" icon will change from a border
                    // shape to a filled in heart shape. A toast message will display
                    // confirming the user's choice to enter the attraction into favorites list
                    Toast.makeText(context, attractionNameTextView.getText() + " added to favorites", Toast.LENGTH_SHORT).show();

                    if (attractionItem != null) {
                        listener.onLikeButtonClicked(attractionItem, position, true);
                    }

                } else {

                    //If the like icon was previously pressed for an item, and the user presses
                    // the icon again, the item will be removed from favorites list.
                    // The icon image will change from the filled in "heart" shape, to the border
                    // version. A toast message will display confirming the
                    // user's choice to remove the attraction from favorites list
                    likeImageView.clearColorFilter();
                    likeImageView.setTag(R.drawable.like);
                    likeImageView.setImageResource(R.drawable.like);

                    // Show toast message, Clear pink Tint
                    Toast.makeText(context, attractionNameTextView.getText() + " removed from " +
                            "favorites", Toast.LENGTH_SHORT).show();

                    if (attractionItem != null) {
                        listener.onLikeButtonClicked(attractionItem, position, false);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();

        if (attractionItem == null) {

        } else {

            // pass item to main activity
            listener.onAttractionClicked(attractionItem, position, attractionRecyclerViewAdapter);
        }
    }

    /**
     * Called by onBindViewHolder method in the RecyclerView.Adapter, It sets the contents of
     * an item at a given position in the RecyclerView.
     *
     * @param item The Attraction object item at the position obtained in onBindViewHolder
     */
    public void setItem(Attraction item) {
        this.attractionItem = item;

        attractionNameTextView.setText(attractionItem.getAttractionName());

        String blurbText = attractionItem.getAttractionBlurb();

        attractionDescriptionTextView.setText(blurbText);
        attractionImageView.setImageResource(attractionItem.getAttractionImageId());
        attractionImageView.setTag(attractionItem.getAttractionImageId());

        String favorite = attractionItem.getAttractionName();

        // Get the sharedpreferences data to see if the item is a favorite
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, android.content.Context.MODE_PRIVATE);

        if (sharedPreferences.contains(favorite)) {

            // If the attraction name is in shared preferences, that means it's a favorite
            // Update boolean variable, and setup the drawable to reflect whether the attraction
            // was liked or not
            boolean wasAttractionLiked = sharedPreferences.getBoolean(favorite, false);

            if (wasAttractionLiked) {
                likeImageView.setImageResource(R.drawable.liked);
                likeImageView.setTag(R.drawable.liked);
                DrawableCompat.setTint(likeImageView.getDrawable(), ContextCompat.getColor(context,
                        R.color.secondaryLightColor));
            }
        } else {
            likeImageView.setTag(R.drawable.like);
            likeImageView.setImageResource(R.drawable.like);
        }
    }
}

