package com.example.android.atlantastartours;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;

/**
 * {@link Attraction represents an attraction or destination that the user is interested in visiting
 */
public class Attraction {

    // Name of the attraction
    private String mAttractionName;

    // Category of the Attraction. Will be either Dine, Shop, Venue, or Nightlife
    private String mAttractionCategory;

    // Formatted Address of the Location
    private String mAttractionAddress;

    // Geolocation Coordinates of the Location for Mapping Purposes
    private String mAttractionCoordinates;

    // Phone Number of the Attraction Formatted
    private String mAttractionPhoneNumberText;

    // Phone Number URL
    private String mAttractionPhoneNumberLink;

    // Attraction Website
    private String mAttractionWebsite;

    // Attraction Description
    private String mAttractionDescription;

    // Attraction Blurb
    private String mAttractionBlurb;

    // Attraction Image Resource Id
    private int mAttractionImageId;

    // Boolean keeps track of if Attraction was "liked" by the user
    private boolean isFavorite;

    //final static ArrayList<Attraction> diningList, shoppingList;

    /**
     * Constructs a new Attraction object
     */
    public Attraction() {
    }

    /**
     * Constructs a new Attraction object using 10 inputs
     *
     * @param name        the attraction name
     * @param category    the attraction category
     * @param address     the attraction address
     * @param coordinates the attraction coordinates
     * @param phone       the attraction phone number
     * @param phoneLink   phone number in link format
     * @param website     the attraction website
     * @param description the attraction description
     * @param blurb       the attraction blurb
     * @param imageId     the attraction's Image Resource id
     */
    public Attraction(String name, String category, String address, String coordinates,
                      String phone, String phoneLink, String website, String description,
                      String blurb, int imageId) {

        // Set passed in parameters to be the new values of the Attraction fields
        mAttractionName = name;
        mAttractionCategory = category;
        mAttractionAddress = address;
        mAttractionCoordinates = coordinates;
        mAttractionPhoneNumberText = phone;
        mAttractionPhoneNumberLink = phoneLink;
        mAttractionWebsite = website;
        mAttractionDescription = description;
        mAttractionBlurb = blurb;
        mAttractionImageId = imageId;
    }

/*
The next 22 methods are the getters and setters for all 11 fields of Attraction object
 */

    public String getAttractionName() {
        return mAttractionName;
    }

    public void setAttractionName(String newAttractionName) {
        mAttractionName = newAttractionName;
    }

    public String getAttractionCategory() {
        return mAttractionCategory;
    }

    public void setAttractionCategory(String newAttractionCategory) {
        mAttractionCategory = newAttractionCategory;
    }

    public String getAttractionAddress() {
        return mAttractionAddress;
    }

    public void setAttractionAddress(String newAttractionAddress) {
        mAttractionAddress = newAttractionAddress;
    }

    public String getAttractionCoordinates() {
        return mAttractionCoordinates;
    }

    public void setAttractionCoordinates(String newAttractionCoordinates) {
        mAttractionCoordinates = newAttractionCoordinates;
    }

    public String getAttractionPhoneNumberText() {
        return mAttractionPhoneNumberText;
    }

    public void setAttractionPhoneNumberText(String newAttractionPhoneNumberText) {
        mAttractionPhoneNumberText = newAttractionPhoneNumberText;
    }

    public String getAttractionPhoneNumberLink() {
        return mAttractionPhoneNumberLink;
    }

    public void setAttractionPhoneNumberLink(String newAttractionPhoneNumberLink) {
        mAttractionPhoneNumberLink = newAttractionPhoneNumberLink;
    }

    public String getAttractionWebsite() {
        return mAttractionWebsite;
    }

    public void setAttractionWebsite(String newAttractionWebsite) {
        mAttractionWebsite = newAttractionWebsite;
    }

    public String getAttractionDescription() {
        return mAttractionDescription;
    }

    public void setAttractionDescription(String newAttractionDescription) {
        mAttractionDescription = newAttractionDescription;
    }

    public String getAttractionBlurb() {
        return mAttractionBlurb;
    }

    public void setAttractionBlurb(String newAttractionBlurb) {
        mAttractionBlurb = newAttractionBlurb;
    }

    public int getAttractionImageId() {
        return mAttractionImageId;
    }

