package com.example.campusEats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Notification extends AppCompatActivity {
    // 定義按鈕
    private Button button_notify;
    private Button button_cancel;
    private Button button_update;

    // 定義一個通知管道的ID (【static】永遠存在且【final】不會改變)
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // 定義一個 NotificationManager
    public NotificationManager mNotifyManager;

    // 定義通知的ID(預設為 0)->等等create icon會用到
    public static final int NOTIFICATION_ID = 0;

    // Task 3: Broadcase Receiver
    public static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    public NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_store);

//        // Button的點擊事件
//        button_notify = findViewById(R.id.notify);
//        button_notify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 呼叫send方法
//                sendNotification();
//            }
//        });

//        button_update = findViewById(R.id.update);
//        button_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Update the notification
//                updateNotification();
//            }
//        });
//
//        button_cancel = findViewById(R.id.cancel);
//        button_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Cancel the notification
//                cancelNotification();
//            }
//        });

        // 如果沒有這一行會GG(去呼叫該方法)
        createNotificationChannel();

        // 只有第一個可以按
        setNotificationButtonState(true, false, false);

        // 註冊Receiver
        registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION));
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
    // Get通知的Builder的方法(End)

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
    // Create Notify的方法(End)


    // send方法(送出通知)
    public void sendNotification() {
        // updateIntent
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // 通知會有文字(Update Notification)，點下去等於操作第二個Button(Update)
        notifyBuilder.addAction(R.drawable.ic_round_fastfood_24, "Update Notification", updatePendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // 把第一個鎖住，其他開起來可以按
//        setNotificationButtonState(false, true, true);

    }
    // send方法(End)


    public void updateNotification() {
        // png->bitmap(點陣圖)
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.ic_baseline_account_circle_24);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!"));

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // 把第一、二個鎖住，剩第三個可以按
        setNotificationButtonState(false, false, true);

    }

    public void cancelNotification() {
        // 去掉剛剛跑出來的通知
        mNotifyManager.cancel(NOTIFICATION_ID);

        // 把第二、三個鎖住，剩第一個可以按
        setNotificationButtonState(true, false, false);

    }


    // 用來鎖定按鈕的方法
    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }


    // 【Task 3】NotificationReceiver 類別
    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification
            updateNotification();
        }
    }

    // 將註冊 Destory掉
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}