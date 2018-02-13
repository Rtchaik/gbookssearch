package com.example.android.googlebookssearch;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//Custom adaptor for array data

class GbooksAdapter extends ArrayAdapter {

    GbooksAdapter(Activity context, ArrayList<GoogleBooks> gbooks) {
        super(context, 0, gbooks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        GoogleBooks currentGbooks = (GoogleBooks) getItem(position);

        TextView authorView = listItemView.findViewById(R.id.author);
        authorView.setText(currentGbooks.getmAuthor());

        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentGbooks.getmTitle());

        return listItemView;
    }
}
