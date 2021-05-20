package com.example.campusEats.ui.suggest.category_ui;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.campusEats.MainActivity;
import com.example.campusEats.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements View.OnClickListener {


    // Initialize Variable
    Bundle bundle;
    ArrayList<String> product_name;
    ArrayList<String> product_price;
    ProgressDialog progressDialog;
    Handler handler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category, container, false);


        Button btn_back = root.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });




//        取得見面元件
        LinearLayout store01 = root.findViewById(R.id.store_01);
        LinearLayout store02 = root.findViewById(R.id.store_02);
        LinearLayout store03 = root.findViewById(R.id.store_03);
        LinearLayout store04 = root.findViewById(R.id.store_04);
        LinearLayout store05 = root.findViewById(R.id.store_05);
        LinearLayout store06 = root.findViewById(R.id.store_06);
        LinearLayout store07 = root.findViewById(R.id.store_07);
        LinearLayout store08 = root.findViewById(R.id.store_08);

        //設定共用事件
        store01.setOnClickListener(this);
        store02.setOnClickListener(this);
        store03.setOnClickListener(this);
        store04.setOnClickListener(this);
        store05.setOnClickListener(this);
        store06.setOnClickListener(this);
        store07.setOnClickListener(this);
        store08.setOnClickListener(this);

        /* 產品名稱以及價格 */
        product_name = new ArrayList<String>();
        product_price = new ArrayList<String>();


        return root;
    }



    @Override
    public void onClick(final View view) {

        // Initialize Progress Dialog
        progressDialog = new ProgressDialog(getContext());

        // Show Dialog
        progressDialog.show();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000); // 3000 milliseconds delay


        // Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);

        // Set Transparent Background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );





        //判斷id
        switch (view.getId()) {
            //執行方法
            case R.id.store_01:
//                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "芙樂廚坊早午餐");
                bundle.putString("store_category", "早午餐");
                bundle.putInt("store_img", R.drawable.store_01);

                /* 輸入產品名稱 */
                product_name.add(0, "鍋燒意麵");
                product_name.add(1, "原味抓餅");
                product_name.add(2, "巧克力吐司");
                product_name.add(3, "麥香雞漢堡");
                product_name.add(4, "紅茶");
                product_name.add(5, "奶茶");
                product_name.add(6, "豆漿");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "55");
                product_price.add(1, "30");
                product_price.add(2, "15");
                product_price.add(3, "35");
                product_price.add(4, "10");
                product_price.add(5, "15");
                product_price.add(6, "15");
                bundle.putSerializable("product_price", product_price);
//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;

            case R.id.store_02:
//                Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "大內製麵-土庫店");
                bundle.putString("store_category", "麵食");
                bundle.putInt("store_img", R.drawable.store_02);

                /* 輸入產品名稱 */
                product_name.add(0, "麻將豆菜麵");
                product_name.add(1, "肉燥豆菜麵");
                product_name.add(2, "豬肉麻醬乾拌麵");
                product_name.add(3, "榨菜麻將乾拌麵");
                product_name.add(4, "貢丸湯");
                product_name.add(5, "蛋花湯");
                product_name.add(6, "冷盤小菜");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "35");
                product_price.add(1, "35");
                product_price.add(2, "45");
                product_price.add(3, "45");
                product_price.add(4, "30");
                product_price.add(5, "25");
                product_price.add(6, "30");
                bundle.putSerializable("product_price", product_price);


//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;
            case R.id.store_03:
//                Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "元之氣早午餐 - 清豐二路");
                bundle.putString("store_category", "早午餐");
                bundle.putInt("store_img", R.drawable.store_04);

                /* 輸入產品名稱 */
                product_name.add(0, "照燒豬肉漢堡");
                product_name.add(1, "燻雞漢堡");
                product_name.add(2, "里肌總匯");
                product_name.add(3, "花生厚片");
                product_name.add(4, "脆薯");
                product_name.add(5, "巧克力可可");
                product_name.add(6, "錫蘭紅茶");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "30");
                product_price.add(1, "40");
                product_price.add(2, "70");
                product_price.add(3, "25");
                product_price.add(4, "35");
                product_price.add(5, "15");
                product_price.add(6, "10");
                bundle.putSerializable("product_price", product_price);


//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;
            case R.id.store_04:
