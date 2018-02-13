package com.example.android.googlebookssearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Loads a list of books by using an AsyncTask to perform the
 * network request to the given URL.
 */

class GbooksLoader extends AsyncTaskLoader {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link GbooksLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    GbooksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public Object loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        return GbooksUtils.extractGbooks(mUrl);
    }
}
