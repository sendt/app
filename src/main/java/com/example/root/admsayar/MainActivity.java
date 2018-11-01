package com.example.root.admsayar;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

             // sensoreventlistener sensorden gelen değişimleri anlık alınmasını sağlar.
int adım_sayısı=0;
boolean saymayadevam=false,
        hedef=false;
float son_x, ilk_y,son_z;
float ilk_x,son_y,ilk_z;

Sensor accelerometer;
SensorManager sm;
TextView acceleration,text;
Button basla,bitir,hedef_bel,gecmisi_göster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //yy=i;
        //int  gelmis_sayi=gelen_mesaj.getInt("yoldakiveri");

        text=(TextView)findViewById(R.id.textView);
        basla=(Button)findViewById(R.id.basla);
        bitir=(Button)findViewById(R.id.bitir);
        hedef_bel=(Button)findViewById(R.id.hedef);
        gecmisi_göster=(Button)findViewById(R.id.geçmişi_göster);
        sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        //üstteki satırda sensörün duyarlılığı(hassaslığı değştilebilir)
        acceleration=(TextView)findViewById(R.id.acceleration);

    }

    public  void tiklanma(View v){
        switch (v.getId()){
            case R.id.basla:
                adım_sayısı=0;
                saymayadevam=true;
                adım_sayısı=0;
                break;
            case R.id.bitir:
                saymayadevam=false;
                text.setText(" toplam atılan adım sayısı:\n"+adım_sayısı);
                break;
            case R.id.hedef:
                hedef=true;
                Intent intent=new Intent(MainActivity.this,Hedef.class);
                startActivity(intent);

                break;
            case R.id.geçmişi_göster:
                break;
        }


       /* if(v.getId()==R.id.hedef_belirle){
        //buraya alert dialog yazılacak
        text.setText("hedef");
        Intent intent=new Intent(MainActivity.this,Hedef.class);
        startActivity(intent);

    }
        if(v.getId()==R.id.basla){
            adım_sayısı=0;
            saymayadevam=true;
                adım_sayısı=0;
            }
        if(v.getId()==R.id.bitir){
            saymayadevam=false;
            text.setText(" toplam atılan adım sayısı:\n"+adım_sayısı);
        }*/


    }

    @Override
    public void onSensorChanged(SensorEvent arg0) {
            if(arg0.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                float[] values =arg0.values;
                ilk_x=values[0];
                ilk_y=values[1];
                ilk_z=values[2];
                acceleration.setText("X : "+ilk_x+"\nY : "+ilk_y+"\nZ :"+ilk_z);
                if(Math.abs(son_y-ilk_y)>=1.7){
                    adım_sayısı++;
                    son_y=ilk_y;
                   if(saymayadevam && !hedef ) {
                       text.setText("atılan adım sayısı:\n" + adım_sayısı);
                   }
                    else if(hedef){
                       int deger = getIntent().getExtras().getInt("sayi");
                        int u=deger-adım_sayısı;
                        text.setText("son "+u+" adım");
                    }
                }

            }
            // vibrator.vibrate(500); ile titreşim ayarlanabilir..
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
