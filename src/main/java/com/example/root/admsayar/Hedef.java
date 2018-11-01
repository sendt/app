package com.example.root.admsayar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

public class Hedef extends AppCompatActivity {
        TextView tv;
        EditText edtxt;
        Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hedef);
        tv=(TextView)findViewById(R.id.textView2);
        edtxt=(EditText)findViewById(R.id.editText);
        btn=(Button)findViewById(R.id.button);

    }
    public  void tiklan(View v){
        if(v.getId()==R.id.button);
        Intent intent=new Intent(Hedef.this,MainActivity.class);
        intent.putExtra("sayi",(int)(Integer.parseInt(edtxt.getText().toString())));
        startActivity(intent);
    }
}
