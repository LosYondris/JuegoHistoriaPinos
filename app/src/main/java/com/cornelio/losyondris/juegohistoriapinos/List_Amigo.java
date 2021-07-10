package com.cornelio.losyondris.juegohistoriapinos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class List_Amigo extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__amigo);

        Button ent = (Button) findViewById(R.id.entrar);
        Button sal = (Button) findViewById(R.id.salir);
        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.revokeAccess(googleSignInClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            loginScreen();
                        }else {
                            //mensaje
                           // Log.i("TAG","No se pudo revokar sesion ");
                            Toast.makeText(List_Amigo.this,"No se pudo revokar sesion", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(googleSignInClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            loginScreen();
                        }else {
                            //mensaje
                           // Log.i("TAG","No se pudo Cerrar sesion ");
                            Toast.makeText(List_Amigo.this,"No se pudo Cerrar sesion", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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




    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleSignInClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignR(result);
        }else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignR(googleSignInResult);
                }
            });
        }
    }

    private void handleSignR(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            TextView nom = (TextView) findViewById(R.id.nombre);
            TextView correo = (TextView) findViewById(R.id.correo);
            TextView tel = (TextView) findViewById(R.id.tel);
            ImageView fot = (ImageView) findViewById(R.id.foto);



            nom.setText(account.getDisplayName());
            correo.setText(account.getEmail());
            tel.setText(account.getId());
            Glide.with(this).load(account.getPhotoUrl()).into(fot);

        }else {
            loginScreen();
        }
    }


    public  void loginScreen(){
        Intent ini = new Intent(List_Amigo.this,MainActivity.class);
        ini.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ini);
    }
    public  void loginOut(){}
    public  void revoke(){}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}