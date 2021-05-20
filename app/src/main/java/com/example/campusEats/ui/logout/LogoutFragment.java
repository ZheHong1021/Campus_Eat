package com.example.campusEats.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.example.campusEats.R;

public class LogoutFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        showDialog();
        return root;
    }


    void showDialog() {
//        FragmentManager fm = getSupportFragmentManager();
        DialogFragment newFragment = LogoutDialog.newInstance("您確定要登出嗎??");
        newFragment.show(getFragmentManager(), "activity_dashboard");
    }






}