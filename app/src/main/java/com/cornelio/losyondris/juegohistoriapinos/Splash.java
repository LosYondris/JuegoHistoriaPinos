package com.cornelio.losyondris.juegohistoriapinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// Pantalla completa
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); /////modo horizontar ( - )
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /////modo verticar ( | )
        setContentView(R.layout.splash);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView losy = (TextView) findViewById(R.id.id_losyondris);
        TextView Dvby = (TextView) findViewById(R.id.id_desarrollado);
        ImageView logoDv = (ImageView) findViewById(R.id.id_logo);
        LinearLayout dvL = (LinearLayout) findViewById(R.id.id_coptray);

        losy.setAnimation(animation2);
        Dvby.setAnimation(animation2);
        logoDv.setAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // Intent vt = new Intent(Splash.this, Formulario.class);
                Intent vt = new Intent(Splash.this, MainActivity.class);
                startActivity(vt);
                overridePendingTransition(R.anim.right_in,R.anim.right_out);
                finish();
            }
        }, 4000);




   }
}