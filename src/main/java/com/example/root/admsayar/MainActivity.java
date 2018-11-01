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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private static final android.util.Log Log = null;
    // sensoreventlistener sensorden gelen değişimleri anlık alınmasını sağlar.
int adım_sayısı=0;
boolean saymayadevam=false,
        hedef=false;   int durm=0;
float son_x, ilk_y,son_z;
float ilk_x,son_y,ilk_z;
    int gelen_hedef;
Sensor accelerometer;
SensorManager sm;
TextView acceleration,text;
EditText hedefal;
Button basla,bitir,hedef_bel,gecmisi_göster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //yy=i;
        //int  gelmis_sayi=gelen_mesaj.getInt("yoldakiveri");
        //int deger = getIntent().getExtras().getInt("sayi");
        //y=deger;
        hedefal=(EditText)findViewById(R.id.hedefal);
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
                durm=1;
                adım_sayısı=0;
                break;
            case R.id.bitir:
                saymayadevam=false;
                text.setText(" toplam atılan adım sayısı:\n"+adım_sayısı);
                break;
            case R.id.hedef:
                durm=2;
              gelen_hedef=Integer.parseInt(hedefal.getText().toString());
               // Intent intent=new Intent(MainActivity.this,Hedef.class);
                //startActivity(intent);
                break;
            case R.id.geçmişi_göster:
                break;
        }



    } //verilri kayıt edelim
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter =
                                  new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    /// veri okuyalım
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
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


                   if(durm==1  ) {
                       text.setText("atılan adım sayısı:\n" + adım_sayısı);
                   }
                   if(durm==2)
                    text.setText("son :\n" +( gelen_hedef - adım_sayısı)+" adım kaldı");
                    //Date currentTime = Calendar.getInstance().getTime();

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
