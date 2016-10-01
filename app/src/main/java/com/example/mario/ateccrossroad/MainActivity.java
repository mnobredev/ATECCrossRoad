package com.example.mario.ateccrossroad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

   Button btPlay;
    ImageButton btSangui;
    ImageButton btSilva;
    ImageButton btDario;
    int escolha=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btPlay = (Button) findViewById(R.id.btPlay);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Mainscreen.class);
                intent.putExtra("Escolha",escolha);
                startActivity(intent);

            }
        });

        btSangui = (ImageButton) findViewById(R.id.sangui);
        btSangui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolha=1;
            }
        });

        btSilva = (ImageButton) findViewById(R.id.nsilva);
        btSilva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolha=2;
            }
        });

        btDario = (ImageButton) findViewById(R.id.dario);
        btDario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolha=3;
            }
        });

    }
}