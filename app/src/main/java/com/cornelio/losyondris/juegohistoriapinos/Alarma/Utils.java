package com.cornelio.losyondris.juegohistoriapinos.Alarma;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class Utils {

    public static void otroMas(Context ctxt){
        Intent myIntent = new Intent(ctxt, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) ctxt.getSystemService(ALARM_SERVICE);

        Calendar firingCal= Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();

        firingCal.set(Calendar.HOUR, 3); // At the hour you wanna fire
        firingCal.set(Calendar.MINUTE, 0); // Particular minute
        firingCal.set(Calendar.SECOND, 0); // particular second

        long intendedTime = firingCal.getTimeInMillis();
        long currentTime = currentCal.getTimeInMillis();

        if(intendedTime >= currentTime){
            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.i("TAG","Dentro " + intendedTime);
        } else{
            firingCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = firingCal.getTimeInMillis();
            Log.i("TAG","Fuera " + intendedTime);
            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }


}