    public void setAttractionImageId(int newAttractionImageId) {
        mAttractionImageId = newAttractionImageId;
    }

    /**
     * Returns fully populated arraylist containing all attractions from all categories
     *
     * @return fullListArray a fully populated ArrayList of Attraction objects from every category
     */
    public ArrayList<Attraction> getFullListArray(Context context) {
        final ArrayList<Attraction> fullListArray = new ArrayList<>();

        // Get String array resources and assign to String to populate ArrayList
        String names[] = context.getResources().getStringArray(R.array.attraction_name);
        String categories[] = context.getResources().getStringArray(R.array.attraction_category);
        String addresses[] = context.getResources().getStringArray(R.array.attraction_address);
        String coordinates[] = context.getResources().getStringArray(R.array.attraction_coordinates);
        String phones[] = context.getResources().getStringArray(R.array.attraction_phone);
        String phoneLinks[] = context.getResources().getStringArray(R.array.attraction_phone_link);
        String website[] = context.getResources().getStringArray(R.array.attraction_website);
        String descriptions[] = context.getResources().getStringArray(R.array.attraction_description);
        String blurbs[] = context.getResources().getStringArray(R.array.attraction_blurb);

        // Get array of image resource ids
        TypedArray attractionImages = context.getResources().obtainTypedArray(R.array.attraction_images);

        // Populate ArrayList from the string-arrays
        for (int i = 0; i < names.length; i++) {
            fullListArray.add(new Attraction(names[i], categories[i], addresses[i], coordinates[i],
                    phones[i], phoneLinks[i], website[i], descriptions[i], blurbs[i],
                    attractionImages.getResourceId(i, -1)));
        }

        // Next, recycle TypedArray
        attractionImages.recycle();

        //Return the fully populated array
        return fullListArray;
    }

    /**
     * Returns fully populated arraylist from dining category
     *
     * @return diningArray a fully populated ArrayList of Attraction objects from dining category
     */
    public ArrayList<Attraction> getDiningArray(Context context) {
        final ArrayList<Attraction> diningArray = new ArrayList<>();

        // Get String array resources and assign to String to populate ArrayList
        String names[] = context.getResources().getStringArray(R.array.attraction_name);
        String categories[] = context.getResources().getStringArray(R.array.attraction_category);
        String addresses[] = context.getResources().getStringArray(R.array.attraction_address);
        String coordinates[] = context.getResources().getStringArray(R.array.attraction_coordinates);
        String phones[] = context.getResources().getStringArray(R.array.attraction_phone);
        String phoneLinks[] = context.getResources().getStringArray(R.array.attraction_phone_link);
        String website[] = context.getResources().getStringArray(R.array.attraction_website);
        String descriptions[] = context.getResources().getStringArray(R.array.attraction_description);
        String blurbs[] = context.getResources().getStringArray(R.array.attraction_blurb);

        // Get array of image resource ids
        TypedArray attractionImages = context.getResources().obtainTypedArray(R.array.attraction_images);

        // Populate ArrayList from the string-arrays
        for (int i = 0; i < 4; i++) {
            diningArray.add(new Attraction(names[i], categories[i], addresses[i], coordinates[i],
                    phones[i], phoneLinks[i], website[i], descriptions[i], blurbs[i],
                    attractionImages.getResourceId(i, -1)));
        }

        // Next, recycle TypedArray
        attractionImages.recycle();

        //Return the fully populated array
        return diningArray;
    }

    /**
     * Returns fully populated arraylist from shopping category
     *
     * @return shoppingArray a fully populated ArrayList of Attraction objects from shopping
     * category
     */
    public ArrayList<Attraction> getShoppingArray(Context context) {
        final ArrayList<Attraction> shoppingArray = new ArrayList<>();

        // Get String array resources and assign to String to populate ArrayList
        String names[] = context.getResources().getStringArray(R.array.attraction_name);
        String categories[] = context.getResources().getStringArray(R.array.attraction_category);
        String addresses[] = context.getResources().getStringArray(R.array.attraction_address);
        String coordinates[] = context.getResources().getStringArray(R.array.attraction_coordinates);
        String phones[] = context.getResources().getStringArray(R.array.attraction_phone);
        String phoneLinks[] = context.getResources().getStringArray(R.array.attraction_phone_link);
        String website[] = context.getResources().getStringArray(R.array.attraction_website);
        String descriptions[] = context.getResources().getStringArray(R.array.attraction_description);
        String blurbs[] = context.getResources().getStringArray(R.array.attraction_blurb);

        // Get array of image resource ids
        TypedArray attractionImages = context.getResources().obtainTypedArray(R.array.attraction_images);

        // Populate ArrayList from the string-arrays
        for (int i = 9; i < 12; i++) {
            shoppingArray.add(new Attraction(names[i], categories[i], addresses[i], coordinates[i],
                    phones[i], phoneLinks[i], website[i], descriptions[i], blurbs[i],
                    attractionImages.getResourceId(i, -1)));
        }

        // Next, recycle TypedArray
        attractionImages.recycle();

        //Return the fully populated array
        return shoppingArray;
    }

