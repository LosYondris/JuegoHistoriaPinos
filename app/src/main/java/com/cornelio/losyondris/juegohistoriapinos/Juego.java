package com.cornelio.losyondris.juegohistoriapinos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cornelio.losyondris.juegohistoriapinos.Alarma.Utils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Juego extends AppCompatActivity {
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mReference;
    TextView tvPantalla, diaP,diaR, diaC, monedad, niveles;
    TextView mTiempo;
    Button confirmar;
   // MediaPlayer mp;
    RadioButton IdSrc ;
    RadioGroup radioGrup;
    View dview;
    Dialog mydialog;
    Mtodos mtd = new Mtodos();
    String timeLeftFormatted, getNiveles;
    int mMonedas = 0;
    private  static  final  long START_TIME_IN_MILLIS = 30000;
    boolean mTimerRunning;
    private  long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;
    int b = 0;
    int contBN,contMAL,preguntaAct;
    String[] parts;



//    RecyclerView rvs;
//    ArrayList<PooUser> list;
//    MyAdapter myAdapter;
    private  int ids_answers[] = { R.id.a,R.id.b,R.id.c,R.id.d};
    private int Correctac_answer;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        mydialog = new Dialog(this);

        FirebaseApp.initializeApp(this);
        mfirebaseDatabase = FirebaseDatabase.getInstance();
       // mfirebaseDatabase.setPersistenceEnabled(true);
        mReference = mfirebaseDatabase.getReference();
        mReference.keepSynced(true);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);




      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// Pantalla completa
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); /////modo horizontar ( - )
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /////modo verticar ( | )


        mTimerRunning = false;
        niveles = (TextView) findViewById(R.id.idNivel);
        getNiveles = niveles.getText().toString();
        // mtd.SonidoFondo(Juego.this,R.raw.fondo_b);
        b = +1;

        SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
        String Lmonedas = preferences.getString("BDmoneda","0");

        //seleccional cual dialogo inicar
        if(!Lmonedas.equals("0")){ playgames(); }else { registro_user(); }

        tvPantalla = findViewById(R.id.pantalla_preg);
        confirmar = (Button) findViewById(R.id.btnConfirmar);
        radioGrup = (RadioGroup) findViewById(R.id.btnRadios);
        mTiempo = findViewById(R.id.id_tiempo);
        AsignarSonidoBTN();
        BtnHeader();
        monedad =(TextView) findViewById(R.id.id_monedad);


        Utils.otroMas(Juego.this);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Id = radioGrup.getCheckedRadioButtonId();
                int answ = -1;

                for (int c = 0; c< ids_answers.length; c++){
                    if(ids_answers[c] == Id){
                        answ = c;
                    }
                }
                if (answ ==  Correctac_answer) {
                    // Toast.makeText(Juego.this,"Correcto",Toast.LENGTH_LONG).show();
                    mtd.SonidoGeneral(Juego.this,R.raw.victoria);
                    preguntaAct++;
                    contBN++;
                    General_pregunta();
                    radioGrup.clearCheck(); // demarcar los btn
                    resetTimer(); //resectiar el tiempo
                  int f = Integer.parseInt(monedad.getText().toString());
                    f = f + 5;
                  monedad.setText( String.valueOf(f));
                   // ValorNIvelsx(f);
                    OcultarCorazon(5);
                }
                else {
                    // Toast.makeText(Juego.this,"InCorrecto",Toast.LENGTH_LONG).show();
                    mtd.SonidoGeneral(Juego.this,R.raw.error);
                    radioGrup.clearCheck();
                    int anterio = contMAL++;
                    OcultarCorazon(contMAL);

                    resetTimer();

                    int f = Integer.parseInt(monedad.getText().toString());
                    int fx = f - 10;
                    if (fx <=0){
                        fx = 1;
                        monedad.setText( String.valueOf(fx));
                    }else {
                        monedad.setText( String.valueOf(fx));
                    }

                    //Log.i("TAG","valor: "+ f);

                }

                GuardarPref();

                //myRadioGrup(view);
                //Juego(mtds.poscion, view,IdSrc);
            }

        });


       // Data data = guarNOti("PINOS","Ya! es hora de jugar",01);

    }



    private void LeerPref() {
        SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
        String  Lmonedas = preferences.getString("BDmoneda","1");
        String Lnivel = preferences.getString("BDnivel","1");
        monedad.setText(Lmonedas);
        niveles.setText(Lnivel);

    }

    private void GuardarPref() {
        SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("d/M/yy");
//        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String salida = df.format(fecha);

        String Vmonedas = monedad.getText().toString();
        String Vnivel = niveles.getText().toString();

        editor.putString("BDmoneda",Vmonedas);
        editor.putString("BDnivel",Vnivel);
        editor.putString("fecha",salida);
        editor.commit();
    }

    public void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
               // Toast.makeText(Juego.this,"start", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
               // Toast.makeText(Juego.this,"onFine", Toast.LENGTH_LONG).show();
            }
        }.start();

        mTimerRunning = true;
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    private void resetTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        if(mTimerRunning == false){
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            startTimer();

        }
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
         timeLeftFormatted = String.format(Locale.getDefault(), ":%02d", seconds);
        //String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTiempo.setText(timeLeftFormatted);
        if(seconds == 0){
           // mtd.SonidoGeneral(Juego.this,R.raw.error);
            int mod = Integer.parseInt(monedad.getText().toString());
            if(findViewById(R.id.id_estrella2).getVisibility() == dview.INVISIBLE){
                findViewById(R.id.id_estrella3).setVisibility(dview.INVISIBLE);
                if(findViewById(R.id.id_estrella3).getVisibility() == dview.INVISIBLE && mod < 99){
                    finish();
                    //TODO - llamar el dialogo que diga tienes que esperar una hora para volver a jugar
                }else {
                    if(findViewById(R.id.id_estrella1).getVisibility() == dview.INVISIBLE){
                        Tienda();
                    }else {
                        Tienda();
                    }
                }

            }else {
                    UnaStrellaMeno();

            }

        }
    }


    private  void BtnHeader(){

        findViewById(R.id.id_btnAtra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    finish();
                   mTimerRunning = false;
                   mCountDownTimer.cancel();
                    //onStop();
                   // Toast.makeText(Juego.this,"True", Toast.LENGTH_LONG).show();



                //mtd.SonidoGeneral(Juego.this,R.raw.boop);
                Intent btnAtra = new Intent(Juego.this, MainActivity.class);
                startActivity(btnAtra);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        findViewById(R.id.id_Regalo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mtd.SonidoGeneral(Juego.this,R.raw.boop);
               int valoMoneda = Integer.parseInt(monedad.getText().toString());
                if(valoMoneda >= 50){
                    Comodin_Regalo();
                }else {
                    MonedaInsuficiente();
                }

               // overridePendingTransition(R.anim.right_in,R.anim.right_out);
            }
        });

        findViewById(R.id.idNivel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mtd.SonidoGeneral(Juego.this,R.raw.boop);
                Nivel();


            }
        });

        findViewById(R.id.idtienda).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mtd.SonidoGeneral(Juego.this,R.raw.boop);
                Tienda();

            }
        });

        findViewById(R.id.id_lista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(Juego.this, ListaAmigos.class));
                FirebaseSubir();
                listado();
            }
        });
    }

    private void General_pregunta() {
        SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
       int f = Integer.parseInt(preferences.getString("BDnivel","1"));
       // contBN = preguntaAct + 1;

        switch (f){
            case 1:
                String[] nivel1 = getResources().getStringArray(R.array.nivel_1);
                String q0 = nivel1[ preguntaAct ];
                General_rsc(q0);
                contBN = preguntaAct + 1;
                if (contBN == nivel1.length){ niveles.setText("2"); PasarNIvel("2"); contBN = 0; }

                break;
            case 2:
                String[] nivel2 = getResources().getStringArray(R.array.nivel_2);
                String q0s = nivel2[preguntaAct];
                General_rsc(q0s);
                //Log.i("TAG","Valor2: "+contBN +" - "+ preguntaAct + " - " + nivel2.length);

                contBN = preguntaAct + 1;
                if (contBN == nivel2.length){ niveles.setText("3"); PasarNIvel("3"); contBN = 0; }

                break;
            case 3:
                String[] nivel3 = getResources().getStringArray(R.array.nivel_3);
                String q03 = nivel3[preguntaAct];
                General_rsc(q03);
                //Log.i("TAG","Valor3: "+contBN +" - "+ preguntaAct);

                contBN = preguntaAct + 1;
                if (contBN == nivel3.length){ niveles.setText("4"); PasarNIvel("4"); contBN = 0; }

                break;
            case 4:
                String[] nivel4 = getResources().getStringArray(R.array.nivel_4);
                String q04 = nivel4[preguntaAct];
                General_rsc(q04);
               // Log.i("TAG","Valor3: "+contBN +" - "+ preguntaAct);

                contBN = preguntaAct + 1;
                if (contBN == nivel4.length){ niveles.setText("1"); PasarNIvel("1"); contBN = 0; }

                break;
            default:
                Tienda();
                break;
        }

    }

    private void General_rsc(String q0) {
                //String q0 = all_questions[preguntaAct];
                parts = q0.split(";");
                tvPantalla.setText(parts[0]);
                for (int i = 0; i < ids_answers.length; i++) {
                    RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
                    String answ = parts[i + 1];
                    if (answ.charAt(0) == '*') {
                        Correctac_answer = i;
                        answ = answ.substring(1);
                    }
                    rb.setText(answ);
                }

//        String[] all_questions = getResources().getStringArray(R.array.nivel_1);
//        String q0 = all_questions[preguntaAct];
//        parts = q0.split(";");
//        tvPantalla.setText(parts[0]);
//        for (int i = 0; i < ids_answers.length; i++) {
//            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
//            String answ = parts[i + 1];
//            if (answ.charAt(0) == '*') {
//                Correctac_answer = i;
//                answ = answ.substring(1);
//            }
//            rb.setText(answ);
//        }


    }

    private void AsignarSonidoBTN() {
        findViewById(R.id.a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
            }
        });
        findViewById(R.id.b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
            }
        });
        findViewById(R.id.c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
            }
        });
        findViewById(R.id.d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
            }
        });
    }

    public void myRadioGrup(View v){

        int IdRadio = radioGrup.getCheckedRadioButtonId();
        IdSrc = findViewById(IdRadio);

        //String tv = tvPantalla.getText().toString();

        //Toast.makeText(MainActivity.this,"" + tv + IdSrc.getText(), Toast.LENGTH_LONG).show();

        //radioGrup.clearCheck(); /// para demarcar los radio butom
        //Juego(mtds.poscion); //reiniciar juego


    }

    public void OcultarCorazon(int dat){
        if(dat == 1){
            findViewById(R.id.live1).setVisibility(dview.INVISIBLE);
        }
        if(dat == 2){
            findViewById(R.id.live2).setVisibility(dview.INVISIBLE);
        }
        if(dat == 3){
            findViewById(R.id.live3).setVisibility(dview.INVISIBLE);
            CeroCorazon();
        }
        if(dat == 5){
            findViewById(R.id.live1).setVisibility(dview.VISIBLE);
            findViewById(R.id.live2).setVisibility(dview.VISIBLE);
            findViewById(R.id.live3).setVisibility(dview.VISIBLE);
            contMAL = 0;
        }


    }

    public  void Tienda(){
        mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
        mydialog.setContentView(R.layout.tienda);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();
        pauseTimer();
        int cop100 = Integer.parseInt(monedad.getText().toString());
        mydialog.findViewById(R.id.fbb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mtd.musicaMoneda(mydialog.getContext());
                //mMoneda(puntosMoneda,500);
               // preg( pred,FRACE,img,uno,dos,tres);
                mydialog.dismiss();
                General_pregunta();
                resetTimer();
                mtd.SonidoGeneral(v.getContext(),R.raw.boop);
            }
        });

        mydialog.findViewById(R.id.tienda100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
               // int cop100 = Integer.parseInt(monedad.getText().toString());
                if(cop100 >= 100){
                    if (findViewById(R.id.id_estrella1).getVisibility() == view.INVISIBLE){
                            findViewById(R.id.id_estrella1).setVisibility(view.VISIBLE);
                            int copV = cop100 - 100;
                            monedad.setText(String.valueOf(copV));
                            b =1;
                    }else{
                        if(findViewById(R.id.id_estrella2).getVisibility() == view.INVISIBLE){
                            findViewById(R.id.id_estrella2).setVisibility(view.VISIBLE);
                            int copV = cop100 - 100;
                            monedad.setText(String.valueOf(copV));
                            b =1;
                        }else{
                            if(findViewById(R.id.id_estrella3).getVisibility() == view.INVISIBLE){
                                findViewById(R.id.id_estrella3).setVisibility(view.VISIBLE);
                                int copV = cop100 - 100;
                                monedad.setText(String.valueOf(copV));
                                b =1;
                            }else{
                                Toast.makeText(getApplicationContext(),"Ya tienes las estrellas suficientes", Toast.LENGTH_LONG).show();
                            } } }

                    resetTimer();
                    mydialog.dismiss();

                }else {
                    MonedaInsuficiente();
                }
                //mydialog.dismiss();
            }
        });
        mydialog.findViewById(R.id.tienda300).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
                int cop300 = Integer.parseInt(monedad.getText().toString());
                if(cop300 >= 300){
                    if(findViewById(R.id.id_estrella1).getVisibility() == view.INVISIBLE && findViewById(R.id.id_estrella2).getVisibility() == view.INVISIBLE){
                        findViewById(R.id.id_estrella1).setVisibility(view.VISIBLE);
                        findViewById(R.id.id_estrella2).setVisibility(view.VISIBLE);
                        int copV = cop300 - 300;
                        monedad.setText(String.valueOf(copV));
                        b =1;
                    }else {
                        if(findViewById(R.id.id_estrella2).getVisibility() == view.INVISIBLE && findViewById(R.id.id_estrella3).getVisibility() == view.INVISIBLE){
                            findViewById(R.id.id_estrella2).setVisibility(view.VISIBLE);
                            findViewById(R.id.id_estrella3).setVisibility(view.VISIBLE);
                            int copV = cop300 - 300;
                            monedad.setText(String.valueOf(copV));
                            b =1;
                        }else {
                            if(findViewById(R.id.id_estrella1).getVisibility() == view.INVISIBLE && findViewById(R.id.id_estrella3).getVisibility() == view.INVISIBLE){
                                findViewById(R.id.id_estrella1).setVisibility(view.VISIBLE);
                                findViewById(R.id.id_estrella3).setVisibility(view.VISIBLE);
                                int copV = cop300 - 300;
                                monedad.setText(String.valueOf(copV));
                                b =1;
                            }else {
                                Toast.makeText(getApplicationContext(),"Ya tienes las estrellas suficientes", Toast.LENGTH_LONG).show();
                     } } }
                    resetTimer();
                    mydialog.dismiss();
                }else {
                    MonedaInsuficiente();
                }
            }
        });
        mydialog.findViewById(R.id.tienda500).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
                int cop500 = Integer.parseInt(monedad.getText().toString());
                if(cop500 >= 500){
                    if(findViewById(R.id.id_estrella3).getVisibility() == view.INVISIBLE){
                        int copV = cop500 - 500;
                        monedad.setText(String.valueOf(copV));
                        findViewById(R.id.id_estrella1).setVisibility(view.VISIBLE);
                        findViewById(R.id.id_estrella2).setVisibility(view.VISIBLE);
                        findViewById(R.id.id_estrella3).setVisibility(view.VISIBLE);
                        resetTimer();
                        mydialog.dismiss();
                        b =1;
                    }else {
                        Toast.makeText(getApplicationContext(),"Ya tienes las estrellas suficientes", Toast.LENGTH_LONG).show();
                        resetTimer();
                    }

                }else {
                    MonedaInsuficiente();
                }
                //mydialog.dismiss();
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mydialog.dismiss();
//
//            }
//        },2000);


    }

    public  void Nivel(){
        mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
        mydialog.setContentView(R.layout.niveles);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();

        mydialog.findViewById(R.id.fbb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mtd.musicaMoneda(mydialog.getContext());
                //mMoneda(puntosMoneda,500);
                // preg( pred,FRACE,img,uno,dos,tres);
                General_pregunta();
                resetTimer();
                mydialog.dismiss();
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
            }
        });

        pauseTimer();

        mydialog.findViewById(R.id.id_monedad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        CambiarIconosDeNivel();


    }

    private void CambiarIconosDeNivel() {
        mMonedas = Integer.parseInt(monedad.getText().toString());
       /// Toast.makeText(Juego.this,"P: "+mMonedas, Toast.LENGTH_LONG).show();
        SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
        int fmMonedas = Integer.parseInt(preferences.getString("BDnivel","1"));

        if(mMonedas >=1){ mydialog.findViewById(R.id.nivel1).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 2){ mydialog.findViewById(R.id.nivel2).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 3){ mydialog.findViewById(R.id.nivel3).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 4){ mydialog.findViewById(R.id.nivel4).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 5){ mydialog.findViewById(R.id.nivel5).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 6){ mydialog.findViewById(R.id.nivel6).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 7){ mydialog.findViewById(R.id.nivel7).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 8){ mydialog.findViewById(R.id.nivel8).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 9){ mydialog.findViewById(R.id.nivel9).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 10){ mydialog.findViewById(R.id.nivel10).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 11){ mydialog.findViewById(R.id.nivel11).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 12){ mydialog.findViewById(R.id.nivel12).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 13){ mydialog.findViewById(R.id.nivel13).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 14){ mydialog.findViewById(R.id.nivel14).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 15){ mydialog.findViewById(R.id.nivel15).setBackgroundResource(R.drawable.btn_pregunta);
        }
        if(fmMonedas >= 16){ mydialog.findViewById(R.id.nivel16).setBackgroundResource(R.drawable.btn_pregunta);
        }
    }

    public void Comodin_Regalo(){
        mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
       //Log.i("TAG","Valor: "+ Correctac_answer );

        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String answ = parts[i + 1];
            if (answ.charAt(0) == '*') {
                Correctac_answer = i;
                 answ = answ.substring(1);
               // Log.i("TAG","Valor: "+ answ );


                mydialog.setContentView(R.layout.comoding);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mydialog.setCanceledOnTouchOutside(false);
                mydialog.setCancelable(false);
                mydialog.show();

                ///diaP,diaR, diaC;
               /// diaP = mydialog.findViewById(R.id.dia_preg);
                diaR = mydialog.findViewById(R.id.ida_rep);
                diaC = mydialog.findViewById(R.id.dia_cerrar);

                diaC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mtd.SonidoGeneral(Juego.this,R.raw.boop);
                        int cmo = Integer.parseInt(monedad.getText().toString());
                       int como = cmo - 50;
                        monedad.setText(String.valueOf(como));
                        mydialog.dismiss();
                    }
                });

