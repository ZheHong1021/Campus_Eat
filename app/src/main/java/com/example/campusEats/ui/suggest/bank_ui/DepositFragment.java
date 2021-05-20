package com.example.campusEats.ui.suggest.bank_ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.campusEats.R;
import com.example.campusEats.ui.home.HomeFragment;

// 存款(Deposit)

public class DepositFragment extends Fragment {



    //    拿來用Toast
    public void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_deposit, container, false);

        final Button btn_deposit = root.findViewById(R.id.go_deposit);
        final EditText edit_deposit = root.findViewById(R.id.edit_deposit);



        btn_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass_Deposit =  edit_deposit.getText().toString();

                new DepositFragment().showToast(getContext(), "存款成功......");

                //獲取fragment的實例
                Fragment fragment=new BankFragment();


                //獲取Fragment的管理器
                FragmentManager fragmentManager=getFragmentManager();

                //開啟fragment的事物,在這個對象裡進行fragment的增刪替換等操作。
                FragmentTransaction ft = fragmentManager.beginTransaction();

                //跳轉到fragment ，第一個參數為所要替換的位置id ，第二個參數是替換後的fragment
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.addToBackStack("fragment");

                /* 這邊為將資料回傳給 HomeFragment*/
                Bundle bundle = new Bundle();
//                設立一個 item_Deposit來容納 pass_Deposit內容
//                (之後在 item_Deposit運用 item_Deposit即可獲得 pass_Deposit內容)
                bundle.putString("item_Deposit",pass_Deposit);
                fragment.setArguments(bundle);

                //提交事物
                ft.commit();
            }
        });

        return root;
    }




}