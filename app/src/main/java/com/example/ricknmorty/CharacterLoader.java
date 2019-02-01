package com.example.ricknmorty;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class CharacterLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    CharacterLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getCharacter(mQueryString);
    }
}
