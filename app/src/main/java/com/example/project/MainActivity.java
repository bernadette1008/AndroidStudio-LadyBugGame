package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MySurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        surfaceView = new MySurfaceView(this);
//        setContentView(surfaceView);
        Button startButton = (Button) findViewById(R.id.start);
        Button scoreButton = (Button) findViewById(R.id.score);

        startButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                startActivity(intent);
            }
        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Score.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

}