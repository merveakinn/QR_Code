package com.merveakin.QRKod;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AnaEkran2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran2);
    }

    public void barkodaGit(View view){
        Intent intent = new Intent(AnaEkran2.this,MainActivity.class);
        startActivity(intent);
        finish();

    }


    public void urunGirisCikis(View view){
        Intent intent = new Intent(AnaEkran2.this,MainActivity.class);
        startActivity(intent);
    }


/*
    public void denemee(View view){
        Intent intent = new Intent(AnaEkran2.this,connectionmySQL.class);
        startActivity(intent);}
*/

}