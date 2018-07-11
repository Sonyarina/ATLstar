/*
 * Images used in this project were obtained on the attractions' websites. This project is for
 * educational purposes only.
 */
package com.example.android.atlantastartours;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements AttractionViewHolder
        .AttractionListener, DetailsFragment.OnFragmentInteractionListener,
        CategoryPagerAdapter.MyOnBackStackChangedListener {

    // Name of SharedPreferences file
    public static final String MY_PREFERENCES = "mypref";

    //SharedPreferences to retrieve and store user preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;

    // There are 5 categories relevant to MainActivity
    // Home, Dining, Shopping, Venues, and Nightlife
    // The attraction details fragment will also have a constant reference
    private static final int HOME = 0;
    private static final int DINING = 1;
    private static final int SHOPPING = 2;
    private static final int VENUES = 3;
    private static final int NIGHTLIFE = 4;
    private static final int DETAIL_SCREEN = 5;

    //The current Attraction object
    Attraction currentAttractionItem;

    // Root view of the main activity layout
    RelativeLayout topHalfView;

    // TextView containing the slogan for the category
    TextView sloganTextView;

    // LinearLayout view which holds the layout used for tab fragments
    LinearLayout tabsLinearLayoutView;

    // FrameLayout view which holds the layout used for Attraction Detail Screen
    FrameLayout detailsFrameLayoutView;

    // Layout manager that allows the user to flip left and right through pages of data
    ViewPager viewPager;

    // App Support Toolbar
    Toolbar mainToolbar;

    // Create new CategoryPagerAdapter object to manage which fragment should be shown on each page
    CategoryPagerAdapter adapter;

    // Keeps track of current adapter position of the recycler view (needed in method call to
    // notifyItemChanged(position)
    int currentAdapterPosition = 0;

    // A RecyclerViewAdapter object
    AttractionRecyclerViewAdapter attractionRecyclerViewAdapter;

    // Keeps track of whether like button was pressed on details fragment
    boolean wasLikeButtonPressedInDetailsFragment = false;

    // Keep track of DetailsFragment "parent" fragment
    // By default will be initialized to home category
    int detailsFragmentParent = HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        // Find views
        topHalfView = findViewById(R.id.top_half_tab_layout);
        tabsLinearLayoutView = findViewById(R.id.main_tabs_layout);
        detailsFrameLayoutView = findViewById(R.id.attraction_details_fragment_container);
        sloganTextView = findViewById(R.id.category_slogan);

        // Set up textview
        sloganTextView.setText(R.string.home_slogan);

        // Set up toolbar (action bar)
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        // Configure the Up button on main toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mainToolbar.setLogo(R.drawable.app_title);

        // Find the ViewPager which allows user to swipe between fragments
        // Then assign it to new ViewPager object
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create new CategoryPagerAdapter object to manage which fragment should be shown on each page
        adapter = new CategoryPagerAdapter(this, getSupportFragmentManager());

        // Set the above created adapter onto the viewPager
        viewPager.setAdapter(adapter);

        // Set OnPageChangeListener to the viewpager. This requires the implementation of three
        // abstract methods from the superclass
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * This method will be invoked when the current page is scrolled, either as part
             * of a programmatically initiated smooth scroll or a user initiated touch scroll.
             *
             * @param position Position index of the first page currently being displayed.
             *                 Page position+1 will be visible if positionOffset is nonzero.
             * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
             * @param positionOffsetPixels Value in pixels indicating the offset from position.
             * (Javadoc copied from superclass.)
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v("MainActivity", "Page " + position + "was scrolled");
            }

            /**
             * This method will be invoked when a new page becomes selected. This method is required to
             * implement the ViewPager.OnPageSelected interface from
             * the Home, Dining, Shopping, Venues, Nightlife fragment classes. It provides instructions
             * for how to change GUI when a new category is selected and visible
             * @param position the current page number/position
             */
            @Override
            public void onPageSelected(int position) {
                Log.v("MainActivity", "Page is" + position);
                switch (position) {
                    case HOME:
                        sloganTextView.setText(R.string.home_slogan);
                        break;
                    case DINING:
                        sloganTextView.setVisibility(View.VISIBLE);
                        sloganTextView.setText(R.string.dine_slogan);
                        break;
                    case SHOPPING:
                        sloganTextView.setText(R.string.shop_slogan);
                        break;
                    case VENUES:
                        sloganTextView.setText(R.string.venues_slogan);
                        break;
                    case NIGHTLIFE:
                        sloganTextView.setText(R.string.nightlife_slogan);
                        break;
                }
            }

            /**
             * Called when the scroll state changes. Useful for
             * discovering when the user begins dragging, when the pager is automatically
             * settling to the current page, or when it is fully stopped/idle.
             * (Javadoc copied from superclass.)
             *
             * @param state The new scroll state.
             * @see ViewPager#SCROLL_STATE_IDLE
             * @see ViewPager#SCROLL_STATE_DRAGGING
             * @see ViewPager#SCROLL_STATE_SETTLING
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.v("MainActivity", "Page state is " + state);
            }
        });
        // Give the TabLayout the ViewPager
        // Find view containing TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        // Connect the tab layout with the view pager.
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onLikeButtonClicked(Attraction attractionItem, int position, boolean wasLiked) {

        // Get the name of the attraction and then check if the name is stored in SharedPreferences
        String favorite = attractionItem.getAttractionName();

        sharedPrefEditor = sharedPreferences.edit();
        // If the attraction was liked, add the attraction name to sharedpreferences with the
        // value true. If the attraction was unliked, remove the attraction name from
        // sharedpreferences

        if (wasLiked) {
            sharedPrefEditor.putBoolean(favorite, true);
            sharedPrefEditor.apply();

        } else {

            sharedPrefEditor.remove(favorite).commit();
        }
        if (sharedPreferences.contains(favorite)) {
        }
    }

    /**
     * This method is required to implement the AttractionListener interface from the
     * AttractionViewHolder class
     *
     * @param attractionItem the Attraction object that was clicked on
     * @param position       the position of the RecyclerView adapter when item was clicked on
     * @param listAdapter    a copy AttractionRecyclerViewAdapter which will be needed to update
     *                       list  with new information
     */
    @Override
    public void onAttractionClicked(Attraction attractionItem, int position,
                                    AttractionRecyclerViewAdapter listAdapter) {

        attractionRecyclerViewAdapter = listAdapter;

        currentAttractionItem = attractionItem;

        String attractionName = attractionItem.getAttractionName();

        boolean wasAttractionLiked = false;

        if (sharedPreferences.contains(attractionName)) {

            wasAttractionLiked = true;
        }

        // Get category code from viewPager. The page number represents the category code as well
        // Category codes: 0 - Home, 1 - Dining, 2 - Shopping, 3  Venues, 4 - Nightlife
        //Get Attraction category and convert to code
        detailsFragmentParent = viewPager.getCurrentItem();

        Log.v("MainActivity", "The parent fragment was " + detailsFragmentParent);

        //Instantiate the DetailsFragment
        DetailsFragment detailsFragment = DetailsFragment.newInstance(detailsFragmentParent, position,
                wasAttractionLiked);

        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the DetailsFragment
        fragmentTransaction.add(R.id.attraction_details_fragment_container,
                detailsFragment, "details").addToBackStack(null).commit();

        // Call method to Adjust the toolbar for DetailFragment display
        categoryViewSetup(DETAIL_SCREEN);
    }

    /**
     * This method is required to implement the OnFragmentInteractionListener interface from the
     * DetailsFragment class. It provides instructions for how to handle the DetailsFragment being
     * removed (when the user presses the Up or Back button)
     */
    @Override
    public void detachDetailScreen() {
        Log.v("MainActivity", "Detail Screen detached");

        // Toolbar: Remove the Up button, Remove the title, Make logo visible
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Set logo drawable resource
        mainToolbar.setLogo(R.drawable.app_title);

        // Change toolbar background back to transparent
        mainToolbar.setBackgroundColor(Color.TRANSPARENT);

        //Hide the details fragment and reveal the tab layout
        detailsFrameLayoutView.setVisibility(View.GONE);
        tabsLinearLayoutView.setVisibility(View.VISIBLE);
        sloganTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method overrides the behavior of the up button. Pressing the up button from the detail
     * screen will result in the same behavior as if the back button were pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Make toolbar height bigger
        mainToolbar.requestLayout();
        mainToolbar.getLayoutParams().height = 147;
        mainToolbar.requestLayout();

        // This code will make Up button perform similarly to the "Back Button"
        // Guidance and tip found on stackoverflow.com
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    /**
     * This method is required to implement the OnFragmentInteractionListener interface from the
     * DetailsFragment class
     *
     * @param category   the category of the Attraction object that was clicked on
     * @param position   the index of the Attraction item
     * @param isFavorite is the item a favorite now?
     */
    @Override
    public void onClickDetailsLikeButton(int category, int position, boolean isFavorite) {

        // Update boolean to indicate that like button was pressed during DetailsFragment
        wasLikeButtonPressedInDetailsFragment = true;

        //Update member variable currentAdapterPosition so that it can be used in notifyItemChanged
        currentAdapterPosition = position;

        // Create new attraction item
        Attraction attractionItem = new Attraction();

        // If the "parent fragment" of the DetailsFragment is the HomeFragment, then the
        // category has to be changed to 0 for the getDetailAttraction method to work properly
        if (detailsFragmentParent == HOME) {
            attractionItem = attractionItem.getDetailAttraction(this, HOME, position);

            Log.v("MainActivity", "The parent fragment was HOME");

        } else {
            attractionItem = attractionItem.getDetailAttraction(this, category, position);

            Log.v("MainActivity", "The parent fragment was " + detailsFragmentParent);
        }

        String name = attractionItem.getAttractionName();

        Log.v("MainActivity", "clicked item was " + name);

        // Prepare shared preferences editor for use in conditional statement
        sharedPrefEditor = sharedPreferences.edit();

        if (isFavorite) {
            // Show toast message
            Toast.makeText(this, name + " was added to Favorites!",
                    Toast
                            .LENGTH_SHORT).show();

            // Add attraction name to SharedPreferences
            sharedPrefEditor.putBoolean(name, true);
            sharedPrefEditor.apply();

        } else {
            // Show toast message
            Toast.makeText(this, name + " was removed from " +
                    "Favorites!", Toast.LENGTH_SHORT).show();

            Log.v("MainActivity", "Removing Key name " + name + " from favorites.");

            // Remove attraction name from shared preferences
            sharedPrefEditor.remove(name).commit();
        }
    }

    /**
     * This method is required to implement the OnFragmentInteractionListener interface from the
     * DetailsFragment class
     *
     * @param category the category of the Attraction object that was clicked on
     * @param position the index of the Attraction item
     * @param phone    the phone number of Attraction object that was clicked on
     */
    @Override
    public void onClickDetailsCallButton(int category, int position, String phone) {

        // Create new attraction item
        Attraction attractionItem = new Attraction();

        // If the "parent fragment" of the DetailsFragment is the HomeFragment, then the
        // category has to be changed to 0 for the getDetailAttraction method to work properly
        if (detailsFragmentParent == HOME) {
            attractionItem = attractionItem.getDetailAttraction(this, HOME, position);

            Log.v("MainActivity", "The parent fragment was HOME");

        } else {
            attractionItem = attractionItem.getDetailAttraction(this, category, position);

            Log.v("MainActivity", "The parent fragment was " + detailsFragmentParent);
        }

        String number = attractionItem.getAttractionPhoneNumberLink();

        //Send Intent to call number
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(number));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is required to implement the OnFragmentInteractionListener interface from the
     * DetailsFragment class
     *
     * @param category the category of the Attraction object that was clicked on
     * @param position the index of the Attraction item
     * @param website  the Attraction object that was clicked on
     */
    @Override
    public void onClickDetailsWebButton(int category, int position, String website) {

        // Create new attraction item
        Attraction attractionItem = new Attraction();

        // If the "parent fragment" of the DetailsFragment is the HomeFragment, then the
        // category has to be changed to 0 for the getDetailAttraction method to work properly
        if (detailsFragmentParent == HOME) {
            attractionItem = attractionItem.getDetailAttraction(this, HOME, position);

            Log.v("MainActivity", "The parent fragment was HOME");

        } else {
            attractionItem = attractionItem.getDetailAttraction(this, category, position);

            Log.v("MainActivity", "The parent fragment was " + detailsFragmentParent);
        }

        String url = getString(R.string.url_prefix);
        url = url + attractionItem.getAttractionWebsite();

        //Send Intent to open website
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is required to implement the OnFragmentInteractionListener interface from the
     * DetailsFragment class
     *
     * @param category the category of the Attraction object that was clicked on
     * @param position the index of the Attraction item
     * @param coords   the Attraction object that was clicked on
     */
    @Override
    public void onClickDetailsMapButton(int category, int position, String coords) {

        // Create new attraction item
        Attraction attractionItem = new Attraction();

        // If the "parent fragment" of the DetailsFragment is the HomeFragment, then the
        // category has to be changed to 0 for the getDetailAttraction method to work properly
        if (detailsFragmentParent == HOME) {
            attractionItem = attractionItem.getDetailAttraction(this, HOME, position);

            Log.v("MainActivity", "The parent fragment was HOME");

        } else {
            attractionItem = attractionItem.getDetailAttraction(this, category, position);

            Log.v("MainActivity", "The parent fragment was " + detailsFragmentParent);
        }

        String location = attractionItem.getAttractionCoordinates();

        //Send Intent to get directions
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(location));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is required to implement the OnFragmentInteractionListener interface from the
     * DetailsFragment class
     *
     * @param category       the category of the Attraction object that was clicked on
     * @param position       the index of the Attraction item
     * @param attractionInfo the info related to the Attraction object that was clicked on
     */
    @Override
    public void onClickDetailsShareButton(int category, int position, String attractionInfo) {

        // Create new attraction item
        Attraction attractionItem = new Attraction();

        // If the "parent fragment" of the DetailsFragment is the HomeFragment, then the
        // category has to be changed to 0 for the getDetailAttraction method to work properly
        if (detailsFragmentParent == HOME) {
            attractionItem = attractionItem.getDetailAttraction(this, HOME, position);

            Log.v("MainActivity", "The parent fragment was HOME");

        } else {
            attractionItem = attractionItem.getDetailAttraction(this, category, position);

            Log.v("MainActivity", "The parent fragment was " + detailsFragmentParent);
        }

        String informationToShare = attractionItem.toString();

        // Create intent
        // Code provided by android developer website:
        // https://developer.android.com/training/sharing/send
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, informationToShare);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_info)));
    }

    /**
     * This method is updates views based on category
     *
     * @param category the category of the current Fragment
     */
    public void categoryViewSetup(int category) {

        if (category == DETAIL_SCREEN) {

            //Make toolbar height larger
            //Make toolbar height bigger to accommodate more devices
            mainToolbar.requestLayout();
            mainToolbar.getLayoutParams().height = 180;
            mainToolbar.requestLayout();

            // For Attraction Details Fragment, update Toolbar theme
            // Adjust main toolbar theme, Add Title to Toolbar, Add back button to toolbar
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Change Up button color to white
            mainToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.primaryTextColor),
                    PorterDuff.Mode.SRC_ATOP);

            mainToolbar.setBackgroundResource(R.drawable.scrim);

            mainToolbar.setTitleTextColor(Color.WHITE);

            // Set Title
            mainToolbar.setTitle(currentAttractionItem.getAttractionName());

            //Hide the tabs layout and reveal the attraction details layout
            sloganTextView.setVisibility(View.GONE);
            tabsLinearLayoutView.setVisibility(View.GONE);
            detailsFrameLayoutView.setVisibility(View.VISIBLE);

        } else {
            //Update ToolBar settings

            if (category == HOME) {
                // Update to HOME themed views
                Log.v("MainActivity", "Update to HOME themed views");

            } else if (category == DINING) {
                // Update to DINING themed views
                Log.v("MainActivity", "Update to DINING themed views");

            } else if (category == SHOPPING) {
                // Update to SHOPPING themed views
                Log.v("MainActivity", "Update to SHOPPING themed views");

            } else if (category == VENUES) {
                // Update to VENUES themed views
                Log.v("MainActivity", "Update to VENUES themed views");

            } else {
                //Update to NIGHTLIFE themed views
                Log.v("MainActivity", "Update to NIGHTLIFE themed views");
            }
        }
    }

    /**
     * In the CategoryPagerAdapter constructor method, an addOnBackStackChangedListener listener was attached to the
     * FragmentManager that's passed in as a parameter. This method is called in the method
     * onBackStackChanged when the user presses the Up button. Guidance found at article:
     * Learn how to use the OnBackStackChangedListener to get the current Fragment
     * https://why-android.com/2016/03/29/learn-how-to-use-the-onbackstackchangedlistener
     */
    @Override
    public void fragmentChanged() {

        // If like button was pressed during the details fragment, notify the recyclerview's adapter
        // that the item has changed
        if (wasLikeButtonPressedInDetailsFragment) {

            // Call the related fragment's recyclerview and notify item changed
            attractionRecyclerViewAdapter.updateAttractionList(currentAdapterPosition);

            // Reset wasLikeButtonPressedInDetailsfragment to false
            wasLikeButtonPressedInDetailsFragment = false;
        }
    }

    /**
     * Reset favorites. Goal is to get this feature added to toolbar in later updates to this
     * project
     */
    public void resetFavorites() {

        sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.clear();
        sharedPrefEditor.apply();
    }
}