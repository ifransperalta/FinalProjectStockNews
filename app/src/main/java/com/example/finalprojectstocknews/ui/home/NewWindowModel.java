package com.example.finalprojectstocknews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewWindowModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewWindowModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Watchlist");
    }

    public LiveData<String> getText() {
        return mText;
    }
}