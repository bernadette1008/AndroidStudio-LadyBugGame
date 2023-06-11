package com.example.project;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {
    static int stage = 1;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}
