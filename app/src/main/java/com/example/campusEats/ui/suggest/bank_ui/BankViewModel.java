package com.example.campusEats.ui.suggest.bank_ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BankViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is love fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}