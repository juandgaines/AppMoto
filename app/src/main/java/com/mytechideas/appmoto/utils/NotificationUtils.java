package com.mytechideas.appmoto.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.mytechideas.appmoto.R;
import com.mytechideas.appmoto.activities.dashboardactivity.DashBoardActivity;
import com.mytechideas.appmoto.services.MotoBackgroundService;
import com.mytechideas.appmoto.services.MotoBackgroundTasks;

public class NotificationUtils {

    private static final int SENDING_DATA_PENDING_INTENT =3440 ;

    private static final String SENDING_DATA_NOTIFICATION_CHANNEL_ID="sending_data_notification_channel";
    private static final int SENDING_DATA_NOTIFICATION_ID = 2001;

    private static final int ACTION_STOP_SHARING = 4001;

    public static void createNotificationMotoCurrentlySharing(Context context){

        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(SENDING_DATA_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel_sending_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,
                SENDING_DATA_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_motorcycle_black_18dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_sending_title))
                .setContentText(context.getString(R.string.notification_sending_content))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_sending_content)
                ))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentintent(context))
                .addAction(stopAction(context))
                .setProgress(100,0,true)
                .setOngoing(true);

        if(Build.VERSION.SDK_INT>=  Build.VERSION_CODES.JELLY_BEAN
        && Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SENDING_DATA_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void createNotificationMotoAccident(Context context){

        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(SENDING_DATA_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel_sending_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,
                SENDING_DATA_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_motorcycle_black_18dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_accident_title))
                .setContentText(context.getString(R.string.notification_accident_content))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_accident_content)
                ))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentintent(context));

        if(Build.VERSION.SDK_INT>=  Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SENDING_DATA_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void createNotificationTripUploaded(Context context){

        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(SENDING_DATA_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel_sending_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,
                SENDING_DATA_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_motorcycle_black_18dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_shared_title))
                .setContentText(context.getString(R.string.notification_shared_content))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_shared_content)
                ))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentintent(context));

        if(Build.VERSION.SDK_INT>=  Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SENDING_DATA_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void createNotificationTripFailUploaded(Context context){

        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(SENDING_DATA_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel_sending_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,
                SENDING_DATA_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_motorcycle_black_18dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_shared_fail_title))
                .setContentText(context.getString(R.string.notification_shared_fail_content))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_shared_fail_content)
                ))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentintent(context));

        if(Build.VERSION.SDK_INT>=  Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SENDING_DATA_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void createNotificationMotoAccidentFailed(Context context) {
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(SENDING_DATA_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel_sending_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,
                SENDING_DATA_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_motorcycle_black_18dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_report_fail_title))
                .setContentText(context.getString(R.string.notification_report_fail_content))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_report_fail_content)
                ))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentintent(context));

        if(Build.VERSION.SDK_INT>=  Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SENDING_DATA_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static  void closeNotification(Context context){
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(SENDING_DATA_NOTIFICATION_ID);
    }

    private static PendingIntent contentintent(Context context){

        Intent startActivityIntent =new Intent(context, DashBoardActivity.class);

        return PendingIntent.getActivity(context,
                SENDING_DATA_PENDING_INTENT,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

    }

    private static Bitmap largeIcon(Context context){
        Resources resources=context.getResources();
        Bitmap largeIcon= BitmapFactory.decodeResource(resources, R.mipmap.ic_motorcycle_black_18dp);
        return  largeIcon;
    }

    private static NotificationCompat.Action stopAction(Context context){

        /*Intent stopSensorSharing =new Intent(context, MotoBackgroundService.class);
        stopSensorSharing.setAction(MotoBackgroundTasks.ACTION_STOP_SENSORS);

        PendingIntent stopSensorSharingPendingIntent= PendingIntent.getService(
                context,
                ACTION_STOP_SHARING,
                stopSensorSharing,
                PendingIntent.FLAG_UPDATE_CURRENT);*/

        Intent resultIntent =new Intent(context,DashBoardActivity.class);
        resultIntent.putExtra("extra",ACTION_STOP_SHARING);

        TaskStackBuilder stackBuilder= TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        PendingIntent resultPendingInteng= stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action stopSharingAction= new NotificationCompat.Action(  R.drawable.ic_baseline_stop_24px,
                context.getString(R.string.stop),
                resultPendingInteng);

        return stopSharingAction;
    }


}
