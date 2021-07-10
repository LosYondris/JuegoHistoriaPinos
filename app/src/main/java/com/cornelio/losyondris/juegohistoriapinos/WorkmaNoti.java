package com.cornelio.losyondris.juegohistoriapinos;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkmaNoti extends Worker {
    public WorkmaNoti(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    public static void guardar(){
        OneTimeWorkRequest note = new OneTimeWorkRequest.Builder(WorkmaNoti.class)
                .setInitialDelay(1, TimeUnit.DAYS)
                .setConstraints(setCons()).build();
        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(note);

       // Long time = 1625086800852L;
//        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
//                WorkmaNoti.class,
//                1,
//                TimeUnit.HOURS,
//                PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
//                TimeUnit.MILLISECONDS)
//                .setInitialDelay(1, TimeUnit.MINUTES)
//                .addTag("send_reminder_periodic")
//                .build();
//
//
//        WorkManager.getInstance()
//                .enqueueUniquePeriodicWork("send_reminder_periodic", ExistingPeriodicWorkPolicy.REPLACE, workRequest);




    }

//1625086800852L
//    public static void guardar(Long duracion, Data data,String tag){
//        OneTimeWorkRequest note = new OneTimeWorkRequest.Builder(WorkmaNoti.class)
//                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
//                .setInputData(data).build();
//        WorkManager instance = WorkManager.getInstance();
//        instance.enqueue(note);

//
//    }


    private static Constraints setCons() {
        Constraints constraints = new Constraints.Builder().build();
        return constraints;
    }

    @NonNull
    @Override
    public Result doWork() {
      //  guardar();
       //  vernotificacion();

//        String titulo = getInputData().getString("titulo");
//        String detalle = getInputData().getString("detalle");
//        int id = (int) getInputData().getLong("idnoti",0);
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//            String time = simpleDateFormat.format(new Date());
//            if(time.equals("9:26")){
//                Log.i("TAG","Hoy Son Valor: "+time);
//            }
//


//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//
//                Log.d("Log", "SSSSSHello World");
//                guardar();
//                vernotificacion();
//
//            }
//        },0, 60000);


        return Result.success();
    }




     public void vernotificacion() {

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


//        //PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(), 0);
//        NotificationManager mn = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            @SuppressLint("WrongConstant") NotificationChannel nc = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "PINOS", NotificationManager.IMPORTANCE_MAX);
//            // Configure the notification channel.
//            nc.setDescription("Probando la notificacion");
//            nc.enableLights(true);
//            nc.setLightColor(Color.RED);
//            nc.setVibrationPattern(new long[]{500, 1000,1000,500,1000,500,2000});
//            nc.enableVibration(true);
//            mn.createNotificationChannel(nc);
//        }
//
//        builder.setAutoCancel(true)
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("YA! Es Hora de Jugar")
//                .setTicker("Nueva Notificacaion")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText("Juego de Historia RD")
//                .setContentIntent(pendingIntent)
//                .setContentInfo("nueva");
//
//        mn.notify(77777,builder.build());
//





//        // NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
//        android.support.v4.app.NotificationCompat.Builder notificationBuilder =new android.support.v4.app.NotificationCompat.Builder(this);
//        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                //.setSmallIcon(R.drawable.ic_my_icono)
//                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
//                        R.mipmap.ic_launcher))
//                .setTicker(title)
//                .setPriority(Notification.PRIORITY_MAX)
//                .setContentTitle(title)
//                .setVibrate(new long[]{0, 500, 1000,500,1000,500,2000})
//                .setContentText(body)
//                .setContentIntent(pendingIntent)
//                .setContentInfo("RD911");
//
//        Random random = new Random();
//        // random.nextInt()
//        notificationManager.notify(random.nextInt(), notificationBuilder.build());







    }


}
