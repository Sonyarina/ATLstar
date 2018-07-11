/**
 * Tips and Guidance for this Class were obtained from the online article:
 * "Your ViewHolders are Dumb. Make ’em Not Dumb" at
 * https://jonfhancock.com/your-viewholders-are-dumb-make-em-not-dumb-82e6f73f630c and
 * Android Horizontal List View Tutorial
 * https://www.androidtutorialpoint.com/material-design/android-horizontal-listview-tutorial/
 */
package com.example.android.atlantastartours;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Guidance and tips for this class obtained from Android Developer:
 * Simple Adapter for a RecyclerView.
 * This class includes click handlers for each item in the ViewHolder.
 * ViewHolder (AttractionViewHolder) is subclassed within this class
 */
public class AttractionRecyclerViewAdapter extends RecyclerView
        .Adapter<AttractionViewHolder> {

    // Variable keeps track of context
    private Context context;

    // Variable contains the resource ID for the item list layout
    private int resourceLayoutID;

    // ArrayList containing Attraction objects
    private ArrayList<Attraction> attractionsList;

    private AttractionViewHolder.AttractionListener attractionListener;

    /**
     * Custom constructor for AttractionRecyclerViewAdapter
     *
     * @param attractionData The ArrayList of Attraction objects
     * @param resource       int that holds item list layout resource ID
     * @param vContext       the context used to inflate the file
     */
    public AttractionRecyclerViewAdapter(ArrayList<Attraction> attractionData, int
            resource, Context vContext, AttractionViewHolder.AttractionListener attractionListener) {

        attractionsList = attractionData;
        context = vContext;
        resourceLayoutID = resource;
        this.attractionListener = attractionListener;
        Log.v("RVAdaptor", "AttractionRecyclerViewAdapter constructor was called");

    }

    /**
     * onCreateViewHolder(ViewGroup, int) is called on to create a new RecyclerView.ViewHolder
     * and initialize some private fields to be used by RecyclerView. (From Developer.Android.com)
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // Pass in int variable containing resource layout ID
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceLayoutID, parent, false);

        Log.v("RVAdaptor", "OnCreateViewHolder was called");

        return new AttractionViewHolder(view, context, attractionListener, this);
    }

    /**
     * Developer.Android.com: Called by RecyclerView to display the data at the specified position.
     * This method will call the setItem method, which will update the contents of the itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final AttractionViewHolder holder, int position) {

        AttractionViewHolder viewHolder = (AttractionViewHolder) holder;
        viewHolder.setItem(attractionsList.get(position));
        Log.v("RVAdaptor", "OnBindViewHolder was called");

    }

    /**
     * Method Returns the total number of items in the data set held by the adapter.
     **/
    @Override
    public int getItemCount() {
        return attractionsList.size();
    }

    /**
     * Method used to update recyclerview based on new information
     * @param position the position of the adapter that needs to be updated
     **/
    public void updateAttractionList(int position){
        notifyItemChanged(position);
    }
}
