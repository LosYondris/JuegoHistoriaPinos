package com.cornelio.losyondris.juegohistoriapinos.Alarma;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.cornelio.losyondris.juegohistoriapinos.R;

public class RebootServiceClass extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RebootServiceClass(String name) {
        super(name);
        startForeground(1, new Notification());
    }

    public RebootServiceClass() {
        super("RebootReceiver");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String intentType = intent.getExtras().getString("caller");
        if (intentType == null) return;
        //if (intentType.equals("RebootReceiver")) {
        if (intentType.equals("RebootReceiver")) {
            SharedPreferences settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            // Utils.setAlarm(settings.getInt("alarmID", 0), settings.getLong("alarmTime", 0), this);

            //Utils.scheduleAlarms(this);
            Utils.otroMas(this);
            Log.i("TAG","myresive dentro de RebooServe");
        }

    }
}
