package com.cornelio.losyondris.juegohistoriapinos.Alarma;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.cornelio.losyondris.juegohistoriapinos.MainActivity;
import com.cornelio.losyondris.juegohistoriapinos.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends IntentService {

    Notification notification;


    public NotificationService(String name) {
        super(name);
    }

    public NotificationService() {
        super("SERVICE");
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent2) {

        String NOTIFICATION_CHANNEL_ID = "LosYondriss";
        final Intent mypagina = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,mypagina,0);

        RemoteViews notificationLayout = new RemoteViews("com.cornelio.losyondris.juegoortografia", R.layout.notificacion_uno);
        RemoteViews notificationLayoutExpanded = new RemoteViews("com.cornelio.losyondris.juegoortografia", R.layout.notificacion_uno);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID);
        // Notification customNotification = new NotificationCompat.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setCustomContentView(notificationLayout);
        builder.setContentIntent(pendingIntent);
        builder.setCustomBigContentView(notificationLayoutExpanded);
        // builder.build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(7772,builder.build());

//        String NOTIFICATION_CHANNEL_ID = getApplicationContext().getString(R.string.app_name);
//        Context context = this.getApplicationContext();
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent mIntent = new Intent(this, MainActivity.class);
//        Resources res = this.getResources();
//        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//
//        String message = "Notificaciones public";
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            final int NOTIFY_ID = 0; // ID of notification
//            String id = NOTIFICATION_CHANNEL_ID; // default_channel_id
//            String title = NOTIFICATION_CHANNEL_ID; // Default Channel
//            PendingIntent pendingIntent;
//            NotificationCompat.Builder builder;
//            NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            if (notifManager == null) {
//                notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            }
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
//            if (mChannel == null) {
//                mChannel = new NotificationChannel(id, title, importance);
//                mChannel.enableVibration(true);
//                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//                notifManager.createNotificationChannel(mChannel);
//            }
//            builder = new NotificationCompat.Builder(context, id);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentTitle(getString(R.string.app_name)).setCategory(Notification.CATEGORY_SERVICE)
//                    .setSmallIcon(R.mipmap.ic_launcher)   // required
//                    .setContentText(message)
//                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setAutoCancel(true)
//                    .setSound(soundUri)
//
//                    .setContentIntent(pendingIntent)
//                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            Notification notification = builder.build();
//            notifManager.notify(NOTIFY_ID, notification);
//
//            startForeground(1, notification);
//
//        } else {
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            notification = new NotificationCompat.Builder(this)
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
//                    .setSound(soundUri)
//                    .setAutoCancel(true)
//                    .setContentTitle(getString(R.string.app_name)).setCategory(Notification.CATEGORY_SERVICE)
//                    .setContentText(message).build();
//            int NOTIFICATION_ID = 1;
//            notificationManager.notify(NOTIFICATION_ID, notification);
//        }
    }
}