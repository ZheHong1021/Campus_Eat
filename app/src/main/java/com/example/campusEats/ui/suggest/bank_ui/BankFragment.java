package com.example.campusEats.ui.suggest.bank_ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusEats.MainActivity;
import com.example.campusEats.R;
import com.example.campusEats.ui.Convert;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class BankFragment extends Fragment {


    //    設立靜態變數
    static Integer balance= 1000;
    Integer withdraw;
    private String deposit;


    /* 單位轉換 */
    Convert convert = new Convert();

    //    設立 getBalance拿來給其他的 Fragment取得現在的存款餘額。
    public static Integer getBalance(){
        return  balance;
    }

    public static Integer setBalance(Integer change_balance){
        balance = change_balance;
        return balance;
    }


    //    拿來用Toast (不知道為什麼 Fragment不能直接用 getApplication)
    public void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_bank, container, false);

        /* 返回按鈕 */
        Button btn_back = root.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });






    /* 定義一下 UI的變數*/
        final Button btn_balance = root.findViewById(R.id.btn_balance);
        final Button btn_deposit = root.findViewById(R.id.btn_deposit);
//        final Button btn_withdraw = root.findViewById(R.id.btn_withdraw);

//        當點選存款餘額(要有 Toast)
        btn_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BankFragment().showToast(root.getContext(), "現在存款餘額：" + balance);
            }
        });


        //  當點選存款
        btn_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("您要加值多少呢？");


                // Set up the input
                final EditText input = new EditText(getContext());

                LinearLayout.LayoutParams input_params = new LinearLayout.LayoutParams(
                        (int)convert.convertDpToPixel(100, getContext()),
                        (int)convert.convertDpToPixel(100, getContext()));

//                input_params.setMargins(0, 0, 0, 5);
                input.setLayoutParams(input_params);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deposit = input.getText().toString();
                        balance += Integer.parseInt(deposit);
                        new BankFragment().showToast(root.getContext(),   "成功加值：" + deposit+ "，現在存款餘額：" + balance);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return root;
    }
}