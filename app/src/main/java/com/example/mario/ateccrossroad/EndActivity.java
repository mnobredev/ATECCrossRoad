package com.example.mario.ateccrossroad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    Button btExit, btRetry;
    TextView tvScore;
    int lastPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        btExit = (Button) findViewById(R.id.btExit);
        btRetry = (Button) findViewById(R.id.btRetry);
        tvScore = (TextView) findViewById(R.id.tvScore);
        String score = null;
        Intent intent = getIntent();
        score  = intent.getStringExtra("score");
        lastPlayed = 0;
        lastPlayed  = intent.getIntExtra("Escolha", lastPlayed);
        tvScore.setText("My score"+"\n"+score);

        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, Mainscreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("Escolha",lastPlayed);
                startActivity(intent);

            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}