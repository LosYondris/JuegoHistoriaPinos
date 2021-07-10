package com.cornelio.losyondris.juegohistoriapinos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
Mtodos mtd = new Mtodos();
Juego jg = new Juego();
MediaPlayer mp;
GoogleApiClient googleSignInClient;
SignInButton signInButton;
private  static  int RC_SIGN_IN =  777;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mfirebaseDatabase.getReference();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// Pantalla completa
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); /////modo horizontar ( - )
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /////modo verticar ( | )

        ////boton de inicio Original
        Button d = (Button) findViewById(R.id.inicio);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(MainActivity.this, R.raw.boop);
                startActivity(new Intent(MainActivity.this, Juego.class));
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
//                Intent fd = new Intent(MainActivity.this, Juego.class);

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        SignInButton mBtn = (SignInButton) findViewById(R.id.sign_in_button);
//        mBtn.setSize(SignInButton.SIZE_WIDE);
//        mBtn.setColorScheme(SignInButton.COLOR_DARK);
//        mBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent vst = new Intent(Auth.GoogleSignInApi.getSignInIntent(googleSignInClient));
//               startActivityForResult(vst, RC_SIGN_IN);
//            }
//        });


        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .setRequiresStorageNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                WorkmaNoti.class,  10, TimeUnit.SECONDS)
                .setConstraints(constraints)
//                .setBackoffCriteria(
//                        BackoffPolicy.LINEAR,
//                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
//                        TimeUnit.MILLISECONDS
//                )
                .build();

        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(periodicWorkRequest);








    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            hangleSignInResult(result);
        }
    }

    private void hangleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            Log.i("TAG","Si");
            //GoogleSignInAccount account = result.getSignInAccount();
            llamarJuego();
        }else {
            Log.i("TAG","No");
        }
    }

    private void llamarJuego() {
        Intent ini = new Intent(MainActivity.this,Juego.class);
        ini.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ini);
    }
}


