package com.example.campusEats.ui.suggest.record_ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.campusEats.MainActivity;
import com.example.campusEats.R;
import com.example.campusEats.ui.Convert;

import org.json.JSONArray;

import java.util.ArrayList;

public class RecordFragment extends Fragment {
    private RecordViewModel recordViewModel;
    Bundle bundle;
    LinearLayout total_record;
    Convert convert;
    Typeface typeface;

    static ArrayList<String> store_array;
    static ArrayList<String> product_array;
    static ArrayList<String> cost_array;
    String store_name;
    Integer total_cost;
    Integer order_count;
    static Integer store_num =0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recordViewModel =
                ViewModelProviders.of(this).get(RecordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        total_record = root.findViewById(R.id.total_record);

        convert = new Convert();

        Bundle bundle = getArguments();



        store_array = new ArrayList<String>();


        if (bundle != null){
            store_name = bundle.getString("putOrderStore");
            total_cost = bundle.getInt("putOrderCost");
            order_count = bundle.getInt("order_count");

            product_array = new ArrayList<String>();
            product_array = bundle.getStringArrayList("product_array");

            store_array.add(store_name);


            store_num += 1;

            for(int i=0; i < store_num ; i++){
                addViewRecord(store_array.get(i), product_array.get(0) + ".....", total_cost);
            }

            Toast.makeText(getContext(), String.valueOf(product_array.size()), Toast.LENGTH_LONG).show();
        }



        Toast.makeText(getContext(), String.valueOf(store_num), Toast.LENGTH_LONG).show();




        Button btn_back = root.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }


    public void addViewRecord(String name, String product, Integer total_cost){

        /* 一個包各一個產品的Linear(被 total_food包著) */
        LinearLayout record = new LinearLayout(getContext());


//        因為在 java這邊使用的是 px；而我們在res那邊用的是dp，所以必須做轉換
//        Integer convert_px = (int)convert.convertDpToPixel(100, getContext());
        LinearLayout.LayoutParams food_params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)convert.convertDpToPixel(100, getContext()));

        food_params.setMargins(0, 0, 0, 5);
        record.setLayoutParams(food_params);
        record.setBackgroundColor(Color.parseColor("#ffffff"));
        // 設為水平
        record.setOrientation(LinearLayout.HORIZONTAL);

        //        全部設為15 dp
        Integer convert_PX_padding_fifteen = (int)convert.convertDpToPixel(15, getContext());
        record.setPadding(convert_PX_padding_fifteen, convert_PX_padding_fifteen, convert_PX_padding_fifteen, convert_PX_padding_fifteen);

        total_record.addView(record);




        /* 一個用來包商店名稱的Layout  */
        LinearLayout store_name_Layout = new LinearLayout(getContext());
        store_name_Layout.setLayoutParams(new LinearLayout.LayoutParams(
                (int)convert.convertDpToPixel(240, getContext()),
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        Integer convert_PX_padding_ten = (int)convert.convertDpToPixel(10, getContext());
        store_name_Layout.setPadding(convert_PX_padding_ten, convert_PX_padding_ten, convert_PX_padding_ten, convert_PX_padding_ten);
        store_name_Layout.setOrientation(LinearLayout.VERTICAL);
        record.addView(store_name_Layout);



        /* 商店名稱 */
        TextView store_name =new TextView(getContext());

        /* 函式所填入的商店名稱 */
        store_name.setText(name);

        store_name.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        store_name.setTextSize(14);

        // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        store_name.setTypeface(typeface);

        store_name_Layout.addView(store_name);


        /* 一個用來包產品、價格、數量的Layout  */
        LinearLayout product_Layout = new LinearLayout(getContext());
        product_Layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
                ));
        store_name_Layout.addView(product_Layout);



        /* 產品名稱、價格、數量 */
        TextView product_info =new TextView(getContext());
        LinearLayout.LayoutParams pInfo_params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        pInfo_params.setMargins(0,0, (int)convert.convertDpToPixel(10, getContext()),0);
        product_info.setLayoutParams(pInfo_params);
             // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        product_info.setTypeface(typeface);

        /* 設定種類 */
        product_info.setText(product);
        product_info.setTextColor(Color.parseColor("#999999"));
        product_info.setTextSize(12);
        store_name_Layout.addView(product_info);



        /* 一個用來包消費總金額的Layout  */
        LinearLayout total_cost_Layout = new LinearLayout(getContext());
        total_cost_Layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        total_cost_Layout.setGravity(Gravity.CENTER);
        total_cost_Layout.setOrientation(LinearLayout.VERTICAL);

        record.addView(total_cost_Layout);



        /* 總金額文字*/
        TextView total_cost_Font =new TextView(getContext());
        LinearLayout.LayoutParams cost_Font = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        total_cost_Font.setLayoutParams(cost_Font);

        // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        total_cost_Font.setTypeface(typeface);

        /* 設定種類 */
        total_cost_Font.setText("總金額");
        total_cost_Font.setTextSize(14);
        total_cost_Layout.addView(total_cost_Font);



        /* 總金額*/
        TextView cost =new TextView(getContext());
        LinearLayout.LayoutParams cost_Params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        cost.setLayoutParams(cost_Params);

        // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        cost.setTypeface(typeface);

        /* 設定種類 */
        cost.setText(String.valueOf(total_cost));
        cost.setTextSize(12);
        total_cost_Layout.addView(cost);


    }




}