//                Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "第一讚炒飯專賣店");
                bundle.putString("store_category", "炒飯");
                bundle.putInt("store_img", R.drawable.store_03);

                /* 輸入產品名稱 */
                product_name.add(0, "肉絲黃金炒飯");
                product_name.add(1, "番茄黃金炒飯");
                product_name.add(2, "蝦仁黃金炒飯");
                product_name.add(3, "泡菜山豬肉炒飯");
                product_name.add(4, "宮保牛肉炒飯");
                product_name.add(5, "蛤仔湯");
                product_name.add(6, "紫菜蛋花湯");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "55");
                product_price.add(1, "55");
                product_price.add(2, "65");
                product_price.add(3, "80");
                product_price.add(4, "85");
                product_price.add(5, "30");
                product_price.add(6, "10");
                bundle.putSerializable("product_price", product_price);

//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;
            case R.id.store_05:
//                Toast.makeText(getContext(), "5", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "大上海火鍋 - 楠梓店");
                bundle.putString("store_category", "火鍋");
                bundle.putInt("store_img", R.drawable.store_05);

                /* 輸入產品名稱 */
                product_name.add(0, "海鮮鍋");
                product_name.add(1, "養生鍋");
                product_name.add(2, "泡菜鍋");
                product_name.add(3, "鴨血鍋");
                product_name.add(4, "龍骨套餐");
                product_name.add(5, "麻辣套餐");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "130");
                product_price.add(1, "110");
                product_price.add(2, "120");
                product_price.add(3, "120");
                product_price.add(4, "120");
                product_price.add(5, "130");
                bundle.putSerializable("product_price", product_price);


//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;
            case R.id.store_06:
//                Toast.makeText(getContext(), "6", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "炸魂雞排 - 楠梓店");
                bundle.putString("store_category", "炸物");
                bundle.putInt("store_img", R.drawable.store_06);

                /* 輸入產品名稱 */
                product_name.add(0, "炸魂雞腿排");
                product_name.add(1, "炸魂雞排");
                product_name.add(2, "炸魂雞翅");
                product_name.add(3, "美式脆薯");
                product_name.add(4, "麥可小雞塊");
                product_name.add(5, "台灣甜不辣");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "75");
                product_price.add(1, "65");
                product_price.add(2, "20");
                product_price.add(3, "40");
                product_price.add(4, "40");
                product_price.add(5, "30");
                bundle.putSerializable("product_price", product_price);

//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;
            case R.id.store_07:
//                Toast.makeText(getContext(), "7", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "北平楊寶寶蒸餃");
                bundle.putString("store_category", "餃類");
                bundle.putInt("store_img", R.drawable.store_07);

                /* 輸入產品名稱 */
                product_name.add(0, "豬肉蒸餃");
                product_name.add(1, "牛肉蒸餃");
                product_name.add(2, "鍋貼");
                product_name.add(3, "蔥油餅");
                product_name.add(4, "烙餅");
                product_name.add(5, "玉米濃湯");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "60");
                product_price.add(1, "70");
                product_price.add(2, "60");
                product_price.add(3, "30");
                product_price.add(4, "40");
                product_price.add(5, "30");
                bundle.putSerializable("product_price", product_price);

//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;
            case R.id.store_08:
//                Toast.makeText(getContext(), "8", Toast.LENGTH_SHORT).show();
                bundle = new Bundle();
                bundle.putString("store_name", "高雄滷味王 - 楠梓店");
                bundle.putString("store_category", "滷味");
                bundle.putInt("store_img", R.drawable.store_08);

                /* 輸入產品名稱 */
                product_name.add(0, "鴨頭");
                product_name.add(1, "鴨翅");
                product_name.add(2, "雞脖子");
                product_name.add(3, "雞皮");
                product_name.add(4, "豬皮");
                product_name.add(5, "豬耳朵");
                product_name.add(6, "豆皮");
                product_name.add(7, "手工黑輪");
                bundle.putSerializable("product_name", product_name);

                /* 輸入產品價格 */
                product_price.add(0, "30");
                product_price.add(1, "30");
                product_price.add(2, "10");
                product_price.add(3, "20");
                product_price.add(4, "20");
                product_price.add(5, "30");
                product_price.add(6, "20");
                product_price.add(7, "10");

                bundle.putSerializable("product_price", product_price);


//                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
                break;
        }

        handler.postDelayed(new Runnable() {
            public void run() {
                Navigation.findNavController(view).navigate(R.id.nav_bottom_store, bundle);
            }
        }, 1000); // 3000 milliseconds delay


    }





}