//                diaP.setText(parts[0]); //la pregunta
                diaR.setText(answ); //la repuesta



            }
            rb.setText(answ);
            //Log.i("TAG","Valor: "+ answ );
        }



    }

    public void MonedaInsuficiente(){
        mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
                pauseTimer();
                mydialog.setContentView(R.layout.moneda_insuficiente);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mydialog.setCanceledOnTouchOutside(false);
                mydialog.setCancelable(false);
                mydialog.show();

                diaC = mydialog.findViewById(R.id.dia_cerrar);

                diaC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mtd.SonidoGeneral(Juego.this,R.raw.boop);
                        mydialog.dismiss();
                        resetTimer();
                    }
                });


    }


    public  void playgames(){

        mydialog.setContentView(R.layout.pay);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();


        mydialog.findViewById(R.id.iniciarJuego).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
                startTimer();
                General_pregunta();
                LeerPref();
                //FirebaseSubir();

            }
        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mydialog.dismiss();
//
//            }
//        },2000);


    }

    private void FirebaseSubir() {
        SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
        String  dni = preferences.getString("dni","0");
        String nombre = preferences.getString("nombre","0");
        String apellido = preferences.getString("apellido","0");
        String foto = preferences.getString("foto","0");
        String edad = preferences.getString("edad","0");
        String nivel = preferences.getString("BDnivel","1");
        String puntos = preferences.getString("BDmoneda","1");
        String fecha = preferences.getString("fecha","0");


        PooUser p = new PooUser();
        p.setDni(dni);
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setFoto(foto);
        p.setEdad(edad);
        p.setNivel(nivel);
        p.setPuntos(puntos);
        p.setFecha(fecha);
        mReference.child("UsuarioPinos").child(p.getDni()).setValue(p);
    }

    //    este metodo se iniciara solo la primera vez que abran la app
    public  void registro_user(){
        PooUser p = new PooUser();
        mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
        mydialog.setContentView(R.layout.registro_user);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();


        mydialog.findViewById(R.id.id_btnGuardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nombre = (EditText) mydialog.findViewById(R.id.id_nombre);
                EditText apellido = (EditText) mydialog.findViewById(R.id.id_apellido);
                EditText edad = (EditText) mydialog.findViewById(R.id.id_edad);
                // Button btn = (Button) mydialog.findViewById(R.id.id_btnGuardar);

                long ahora = System.currentTimeMillis();
                Date fecha = new Date(ahora);
                DateFormat df = new SimpleDateFormat("d/M/yy");

                String mfecha = df.format(fecha).replace("/","");

                String tnombre = nombre.getText().toString().toUpperCase();
                String tapellido = apellido.getText().toString().toUpperCase();
                String tedad = edad.getText().toString();
                String d = String.valueOf(tnombre.charAt(0));
                String n = String.valueOf(tapellido.charAt(0));
                String dni = d+n+tedad;
                String img = d+n;

                p.setDni(dni+"_"+mfecha);
                p.setNombre(tnombre);
                p.setApellido(tapellido);
                p.setFoto(img);
                p.setEdad(tedad);
                p.setNivel("0");
                p.setPuntos("0");
                p.setFecha("28-06-2021");
                mReference.child("UsuarioPinos").child(p.getDni()).setValue(p);


                SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("dni",dni+"_"+mfecha);
                editor.putString("nombre",tnombre);
                editor.putString("apellido",tapellido);
                editor.putString("foto",img);
                editor.putString("edad",tedad);
//                editor.putString("BDnivel",Vnivel);
//                editor.putString("BDmoneda",Vmonedas);
//                editor.putString("fecha",Vnivel);
                editor.commit();


                mtd.SonidoGeneral(Juego.this,R.raw.boop);
                startTimer();
                General_pregunta();
                mydialog.dismiss();

            }
        });


    }

    public  void listado(){
        mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
        mydialog.setContentView(R.layout.list_usuario);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();
        pauseTimer();

        LinearLayoutManager layout_manager = new LinearLayoutManager(getApplicationContext());
        layout_manager.setReverseLayout(true);
        layout_manager.setStackFromEnd(true);


        mydialog.findViewById(R.id.idcerrarlista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
            }
        });

        SharedPreferences preferences = getSharedPreferences("BDPinos", Context.MODE_PRIVATE);
        String  pnombre = preferences.getString("nombre","Nombre");
        String papellido = preferences.getString("apellido","Apellido");
        TextView idtitulo = (TextView) mydialog.findViewById(R.id.idtitulo);
        idtitulo.setText(pnombre+" "+papellido);

        RecyclerView rvs;
        DatabaseReference database;
        MyAdapter myAdapter;
        ArrayList<PooUser> list;
        rvs = (RecyclerView) mydialog.findViewById(R.id.id_rv);

