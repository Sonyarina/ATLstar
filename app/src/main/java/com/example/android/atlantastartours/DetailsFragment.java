/**
 * The general structure for this Class was obtained through the Android Studio blank template
 * for Fragments. Additional Guidance and tips were found in the Android Training lesson
 * Communicating with Other Fragments:
 * http://developer.android.com/training/basics/fragments/communicating.html
 */
package com.example.android.atlantastartours;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    //SharedPreferences to retrieve and store user preferences
    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "mypref";

    // Keys used for the bundle passed and retrieved from Activity
    private static final String ATTRACTION_CATEGORY_KEY = "Home";
    private static final String ITEM_POSITION_KEY = "item_position_key";
    private static final String ATTRACTION_LIKED_KEY = "attraction_liked_key";

    // There are 4 categories relevant to what the Detail Screen will show:
    // Dining, Shopping, Venues, and Nightlife
    private static final int HOME = 0;
    private static final int DINING = 1;
    private static final int SHOPPING = 2;
    private static final int VENUES = 3;
    private static final int NIGHTLIFE = 4;

    //Initialize member fields to default values
    //These fields will be used to generate an Attraction item to display
    private int mCategory = HOME;
    private int mPosition = 0;
    private Attraction currentAttraction;
    private boolean viewMoreInfo;
    private boolean isFavorite = false;

    // Needed to pass into method which will generate Attraction item
    private Context mContext;

    //These TextViews contain the information regarding the Attraction that was clicked on
    TextView attractionBlurb, attractionDescription, attractionName, attractionAddress,
            attractionPhone, attractionWebsite, moreInfoTextView;

    //LinearLayout table containing more information. Visibility will be toggled
    LinearLayout moreInfoTable;

    //These TextViews will be binded to the icon row TextViews in the layout.
    // They will act as buttons
    TextView callButton, webButton, mapsButton, shareButton, likeButton;

    //ImageView holding photo of attraction
    ImageView attractionImage;

    // The listener interface
    private OnFragmentInteractionListener mListener;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity. Guidance and tips for this method found in the Android Training lesson
     * Communicating with Other Fragments:
     * http://developer.android.com/training/basics/fragments/communicating.html
     */
    public interface OnFragmentInteractionListener {

        void onClickDetailsLikeButton(int category, int position, boolean isFavorite);

        void onClickDetailsCallButton(int category, int position, String phoneLink);

        void onClickDetailsWebButton(int category, int position, String website);

        void onClickDetailsShareButton(int category, int position, String attractionInfo);

        void onClickDetailsMapButton(int category, int position, String geolocation);

        void detachDetailScreen();
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category    of item. Used to generate an Attraction item
     * @param position    of item in Array, used to generate an Attraction item
     * @param isFavorited boolean of whether place is a favorite
     * @return A new instance of fragment DetailsFragment.
     */
    public static DetailsFragment newInstance(int category, int position, boolean isFavorited) {

        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ATTRACTION_CATEGORY_KEY, category);
        args.putInt(ITEM_POSITION_KEY, position);
        args.putBoolean(ATTRACTION_LIKED_KEY, isFavorited);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This method is required in a Fragment class.
     * It's overridden to retrieve arguments and populate the attraction item to be displayed in
     * onCreateView
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getInt(ATTRACTION_CATEGORY_KEY);
            mPosition = getArguments().getInt(ITEM_POSITION_KEY);
            isFavorite = getArguments().getBoolean(ATTRACTION_LIKED_KEY);
        }

        // Use argument to populate the Attraction item
        currentAttraction = new Attraction().getDetailAttraction(mContext, mCategory, mPosition);
        if (currentAttraction == null) {
            Log.v("DetailsFragment", "currentAttraction is null");
        } else {
            Log.v("DetailsFragment", "currentAttraction is " + currentAttraction.getAttractionName());
        }
    }

    /**
     * The method onCreateView is required in a Fragment. It's overwritten to specify the layout
     * resource ID, and set up views
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.attraction_details_fragment, container, false);

        //Find and Bind TextViews that show icons, text, and attraction image
        callButton = view.findViewById(R.id.call_button);
        webButton = view.findViewById(R.id.web_button);
        mapsButton = view.findViewById(R.id.map_button);
        shareButton = view.findViewById(R.id.share_button);
        attractionBlurb = view.findViewById(R.id.attraction_snippet);
        attractionDescription = view.findViewById(R.id.attraction_description);
        attractionName = view.findViewById(R.id.attraction_name);
        attractionImage = view.findViewById(R.id.attraction_image);
        attractionPhone = view.findViewById(R.id.attraction_phone);
        attractionAddress = view.findViewById(R.id.attraction_address);
        attractionWebsite = view.findViewById(R.id.attraction_website_text);
        moreInfoTextView = view.findViewById(R.id.more_info);
        moreInfoTable = view.findViewById(R.id.more_info_table);

        //Initialize boolean that determines whether the user wants more info to false
        viewMoreInfo = false;
        moreInfoTable.setVisibility(View.GONE);

        // Set up Attraction Image
        attractionImage.setImageResource(currentAttraction.getAttractionImageId());

        // Populate the TextViews with the correct information
        attractionName.setText(currentAttraction.getAttractionName());
        attractionBlurb.setText(currentAttraction.getAttractionBlurb());
        attractionDescription.setText(currentAttraction.getAttractionDescription());
        attractionAddress.setText(currentAttraction.getAttractionAddress());
        attractionPhone.setText(currentAttraction.getAttractionPhoneNumberText());
        attractionWebsite.setText(currentAttraction.getAttractionWebsite());

        // Set up like button
        likeButton = view.findViewById(R.id.like_button);

        // Initialize SharedPreferences
        sharedPreferences = mContext.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        if (isFavorite) {

            Log.v("DetailsFrag", currentAttraction.getAttractionName() + " is a favorite");
            likeButton.setText(R.string.fav_saved);

            // Change heart icon to be the filled in pink heart shape
            // Create drawable object and set equal to the liked shape, then change color
            // to pink and pass into the TextView's compound drawable
            Drawable heart = getResources().getDrawable(R.drawable.liked);
            heart.setColorFilter(getResources().getColor(R.color.secondaryLightColor), PorterDuff.Mode
                    .SRC_ATOP);
            likeButton.setCompoundDrawablesWithIntrinsicBounds(null, heart, null, null);

        } else {
            likeButton.setText(R.string.fav_save);

            Log.v("DetailsFrag", currentAttraction.getAttractionName() + " is NOT a favorite");

            // Create drawable object and set equal to the like shape, then change color to black
            // and pass into the TextView's compound drawable
            Drawable heart = getResources().getDrawable(R.drawable.like);
            heart.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
            likeButton.setCompoundDrawablesWithIntrinsicBounds(null, heart, null, null);
        }

        // Attach listener to More Info text view
        // In the default layout, the phone number and website address are hidden from view
        // Clicking View More Info will make that information visible, and the user can then
        // hide the info again by pressing View Less Info
        moreInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewMoreInfo) {

                    // User wants to see more information
                    moreInfoTable.setVisibility(View.VISIBLE);

                    // Change TextView text to say "View Less Info"
                    moreInfoTextView.setText(R.string.less_info_link);

                    // Set viewMoreInfo boolean to true
                    viewMoreInfo = true;

                } else {
                    // The user chose to see less information. Hide the more info table
                    moreInfoTable.setVisibility(View.GONE);

                    // Change TextView text to say "View More Info"
                    moreInfoTextView.setText(R.string.more_info_link);

                    // Set viewMoreInfo boolean to false
                    viewMoreInfo = false;
                }
            }
        });

        // Attach listeners to the buttons
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onClickDetailsCallButton(mCategory, mPosition, currentAttraction.getAttractionPhoneNumberLink());
            }
        });

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onClickDetailsWebButton(mCategory, mPosition, currentAttraction.getAttractionWebsite());
            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickDetailsMapButton(mCategory, mPosition, currentAttraction.getAttractionCoordinates());
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickDetailsShareButton(mCategory, mPosition, currentAttraction.toString());
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFavorite) {
                    // If prior to the click, the boolean was set to true, that means that the user
                    // wanted to REMOVE the Attraction from favorites. Reset boolean
                    // isFavorite to False
                    isFavorite = false;

                    // Change heart icon to be the black border shape
                    // Create drawable object and set equal to the like shape, then change color to black
                    // and pass into the TextView's compound drawable
                    Drawable heart = getResources().getDrawable(R.drawable.like);
                    heart.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                    likeButton.setCompoundDrawablesWithIntrinsicBounds(null, heart, null, null);

                    // Update TextView text to say Save
                    likeButton.setText(R.string.fav_save);
                } else {
                    // If prior to the click, the boolean was set to false, that means that the
                    // user wanted to ADD the Attraction to favorites. Reset boolean isFavorite to
                    // true
                    isFavorite = true;

                    // Change heart icon to be the filled in pink heart shape
                    // Create drawable object and set equal to the liked shape, then change color
                    // to pink and pass into the TextView's compound drawable
                    Drawable heart = getResources().getDrawable(R.drawable.liked);
                    heart.setColorFilter(getResources().getColor(R.color.secondaryLightColor), PorterDuff.Mode
                            .SRC_ATOP);
                    likeButton.setCompoundDrawablesWithIntrinsicBounds(null, heart, null, null);

                    // Update TextView text to say Saved
                    likeButton.setText(R.string.fav_saved);
                }
                // Pass to the onClickDetailsLikeButton method, which is implemented in MainActivity
                mListener.onClickDetailsLikeButton(mCategory, mPosition, isFavorite);
            }
        });

        // Return view
        return view;
    }

    /**
     * This method was overriden in order to grab the context which is passed in as a parameter,
     * and use the context to create a listener for the buttons and links
     *
     * @param context The context passed (by MainActivity?)
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            Log.v("detailsfragment", "details screen onAttach has run");

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * This method calls the detailDetachScreen() in MainActivity when the fragment is detached
     * The tasks of that method include changing the toolbar look and toggling the visibility of
     * views from the main activity layout
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener.detachDetailScreen();
        mListener = null;
    }
}