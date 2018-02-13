package com.example.android.googlebookssearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GoogleBooks>> {

    private TextView mEmptyListView;
    private View mProgressBar;
    private GbooksAdapter mAdapter;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView gbooksListView = findViewById(R.id.list);
        mProgressBar = findViewById(R.id.progress_bar);
        mEmptyListView = findViewById(R.id.empty_view);
        gbooksListView.setEmptyView(mEmptyListView);

        // Create a new {@link ArrayAdapter} of gbooks
        mAdapter = new GbooksAdapter(this, new ArrayList<GoogleBooks>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        gbooksListView.setAdapter(mAdapter);

        gbooksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                GoogleBooks currentGbooks = (GoogleBooks) mAdapter.getItem(position);
                Intent showUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(currentGbooks.getmUrl()));
                if (showUrl.resolveActivity(getPackageManager()) != null) {
                    startActivity(showUrl);
                }
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    public void onSearchClick(View view) {
        //Get the search string

        EditText searchTextView = findViewById(R.id.search_text);
        String searchText = searchTextView.getText().toString();
        searchText = searchText.replaceAll(" ", "+");
        mUrl = "https://www.googleapis.com/books/v1/volumes?q=" + searchText + "&maxResults=40";

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            getLoaderManager().restartLoader(0, null, this);
        } else {
            mAdapter.clear();
            mProgressBar.setVisibility(View.GONE);
            mEmptyListView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<GoogleBooks>> onCreateLoader(int id, Bundle args) {
        return new GbooksLoader(this, mUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<GoogleBooks>> loader, List<GoogleBooks> data) {
        // Clear the adapter of previous books data
        mProgressBar.setVisibility(View.GONE);
        if (mUrl == null) {
            mEmptyListView.setText(R.string.type_search);
        } else {
            mEmptyListView.setText(R.string.no_books);
        }
        mAdapter.clear();

        // If there is a valid list of {@link GoogleBooks}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<GoogleBooks>> loader) {
        mAdapter.clear();
    }
}