//        database = FirebaseDatabase.getInstance().getReference("UsuarioPinos");
//        database.addListenerForSingleValueEvent(valueEvenLis);


       // database = FirebaseDatabase.getInstance().getReference("UsuarioPinos");
        Query query = FirebaseDatabase.getInstance().getReference("UsuarioPinos");

        rvs.setHasFixedSize(true);
        rvs.setLayoutManager(new LinearLayoutManager(this));
        //rvs.setReverseLayout(true);
        //rvs.setStackFromEnd(true);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        rvs.setAdapter(myAdapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PooUser ps = dataSnapshot.getValue(PooUser.class);
                    list.add(ps);

                }
                myAdapter.notifyDataSetChanged();
            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    public  void UnaStrellaMeno(){
        Mtodos sdf = new Mtodos();
        sdf.SonidoGeneral(Juego.this,R.raw.error);
        mydialog.setContentView(R.layout.estrella_meno);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();
       //b = 1;

        switch (b){
            case 1:
                //Toast.makeText(Juego.this,"1", Toast.LENGTH_LONG).show();
                findViewById(R.id.id_estrella1).setVisibility(dview.INVISIBLE);
               // findViewById(R.id.id_estrella3).setBackgroundColor(R.color.my_color);
                b = 2;
                break;
            case 2:
                //Toast.makeText(Juego.this,"3", Toast.LENGTH_LONG).show();
                findViewById(R.id.id_estrella2).setVisibility(dview.INVISIBLE);
                b = 3;
                break;
            case 3:
                //Toast.makeText(Juego.this,"2", Toast.LENGTH_LONG).show();
                findViewById(R.id.id_estrella3).setVisibility(dview.INVISIBLE);
                b = 0;
                break;
            default:
                break;
        }

        mydialog.findViewById(R.id.id_estrella_meno).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
                General_pregunta();
                resetTimer();


            }
        });


        pauseTimer();
       // mCountDownTimer.cancel();
       // mTimerRunning = false;

        mydialog.findViewById(R.id.id_cerrarEstrella).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
                General_pregunta();
                resetTimer();
                //mtd.SonidoGeneral(Juego.this,R.raw.boop);


            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mydialog.dismiss();