    /**
     * Returns fully populated arraylist from venues category
     *
     * @return venuesArray a fully populated ArrayList of Attraction objects from venues
     * category
     */
    public ArrayList<Attraction> getVenuesArray(Context context) {
        final ArrayList<Attraction> venuesArray = new ArrayList<>();

        // Get String array resources and assign to String to populate ArrayList
        String names[] = context.getResources().getStringArray(R.array.attraction_name);
        String categories[] = context.getResources().getStringArray(R.array.attraction_category);
        String addresses[] = context.getResources().getStringArray(R.array.attraction_address);
        String coordinates[] = context.getResources().getStringArray(R.array.attraction_coordinates);
        String phones[] = context.getResources().getStringArray(R.array.attraction_phone);
        String phoneLinks[] = context.getResources().getStringArray(R.array.attraction_phone_link);
        String website[] = context.getResources().getStringArray(R.array.attraction_website);
        String descriptions[] = context.getResources().getStringArray(R.array.attraction_description);
        String blurbs[] = context.getResources().getStringArray(R.array.attraction_blurb);

        // Get array of image resource ids
        TypedArray attractionImages = context.getResources().obtainTypedArray(R.array.attraction_images);

        // Populate ArrayList from the string-arrays
        for (int i = 12; i < 16; i++) {
            venuesArray.add(new Attraction(names[i], categories[i], addresses[i], coordinates[i],
                    phones[i], phoneLinks[i], website[i], descriptions[i], blurbs[i],
                    attractionImages.getResourceId(i, -1)));
        }

        // Next, recycle TypedArray
        attractionImages.recycle();

        //Return the fully populated array
        return venuesArray;
    }

    /**
     * Returns fully populated arraylist from nightlife category
     *
     * @return nightlifeArray a fully populated ArrayList of Attraction objects from nightlife
     * category
     */
    public ArrayList<Attraction> getNightlifeArray(Context context) {
        final ArrayList<Attraction> nightlifeArray = new ArrayList<>();

        // Get String array resources and assign to String to populate ArrayList
        String names[] = context.getResources().getStringArray(R.array.attraction_name);
        String categories[] = context.getResources().getStringArray(R.array.attraction_category);
        String addresses[] = context.getResources().getStringArray(R.array.attraction_address);
        String coordinates[] = context.getResources().getStringArray(R.array.attraction_coordinates);
        String phones[] = context.getResources().getStringArray(R.array.attraction_phone);
        String phoneLinks[] = context.getResources().getStringArray(R.array.attraction_phone_link);
        String website[] = context.getResources().getStringArray(R.array.attraction_website);
        String descriptions[] = context.getResources().getStringArray(R.array.attraction_description);
        String blurbs[] = context.getResources().getStringArray(R.array.attraction_blurb);

        // Get array of image resource ids
        TypedArray attractionImages = context.getResources().obtainTypedArray(R.array.attraction_images);

        // Populate ArrayList from the string-arrays
        for (int i = 4; i < 9; i++) {
            nightlifeArray.add(new Attraction(names[i], categories[i], addresses[i], coordinates[i],
                    phones[i], phoneLinks[i], website[i], descriptions[i], blurbs[i],
                    attractionImages.getResourceId(i, -1)));
        }

        // Next, recycle TypedArray
        attractionImages.recycle();

        //Return the fully populated array
        return nightlifeArray;
    }

