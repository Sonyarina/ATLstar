/**
 * The general structure for this Class was obtained through the Miwok project lesson provided
 * by Udacity
 */
package com.example.android.atlantastartours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * {@link CategoryPagerAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of {@link Attraction} objects.
 */
public class CategoryPagerAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;

    private CategoryPagerAdapter categoryPagerAdapter;

    /* The listener and interface below is used to attach addOnBackStackChangedListener listener
     to the FragmentManager to be notified when the user presses the up or back button.
     Guidance found at article: Learn how to use
    the OnBackStackChangedListener to get the current Fragment
    https://why-android.com/2016/03/29/learn-how-to-use-the-onbackstackchangedlistener
     */
    private MyOnBackStackChangedListener listener;

    public interface MyOnBackStackChangedListener {
        void fragmentChanged();
    }

    /**
     * Create a new {@link CategoryPagerAdapter} object.  In the CategoryPagerAdapter constructor
     * method, an addOnBackStackChangedListener listener was attached to the
     * FragmentManager that's passed in as a parameter.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public CategoryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

        if (context instanceof MyOnBackStackChangedListener) {
            listener = (MyOnBackStackChangedListener) context;
        }

        // Attach addOnBackStackChangedListener listener to the FragmentManager to be notified
        // when the user presses the up or back button.
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.v("CategoryPagerAdapter", "OnBackStackListener changed");

                listener.fragmentChanged();
            }
        });
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new DiningFragment();
        } else if (position == 2) {
            return new ShoppingFragment();
        } else if (position == 3) {
            return new VenuesFragment();
        } else {
            return new NightlifeFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.home_fragment);
        } else if (position == 1) {
            return mContext.getString(R.string.dine_fragment);
        } else if (position == 2) {
            return mContext.getString(R.string.shop_fragment);
        } else if (position == 3) {
            return mContext.getString(R.string.venues_fragment);
        } else {
            return mContext.getString(R.string.nightlife_fragment);
        }
    }
}
