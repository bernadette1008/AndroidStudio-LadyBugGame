package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Game extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}
