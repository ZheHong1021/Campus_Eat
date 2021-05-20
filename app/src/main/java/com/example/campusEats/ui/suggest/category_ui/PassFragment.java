package com.example.campusEats.ui.suggest.category_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.campusEats.R;
import com.example.campusEats.ui.suggest.record_ui.RecordViewModel;

public class PassFragment extends Fragment {
    private RecordViewModel suggestViewModel;

//    SuggestActivity suggestActivity = new SuggestActivity();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        updateDetail();
        View root = inflater.inflate(R.layout.fragment_home, null);


        return root;
    }


    public void updateDetail() {
        Intent intent = new Intent(getActivity(), SuggestActivity.class);
        startActivity(intent);
    }


}



