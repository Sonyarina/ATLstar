/**
 * The general structure for this Class was obtained through the Android Studio blank template
 * for Fragments. Additional Guidance and tips were found in the Android Training lesson
 * Communicating with Other Fragments:
 * http://developer.android.com/training/basics/fragments/communicating.html
 */
package com.example.android.atlantastartours;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // Category ID for HomeFragment
    private static final int HOME = 0;

    //ArrayList holding Attraction objects
    static ArrayList<Attraction> attractions = new ArrayList<>();

    // RecyclerView object which will manage the list view recycling
    RecyclerView attractionRecyclerView;

    // Listener used to call methods implemented in MainActivity
    AttractionViewHolder.AttractionListener listener;

    // Context which will be used to populate array
    Context mContext;

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.v("HomeFragment", "onResume running");

    }

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This method checks if the hosting activity has implemented
     * the OnFragmentInteractionListener interface. If it does not,
     * an exception is thrown.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        Log.v("HomeFragment", "onAttach running");

        if (context instanceof AttractionViewHolder.AttractionListener) {
            listener = (AttractionViewHolder.AttractionListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("HomeFragment", "onCreate running");

    /*    // Get String array resources and assign to String to populate ArrayList
        String names[] = getResources().getStringArray(R.array.attraction_name);
        String categories[] = getResources().getStringArray(R.array.attraction_category);
        String addresses[] = getResources().getStringArray(R.array.attraction_address);
        String coordinates[] = getResources().getStringArray(R.array.attraction_coordinates);
        String phones[] = getResources().getStringArray(R.array.attraction_phone);
        String phoneLinks[] = getResources().getStringArray(R.array.attraction_phone_link);
        String website[] = getResources().getStringArray(R.array.attraction_website);
        String descriptions[] = getResources().getStringArray(R.array.attraction_description);
        String blurbs[] = getResources().getStringArray(R.array.attraction_blurb);

        // Get array of image resource ids
        TypedArray attractionImages = getResources().obtainTypedArray(R.array.attraction_images);

        // Populate ArrayList from the string-arrays
        for (int i = 0; i < names.length; i++) {
            attractions.add(new Attraction(names[i], categories[i], addresses[i], coordinates[i],
                    phones[i], phoneLinks[i], website[i], descriptions[i], blurbs[i],
                    attractionImages.getResourceId(i, -1)));
        }

        // Next, recycle TypedArray
        attractionImages.recycle();*/


        // Populist a list of Attractions using the context that was passed in during onAttach
        attractions = new Attraction().getFullListArray(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        // Inflate the layout for this fragment

        Log.v("HomeFragment", "onCreateView running");

        attractionRecyclerView = view.findViewById(R.id.recycler_list);
        attractionRecyclerView.setHasFixedSize(true);
        LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(getActivity());


        //This is how orientation is determined
        myLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (attractions.size() > 0 & attractionRecyclerView != null) {
            attractionRecyclerView.setAdapter(new AttractionRecyclerViewAdapter(attractions, R.layout
                    .horizontal_item_list, getActivity(), listener));
        }

        attractionRecyclerView.setLayoutManager(myLinearLayoutManager);

        return view;
    }

    /**
     * Method from superclass. Used for troubleshooting app
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.v("HomeFrag", "HomeFragment detached");
        listener = null;
    }
}
