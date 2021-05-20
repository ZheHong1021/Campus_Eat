package com.example.campusEats.ui.logout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;


import com.example.campusEats.MainActivity;
import com.example.campusEats.R;


public  class LogoutDialog extends DialogFragment {

    ProgressDialog progressDialog;
    Handler handler;
    Intent intent;

    public static LogoutDialog newInstance(String title) {
        LogoutDialog frag = new LogoutDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        return new AlertDialog.Builder(getActivity())
//                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(title)
                .setNegativeButton("確定登出",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                                        intent = new Intent(getContext(), MainActivity.class);
                                        intent.putExtra("is_Login", false);
                                        startActivity(intent);
                                        Toast.makeText(getContext(),"登出成功" , Toast.LENGTH_SHORT).show();


                            }
                        }
                )
                .setPositiveButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();

                            }
                        }
                )
                .create();
    }





}





