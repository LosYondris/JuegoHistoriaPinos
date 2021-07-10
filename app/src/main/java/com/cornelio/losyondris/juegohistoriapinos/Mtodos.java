package com.cornelio.losyondris.juegohistoriapinos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

class Mtodos {
    MediaPlayer mp;

    //  TODO  Sonido de todos los boton
    public  void  SonidoGeneral(Context context, int sonido ){
         mp = MediaPlayer.create(context, sonido);

            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                    //mp.reset();
                }
            });
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    mediaPlayer.reset();
                    return false;
                }
            });



        @SuppressLint("ServiceCast") Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(100);
        VibrationEffect effect = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            effect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE);
//            vib.vibrate(effect);
        }

      // mp.reset();
    }

    //  TODO   Sonido de Fondo
    public  void  SonidoFondo(Context context, int sonido){
        mp = MediaPlayer.create(context, sonido);
        mp.setLooping(true);
        mp.start();
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 7 * 7 / 7, 0);
    }


    //  TODO   Sonido en Estod
    public  void  SonidoStop(){
        mp.stop();
    }



}
