package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Item implements Object{
    int x, y;
    int diameter;

    @Override
    public void paintObject(Canvas g, Player target){
        Paint paint = new Paint();

        paint.setColor(Color.rgb(255,255,255));
        g.drawCircle(x, y, diameter, paint);
    }
}