    /**
     * Returns a specific Attraction item from homePage category
     *
     * @return homePageAttraction an Attraction object from homePage category
     */
    public Attraction getHomePageAttraction(Context context, int i) {

        // Get String array resources and assign to String to populate ArrayList
        String names[] = context.getResources().getStringArray(R.array.attraction_name);
        String categories[] = context.getResources().getStringArray(R.array.attraction_category);
        String addresses[] = context.getResources().getStringArray(R.array.attraction_address);
        String coordinates[] = context.getResources().getStringArray(R.array.attraction_coordinates);
        String phones[] = context.getResources().getStringArray(R.array.attraction_phone);
        String phoneLinks[] = context.getResources().getStringArray(R.array.attraction_phone_link);
        String website[] = context.getResources().getStringArray(R.array.attraction_website);
        String descriptions[] = context.getResources().getStringArray(R.array.attraction_description);
        String blurbs[] = context.getResources().getStringArray(R.array.attraction_blurb);

        // Get array of image resource ids
        TypedArray attractionImages = context.getResources().obtainTypedArray(R.array.attraction_images);

        final Attraction homePageAttraction = new Attraction(names[i], categories[i], addresses[i], coordinates[i],
                phones[i], phoneLinks[i], website[i], descriptions[i], blurbs[i],
                attractionImages.getResourceId(i, -1));

        // Next, recycle TypedArray
        attractionImages.recycle();

        //Return the Attraction item
        return homePageAttraction;
    }

    /**
     * Returns a specific Attraction item from dining category
     *
     * @return diningAttraction an Attraction object from dining category
     */
    public Attraction getDiningAttraction(Context context, int i) {

        // Generate a new dining array but only retrieve the item at the specified position
        // Return the dining attraction item

        return new Attraction().getDiningArray(context).get(i);
    }

    /**
     * Returns a specific Attraction item from shopping category
     *
     * @return shoppingAttraction an Attraction object from shopping category
     */
    public Attraction getShoppingAttraction(Context context, int i) {

        // Generate a new shopping array but only retrieve the item at the specified position
        // Return the shopping attraction item

        return new Attraction().getShoppingArray(context).get(i);
    }

    /**
     * Returns a specific Attraction item from venues category
     *
     * @return venuesAttraction an Attraction object from venues category
     */
    public Attraction getVenuesAttraction(Context context, int i) {

        // Generate a new Venues array but only retrieve the item at the specified position
        // Return the Venues attraction item

        return new Attraction().getVenuesArray(context).get(i);
    }

    /**
     * Returns a specific Attraction item from nightlife category
     *
     * @return nightlifeAttraction an Attraction object from nightlife category
     */
    public Attraction getNightlifeAttraction(Context context, int i) {

        // Generate a new Nightlife array but only retrieve the item at the specified position
        // Return the Nightlife attraction item

        return new Attraction().getNightlifeArray(context).get(i);
    }

    /**
     * Returns a specific Attraction item from nightlife category
     * Category codes: 0 - Home, 1 - Dining, 2 - Shopping, 3  Venues, 4 - Nightlife
     *
     * @return nightlifeAttraction an Attraction object from nightlife category
     */
    public Attraction getDetailAttraction(Context context, int category, int index) {
        if (category == 0) {
            // Home Page category. Get item at the specified position
            return getHomePageAttraction(context, index);
        } else if (category == 1) {
            // Dining category. Get Dining item at the specified position
            return getDiningAttraction(context, index);
        } else if (category == 2) {
            // Shopping category. Get Shopping item at the specified position
            return getShoppingAttraction(context, index);
        } else if (category == 3) {
            // Venues category. Get Venues item at the specified position
            return getVenuesAttraction(context, index);
        } else {
            // Nightlife category. Get Nightlife item at the specified position
            return getNightlifeAttraction(context, index);
        }
    }

    /**
     * Returns a string representation of an Attraction object.
     * The {@code toString} method for class {@code Attraction}
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {

        return "Attraction name is " + mAttractionName +
                "\nAttraction Category is " + mAttractionCategory +
                "\nAttraction Address is " + mAttractionAddress +
                "\nAttraction Number is " + mAttractionPhoneNumberText +
                "\nAttraction Website is " + mAttractionWebsite +
                "\nAttraction Blurb is " + mAttractionBlurb +
                "\nAttraction Description is " + mAttractionDescription;
    }
}
