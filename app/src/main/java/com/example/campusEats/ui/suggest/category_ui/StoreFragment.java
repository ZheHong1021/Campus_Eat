package com.example.campusEats.ui.suggest.category_ui;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusEats.MainActivity;
import com.example.campusEats.R;
import com.example.campusEats.ui.Convert;
import com.example.campusEats.ui.suggest.bank_ui.BankFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class StoreFragment extends Fragment {

    TextView store_name;
    TextView store_category;
    ImageView store_image;
    Typeface typeface;
    LinearLayout total_food;
    TextView order_number;
    LinearLayout food;
    ProgressDialog progressDialog;
    Handler handler;

    //    Activity MainActivity = new MainActivity();
    private  MainActivity mainActivity;



    /* 計算總金額 */
    Integer total_cost = 0;
    int idx;
    String total_order;
    Bundle bundle;


    /* 點餐紀錄 */
    ArrayList<String> order_name_List = new ArrayList<String>(); // 也可以不指定
    ArrayList<Integer> order_count_List = new ArrayList<Integer>(); // 也可以不指定
    ArrayList<Integer> order_price_list = new ArrayList<Integer>(); // 也可以不指定
    ArrayList<String> order_Pname;

    /* 呼叫銀行過來扣款 */
    BankFragment bank = new BankFragment();
    Integer now_balance = 0;
    Integer update_balance = 0;

    /* 單位轉換 */
    Convert convert = new Convert();

    // Make sure to use the FloatingActionButton
    // for all the FABs
    FloatingActionButton mAddFab, mAddAlarmFab, mGoLocationFab;

    // These are taken to make visible and invisible along
    // with FABs
    TextView addAlarmActionText, mGoLocationText;

    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);
        total_food = root.findViewById(R.id.total_food);


        /* 將從Category那邊得到的資料作處理 */


        bundle = getArguments();
        final String store_name_Str = bundle.getString("store_name");
        final String store_category_Str = bundle.getString("store_category");
        final Integer store_Get_img = bundle.getInt("store_img");

        store_name = root.findViewById(R.id.store_name);
        store_category = root.findViewById(R.id.store_category);
        store_image = root.findViewById(R.id.store_img);
        store_name.setText(store_name_Str);
        store_category.setText(store_category_Str);
        store_image.setImageResource(store_Get_img);
        store_image.setAlpha(0.15f);



        /* 返回 MainActivity設定 */
        Button go_back_category = root.findViewById(R.id.go_back_category);
        go_back_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_bottom_category);
            }
        });



        /* Fab按鈕設定 */
        // Register all the FABs with their IDs
        // This FAB button is the Parent
        mAddFab = root.findViewById(R.id.add_fab);
        // FAB button
        mAddAlarmFab = root.findViewById(R.id.add_alarm_fab);
        mGoLocationFab = root.findViewById(R.id.add_person_fab);

        // Also register the action name text, of all the FABs.
        addAlarmActionText = root.findViewById(R.id.add_alarm_action_text);
        mGoLocationText = root.findViewById(R.id.add_person_action_text);

        // Now set all the FABs and all the action name
        // texts as GONE
        mAddAlarmFab.setVisibility(View.GONE);
        mGoLocationFab.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        mGoLocationText.setVisibility(View.GONE);

        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are invisible
        isAllFabsVisible = false;

        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs VISIBLE.
                            mAddAlarmFab.show();
                            mGoLocationFab.show();
                            addAlarmActionText.setVisibility(View.VISIBLE);
                            mGoLocationText.setVisibility(View.VISIBLE);

                            // make the boolean variable true as
                            // we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = true;
                        } else {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs GONE.
                            mAddAlarmFab.hide();
                            mGoLocationFab.hide();
                            addAlarmActionText.setVisibility(View.GONE);
                            mGoLocationText.setVisibility(View.GONE);

                            // make the boolean variable false
                            // as we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mGoLocationFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "即將前往", Toast.LENGTH_SHORT).show();
                        /* Google Map */
                        // Map point based on address
                        Uri location = Uri.parse("geo:0,0?q=" + store_name_Str);
                        // Or map point based on latitude/longitude

                        // 高雄夢時代經緯度
//                 Uri location = Uri.parse("geo:22.5951498,120.304735?z=17"); // z param is zoom level
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                            getActivity().startActivity(mapIntent);
                        }
                    }
                });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mAddAlarmFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        total_order = "";
                        for(int i = 0; i < order_name_List.size() ; i++) {
                            /* 結帳只記錄有點到的 */
                            if(order_count_List.get(i) > 0){
                                total_order += String.valueOf(order_name_List.get(i)) + " $" + String.valueOf(order_price_list.get(i)) + "  x  " + String.valueOf(order_count_List.get(i) + "\n");

                                order_Pname = new ArrayList<String>();
                                order_Pname.add(order_name_List.get(i)  + " x " + String.valueOf(order_count_List.get(i)));

//                                /* 產品名稱 */
//                                bundle.putString("product_name_" + String.valueOf(i), order_name_List.get(i));

                                /* 當到陣列最後一筆時計算總花費 */
                                if(i == order_count_List.size() - 1){
                                    total_order += "\n一共花費：$" + String.valueOf(total_cost);
                                }
                            }
                        }




                        new AlertDialog.Builder(getContext())
                                .setIcon(R.drawable.icon_bell)
                                .setTitle("結帳確認")
                                .setMessage(total_order)
                                .setPositiveButton("確定送出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        now_balance = bank.getBalance();
                                        update_balance = now_balance - total_cost;

                                        /* put商店名稱到訂單 */
                                        bundle.putString("putOrderStore", store_name_Str);

                                        /* put消費總金額到訂單 */
                                        bundle.putInt("putOrderCost", total_cost);

                                        bundle.putSerializable("product_array", order_Pname);

                                        // Initialize Progress Dialog
                                        progressDialog = new ProgressDialog(getContext());
                                        // Show Dialog
                                        progressDialog.show();

                                        handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                progressDialog.dismiss();


                                                if(update_balance < 0){
                                                    new AlertDialog.Builder(getContext())
                                                            .setIcon(R.drawable.ic_baseline_close_24)
                                                            .setTitle("餘額不足，快去加值存款")

                                                            .setPositiveButton("立刻加值", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Navigation.findNavController(view).navigate(R.id.nav_bottom_bank);
                                                                }
                                                            })

                                                            .setNegativeButton("取消",null)
                                                            .show();
                                                }else{


                                                    mainActivity.sendNotification();


                                                    new AlertDialog.Builder(getContext())
                                                            .setIcon(R.drawable.ic_baseline_check_24)
                                                            .setTitle("送出成功")
                                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    for(int i = 0; i < order_name_List.size() ; i++) {
                                                                        order_count_List.remove(i);
                                                                        order_name_List.remove(i);
                                                                        order_price_list.remove(i);
                                                                    }
                                                                    bank.setBalance(update_balance);
                                                                    Navigation.findNavController(view).navigate(R.id.nav_bottom_record, bundle);
                                                                }
                                                            })
                                                            .show();

                                                }
                                            }
                                        }, 2000); // 3000 milliseconds delay


                                        // Set Content View
                                        progressDialog.setContentView(R.layout.progress_dialog);

                                        // Set Transparent Background
                                        progressDialog.getWindow().setBackgroundDrawableResource(
                                                android.R.color.transparent
                                        );

                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();

                    }
                });


        /* 從 Category得到的值 */
        ArrayList array_name = bundle.getParcelableArrayList("product_name");
        ArrayList array_price = bundle.getParcelableArrayList("product_price");

        for(int i = 0; i < array_name.size(); i++  ) {
            addViewProduct(String.valueOf(array_name.get(i)), String.valueOf(array_price.get(i)) );
        }


        return root;
    }



    /* 新增產品的函式 */
    public void addViewProduct(final String name, final String price){

        /* 一個包各一個產品的Linear(被 total_food包著) */
        final LinearLayout food = new LinearLayout(getContext());

//        因為在 java這邊使用的是 px；而我們在res那邊用的是dp，所以必須做轉換
//        Integer convert_px = (int)convert.convertDpToPixel(100, getContext());
        LinearLayout.LayoutParams food_params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)convert.convertDpToPixel(100, getContext()));

        food_params.setMargins(0, 0, 0, 5);
        food.setLayoutParams(food_params);
        food.setBackgroundColor(Color.parseColor("#dfe6e9"));

        food.setOrientation(LinearLayout.HORIZONTAL);
        //        全部設為15 dp
        Integer convert_PX_padding_fifteen = (int)convert.convertDpToPixel(15, getContext());
        food.setPadding(convert_PX_padding_fifteen, convert_PX_padding_fifteen, convert_PX_padding_fifteen, convert_PX_padding_fifteen);

        total_food.addView(food);



        /* 這個來包紀錄點餐數量的Layout (當點擊後會跑出數字TextView) */

        final LinearLayout order_layout = new LinearLayout(getContext());


        LinearLayout.LayoutParams order_layout_params = new LinearLayout.LayoutParams(
                (int)convert.convertDpToPixel(20, getContext()),
                (int)convert.convertDpToPixel(20, getContext()));
        order_layout.setLayoutParams(order_layout_params);

        food.addView(order_layout);



        /* 一個包各產品文字(名字、價錢)的Linear(被food包著)  */
        LinearLayout food_font = new LinearLayout(getContext());
        food_font.setLayoutParams(new LinearLayout.LayoutParams(
                (int)convert.convertDpToPixel(250, getContext()),
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        food_font.setOrientation(LinearLayout.VERTICAL);

//        全部設為10 dp
        Integer convert_PX_padding_ten = (int)convert.convertDpToPixel(10, getContext());
        food_font.setPadding(convert_PX_padding_ten, convert_PX_padding_ten, convert_PX_padding_ten, convert_PX_padding_ten);

        food.addView(food_font);


        /* 產品名稱 */
        final TextView product_name =new TextView(getContext());
        product_name.setText(name);

        product_name.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        product_name.setTextSize(18);

        // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        product_name.setTypeface(typeface);

        food_font.addView(product_name);


        /* 產品價格 */
        final TextView product_price =new TextView(getContext());
        product_price.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // 設定字形
        typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
        product_price.setTypeface(typeface);

        product_price.setText("$ "  + price);
        product_price.setTextColor(Color.parseColor("#999999"));
        product_price.setTextSize(16);
        food_font.addView(product_price);

        /* 產品圖片 */
        ImageView product_img = new ImageView(getContext());
        product_img.setLayoutParams(new LinearLayout.LayoutParams(
                (int)convert.convertDpToPixel(100, getContext()),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        product_img.setImageResource(R.drawable.store_01);
        product_img.setScaleType(ImageView.ScaleType.FIT_XY);

        food.addView(product_img);




        /* 點選單一產品後的效果 */
        food.setOnClickListener(new View.OnClickListener() {

            private Integer count = 0;
            @Override
            public void onClick(View v) {
                /* 每次點都要先清空原本的 */
                order_layout.removeAllViews();
                count += 1;

                /*計算總金額*/
                total_cost += Integer.parseInt(price);

                food.setBackgroundColor(Color.parseColor("#eee333"));

                /* 單筆訂單數量 */
                order_number = new TextView(getContext());
                order_number.setText(String.valueOf(count));

                order_number.setLayoutParams(new LinearLayout.LayoutParams(
                        (int) convert.convertDpToPixel(20, getContext()),
                        (int) convert.convertDpToPixel(20, getContext())
                ));
                order_number.setTextSize(14);
                order_number.setBackgroundResource(R.drawable.badge_background);
                order_number.setTextColor(Color.parseColor("#ffffff"));
                order_number.setGravity(Gravity.CENTER);

                // 設定字形
                typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);
                order_number.setTypeface(typeface);
                order_layout.addView(order_number);



                // 長按寫法
                food.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (count > 0) {
                            count -= 1;
                            order_layout.removeAllViews();
                            food.setBackgroundColor(Color.parseColor("#eee333"));

                            /* 單筆訂單數量 */
                            order_number = new TextView(getContext());
                            order_number.setText(String.valueOf(count));

                            order_number.setLayoutParams(new LinearLayout.LayoutParams(
                                    (int) convert.convertDpToPixel(20, getContext()),
                                    (int) convert.convertDpToPixel(20, getContext())
                            ));
                            order_number.setTextSize(14);
                            order_number.setBackgroundResource(R.drawable.badge_background);
                            order_number.setTextColor(Color.parseColor("#ffffff"));
                            order_number.setGravity(Gravity.CENTER);
                            // 設定字形
                            typeface = ResourcesCompat.getFont(getContext(), R.font.poppinsbold);

                            if (count == 0) {
                                if (Integer.parseInt(price) != 0) {
                                    /*計算總金額*/
                                    total_cost -= Integer.parseInt(price);
                                }
                                order_layout.removeAllViews();
                                food.setBackgroundColor(Color.parseColor("#dfe6e9"));
                            } else {

                                /*計算總金額*/
                                total_cost -= Integer.parseInt(price);
                                order_number.setTypeface(typeface);
                                order_layout.addView(order_number);
                            }

                        }


                        boolean isIn = order_name_List.contains(name);
                        if (isIn) {
                            /*長按刪除*/
                            /* 判斷當筆產品的陣列位置 */
                            idx = order_name_List.indexOf(name);
                            /* 隨時更新紀錄數量 */
                            if (order_name_List.get(idx) == name) {
                                /* 先清除當前位置的資料，後續補上 */
                                order_name_List.remove(idx);
                                order_count_List.remove(idx);
                                order_price_list.remove(idx);

                                /* idx補上更新後的資料 */
                                order_name_List.add(idx, name);
                                order_count_List.add(idx, count);
                                order_price_list.add(idx, Integer.parseInt(price));
                            }
                        }
                        return true;

                    }
                });

                /* 判斷當前陣列是否有此筆產品訂單 (有則不用再新增) */
                boolean isIn = order_name_List.contains(name);
                if (!isIn) {
                    /* 單點新增 */
                    /* 點擊單筆後新增一個陣列紀錄 */
                    order_name_List.add(name);
                    order_count_List.add(count);
                    order_price_list.add(Integer.parseInt(price));

                }else{
                    /* 判斷當筆產品的陣列位置 */
                    idx = order_name_List.indexOf(name);
                    /* 隨時更新紀錄數量 */
                    if (order_name_List.get(idx) == name) {
                        /* 先清除當前位置的資料，後續補上 */
                        order_name_List.remove(idx);
                        order_count_List.remove(idx);
                        order_price_list.remove(idx);

                        /* idx補上更新後的資料 */
                        order_name_List.add(idx, name);
                        order_count_List.add(idx, count);
                        order_price_list.add(idx, Integer.parseInt(price));

                    }
                }
            }


        });





    }



}