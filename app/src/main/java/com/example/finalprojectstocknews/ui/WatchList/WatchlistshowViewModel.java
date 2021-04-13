package com.example.finalprojectstocknews.ui.WatchList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WatchlistshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WatchlistshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Watchlist");
    }

    public LiveData<String> getText() {
        return mText;
    }
}