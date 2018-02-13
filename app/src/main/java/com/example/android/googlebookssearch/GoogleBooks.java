package com.example.android.googlebookssearch;

//Google books class

class GoogleBooks {

    private String mTitle;
    private String mAuthor;
    private String mUrl;

    GoogleBooks(String mTitle, String mAuthor, String mUrl) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mUrl = mUrl;
    }

    String getmTitle() {
        return mTitle;
    }

    String getmAuthor() {
        return mAuthor;
    }

    String getmUrl() {
        return mUrl;
    }
}
