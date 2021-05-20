package com.example.campusEats;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
//    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
//    private NotificationManager mNotifyManager;
//    private static final int NOTIFICATION_ID = 0;
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    // 定義一個 NotificationManager
    public NotificationManager mNotifyManager;
    // 定義通知的ID(預設為 0)->等等create icon會用到
    public static final int NOTIFICATION_ID = 0;

    /* 透過從 LoginFragment得到是否有登入的結果來判斷要用哪個介面 */
    static Boolean is_Login = false;
    static String user_ID;
    static String user_Name;
    static String user_Email;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        /* 確認說是否有捕抓到資料(沒有則 is_Login為空字串) */
        if (bundle != null) {
            is_Login = (Boolean) bundle.getBoolean("is_Login");
            user_ID = (String) bundle.getString("id");
            user_Name = (String) bundle.getString("name");
            user_Email = (String) bundle.getString("email");
        }

        // 在LoginFragment中，如果is_Login為True代表登入成功
        if (is_Login) {
            // 將會是登入後的介面
            setContentView(R.layout.activity_dashboard);
        } else {
            // 如果沒登入就是原本的介面(main)
            setContentView(R.layout.activity_main);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // 在LoginFragment中，如果is_Login為True代表登入成功
        if (is_Login) {
            // 將會是登入後的介面
            drawer = findViewById(R.id.drawer_dashboard_layout);
        } else {
            drawer = findViewById(R.id.drawer_layout);
        }


        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_logout, R.id.nav_suggest)
                .setDrawerLayout(drawer)
                .build();


        if (is_Login) {
            /* 設立一個headerView來對應到我的 Nav */
            // https://stackoverflow.com/questions/34973456/how-to-change-text-of-a-textview-in-navigation-drawer-header
            View headerView = navigationView.getHeaderView(0);
            /* 此時就可以修改 nav_header_main的文字了(沒有上面那行會Error) */
            TextView navName = (TextView) headerView.findViewById(R.id.nav_header_title);
            TextView navEmail = (TextView) headerView.findViewById(R.id.nav_header_email);
            navName.setText(user_Name);
            navEmail.setText(user_Email);
        }


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        createNotificationChannel();
        // 如果沒有這一行會GG(去呼叫該方法)
        createNotificationChannel();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    public void sendNotification() {
//        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
//        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
//    }
//
//    public void createNotificationChannel()
//    {
//        mNotifyManager = (NotificationManager)
//                getSystemService(NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >=
//                android.os.Build.VERSION_CODES.O) {
//
//            // Create a NotificationChannel
//            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
//                    "Mascot Notification", NotificationManager
//                    .IMPORTANCE_HIGH);
////            notificationChannel.enableLights(true);
////            notificationChannel.setLightColor(Color.RED);
////            notificationChannel.enableVibration(true);
////            notificationChannel.setDescription("Notification from Mascot");
//            mNotifyManager.createNotificationChannel(notificationChannel);
//        }
//    }
//
//    private NotificationCompat.Builder getNotificationBuilder(){
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
//                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
//                .setContentTitle("BoneFood")
//                .setContentText("餐點訂購成功")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.ic_round_fastfood_24)
//                .setContentIntent(notificationPendingIntent)
//                .setAutoCancel(true);
//        return notifyBuilder;
//
//    }
// Create Notify的方法
    public void createNotificationChannel()
    {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        // 檢查 API版本是否有 26以上(防呆)
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create a NotificationChannel (驗證Channel_ID)
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);  // IMPORTANCE_HIGH: 重要的訊息通知

            // 設定通知的樣式內容
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
    // Get通知的Builder的方法
    public NotificationCompat.Builder getNotificationBuilder(){
        // 呼叫另一個 Explicit Intent (會關聯到 PendingIntent)
        Intent notificationIntent = new Intent(this, MainActivity.class);

        // PendingIntent
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // 使用 PRIMARY_CHANNEL_ID
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);

        // 設定一下通知的文字內容(Title、Content、Icon)
        notifyBuilder.setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_round_fastfood_24)

                // 啟動Pending Intent(離開 APP後，再點通知後會回到 APP中)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 設定優先順序(HIGH最大)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);  // APP關掉，剛剛的通知會關掉(false)、保留(true)

        return notifyBuilder;
    }
    // send方法(送出通知)
    public void sendNotification() {

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());


    }







}








