package com.followanalytics.sdk.azme.integration;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.followanalytics.sdk.azme.integration.azure.NotificationSettings;
import com.followapps.android.FollowApps;
import com.followapps.android.internal.push.PushManager;
import com.google.android.gms.gcm.GcmListenerService;

import java.util.Random;


/**
 * Created by damiii on 14/02/2018.
 */

public class OtherExternalPushService extends GcmListenerService{
    public static final int NOTIFICATION_ID = 123;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    public OtherExternalPushService() {
        super();
    }

    @TargetApi(26)
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);


        //Sender Id from Azure Microsoft
        if(from.equals(NotificationSettings.SenderId)){

            mNotificationManager = (NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //----------CREATE CHANNEL
                // The id of the channel.
                String id = "my_channel_01";
                // The user-visible name of the channel.
                CharSequence name = "Azure Channel";
                // The user-visible description of the channel.«
                String description = "blabla";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                // Configure the notification channel.
                mChannel.setDescription(description);
                mChannel.enableLights(true);
                // Sets the notification light color for notifications posted to this
                // channel, if the device supports this feature.
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel);
                //----------END CREATE CHANNEL

                mBuilder =
                        new NotificationCompat.Builder(this.getApplicationContext(), id)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Notification Hub Demo Azure")
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(data.getString(PushManager.BUNDLE_KEY_MESSAGE)))
                                .setContentText(data.getString(PushManager.BUNDLE_KEY_MESSAGE));
            }else{
                mBuilder =
                        new NotificationCompat.Builder(this.getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Notification Hub Demo Azure")
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(data.getString(PushManager.BUNDLE_KEY_MESSAGE)))
                                .setContentText(data.getString(PushManager.BUNDLE_KEY_MESSAGE));
            }
            mNotificationManager.notify(NOTIFICATION_ID+(new Random().nextInt()), mBuilder.build());
        }else{
            Log.d("OtherExternalPushServi","Sender id :"+from);
            FollowApps.onPushReceived(this.getApplicationContext(),data.getString(PushManager.BUNDLE_KEY_MESSAGE));
        }
    }
}
