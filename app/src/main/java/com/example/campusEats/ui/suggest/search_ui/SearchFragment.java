package com.example.campusEats.ui.suggest.search_ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusEats.MainActivity;
import com.example.campusEats.R;
import com.example.campusEats.ui.Convert;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static android.content.ContentValues.TAG;


public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {
//    初始化
    ImageView store_img;
    LinearLayout total_store;
    Spinner spinner;
    Spinner spinner_budget;
    ArrayAdapter<CharSequence> adapter;
    Typeface typeface;

    /* 單位轉換(dp 跟 pixel的轉換) */
    Convert convert = new Convert();


    //    拿來用Toast (不知道為什麼 Fragment不能直接用 getApplication)
    public void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        total_store = root.findViewById(R.id.total_store);
        store_img = root.findViewById(R.id.store_img);


        /* 選擇的商品總類 */
         spinner = (Spinner) root.findViewById(R.id.store_category);
        // Create an ArrayAdapter using the string array and a default spinner layout
       adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.store_category, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);



        spinner_budget = (Spinner) root.findViewById(R.id.my_budget);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.my_budget_select, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_budget.setAdapter(adapter);

        spinner_budget.setOnItemSelectedListener(this);


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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        /* 得到點選後的數值 */
        String select_item =spinner.getSelectedItem().toString();
        /* Toast出來 */
//        Toast.makeText(getContext(), select_item, Toast.LENGTH_SHORT).show();

        switch (select_item){
            case "全部":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("大內製麵-土庫店", "麵食", R.drawable.store_02);
                addViewProduct("芙樂廚坊早午餐", "早午餐", R.drawable.store_01);
                addViewProduct("第一讚炒飯專賣店", "炒飯", R.drawable.store_03);
                addViewProduct("元之氣早午餐 - 清豐二路", "早午餐", R.drawable.store_04);
                addViewProduct("大上海火鍋 - 楠梓店", "火鍋", R.drawable.store_05);
                addViewProduct("炸魂雞排 - 楠梓店", "炸物", R.drawable.store_06);
                addViewProduct("高雄滷味王 - 楠梓店", "滷味", R.drawable.store_08);
                addViewProduct("北平楊寶寶蒸餃", "餃類", R.drawable.store_07);
                break;

            case "麵食":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("大內製麵-土庫店", "麵食", R.drawable.store_02);
                break;
            case "炒飯":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("第一讚炒飯專賣店", "炒飯", R.drawable.store_03);
                break;
            case "早午餐":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("芙樂廚坊早午餐", "早午餐", R.drawable.store_01);
                addViewProduct("元之氣早午餐 - 清豐二路", "早午餐", R.drawable.store_04);
                break;
            case "火鍋":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("大上海火鍋 - 楠梓店", "火鍋", R.drawable.store_05);
                break;
            case "炸物":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("炸魂雞排 - 楠梓店", "炸物", R.drawable.store_06);
                break;
            case "滷味":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("高雄滷味王 - 楠梓店", "滷味", R.drawable.store_08);
                break;
            case "餃類":
                /* 先清空之前的 */
                total_store.removeAllViews();
                addViewProduct("北平楊寶寶蒸餃", "餃類", R.drawable.store_07);
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    /* 分類商店的函式 */
    public void addViewProduct(String name, String category, Integer store_img){

        /* 一個包各一個產品的Linear(被 total_food包著) */
        LinearLayout store = new LinearLayout(getContext());


//        因為在 java這邊使用的是 px；而我們在res那邊用的是dp，所以必須做轉換
//        Integer convert_px = (int)convert.convertDpToPixel(100, getContext());
        LinearLayout.LayoutParams food_params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)convert.convertDpToPixel(150, getContext()));

        food_params.setMargins(0, 0, 0, 10);
        store.setLayoutParams(food_params);
        store.setBackgroundColor(Color.parseColor("#ffffff"));
        // 設為水平
        store.setOrientation(LinearLayout.HORIZONTAL);

        //        全部設為15 dp
        Integer convert_PX_padding_fifteen = (int)convert.convertDpToPixel(15, getContext());
        store.setPadding(convert_PX_padding_fifteen, convert_PX_padding_fifteen, convert_PX_padding_fifteen, convert_PX_padding_fifteen);

        total_store.addView(store);


        /* 商店圖片 */

        ImageView product_img = new ImageView(getContext());
        product_img.setLayoutParams(new LinearLayout.LayoutParams(
                (int)convert.convertDpToPixel(175, getContext()),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        product_img.setImageResource(store_img);
        product_img.setScaleType(ImageView.ScaleType.FIT_XY);

        store.addView(product_img);



        /* 一個包各產品文字(名字、價錢)的Linear(被food包著)  */
        LinearLayout food_font = new LinearLayout(getContext());
        food_font.setLayoutParams(new LinearLayout.LayoutParams(
                (int)convert.convertDpToPixel(275, getContext()),
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        food_font.setOrientation(LinearLayout.VERTICAL);

//        全部設為10 dp
        Integer convert_PX_padding_ten = (int)convert.convertDpToPixel(10, getContext());
        food_font.setPadding(convert_PX_padding_ten, convert_PX_padding_ten, convert_PX_padding_ten, convert_PX_padding_ten);

        store.addView(food_font);


        /* 商店名稱 */
        TextView store_name =new TextView(getContext());

        /* 函式所填入的商店名稱 */
        store_name.setText(name);

        store_name.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        store_name.setTextSize(18);

        // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        store_name.setTypeface(typeface);

        food_font.addView(store_name);



        /* 產品分類 */
        TextView product_category =new TextView(getContext());
        product_category.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        product_category.setTypeface(typeface);

        /* 設定種類 */
        product_category.setText(category);

        product_category.setBackgroundResource(R.drawable.badge_background);

        //        全部設為3 dp
        Integer convert_PX_padding_three = (int)convert.convertDpToPixel(3, getContext());
        product_category.setPadding(convert_PX_padding_three, convert_PX_padding_three, convert_PX_padding_three, convert_PX_padding_three);

        product_category.setLayoutParams(new LinearLayout.LayoutParams(
                (int)convert.convertDpToPixel(50, getContext()),
                (int)convert.convertDpToPixel(20, getContext())
        ));
        product_category.setTextColor(Color.parseColor("#ffffff"));
        product_category.setTextSize(10);
        product_category.setGravity(Gravity.CENTER);
        food_font.addView(product_category);
    }

}