//                General_pregunta();
//                resetTimer();
//
//                //playgame();
////                overridePendingTransition(0, 0);
////                //startActivity(getIntent());
////                overridePendingTransition(0, 0);
//                mtd.SonidoGeneral(Juego.this,R.raw.boop);
//            }
//        },6000);

    }

    public void BTN_Niveles(){
        mMonedas = Integer.parseInt(monedad.getText().toString());
       // ImageButton nivel = (ImageButton) findViewById(R.id.nivel1);
        ImageButton nivel = (ImageButton) findViewById(R.id.nivel1);
        Toast.makeText(Juego.this,"P: "+mMonedas, Toast.LENGTH_LONG).show();

        if(mMonedas == 100){
            Toast.makeText(Juego.this,"Pasate de nievel", Toast.LENGTH_LONG).show();
           // nivel.setBackground(ActivityCompat.getDrawable(getApplicationContext(), R.drawable.bg_a));
            //nivel.setBackground(ActivityCompat.getDrawable(getApplicationContext(), R.drawable.bg_a));
            nivel.setBackgroundResource(R.drawable.btn_pregunta);
        }
    }

    public  void PasarNIvel(String vvb){
        mtd.SonidoGeneral(Juego.this,R.raw.dialogo);
        mydialog.setContentView(R.layout.nuevo_nivel);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();


        mydialog.findViewById(R.id.id_nuevoNivel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
                resetTimer();
                General_pregunta();
                //LeerPref();

            }
        });

        preguntaAct = 0;
        pauseTimer();

        TextView numeroNiv = (TextView) mydialog.findViewById(R.id.NumeroNIvel);
        numeroNiv.setText(vvb);


    }

    public  void CeroCorazon(){
        Mtodos error = new Mtodos();
        error.SonidoGeneral(Juego.this,R.raw.error);
        mydialog.setContentView(R.layout.cero_corazones);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.setCancelable(false);
        mydialog.show();


        mydialog.findViewById(R.id.cerrarx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.SonidoGeneral(Juego.this,R.raw.boop);
                resetTimer();
                General_pregunta();
                contMAL = 0;
                mydialog.dismiss();
                OcultarCorazon(5);
            }
        });
        TextView tvs = (TextView) findViewById(R.id.idNivel);
        int sds = Integer.parseInt(tvs.getText().toString());
        String ds= String.valueOf(sds -1);
        if(ds.equals("0")){
            tvs.setText("1");
            monedad.setText("1");
        }else {
            tvs.setText(ds);
        }

        preguntaAct = 0;
        pauseTimer();




    }

    public String generateKey(){
        return UUID.randomUUID().toString();
    }

    public Data guarNOti(String titulo, String detalle, int idNot){
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("idNot",idNot).build();
    }

    @Override
    protected void onStop() {
        //Toast.makeText(Juego.this,"Stooo", Toast.LENGTH_LONG).show();
        mtd.SonidoStop();
        super.onStop();
    }









}


