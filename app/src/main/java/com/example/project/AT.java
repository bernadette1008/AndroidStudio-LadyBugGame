package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class AT implements Object{
    float x, y;
    int radius;

    public AT(Player player){
        this.radius = player.radius*2;
        this.x = player.getPlayerX();
        this.y = player.getPlayerY();
    }

    @Override
    public void paintObject(Canvas g){
        Paint paint = new Paint();

        paint.setColor(Color.rgb(178,235,244));
        g.drawCircle(x, y, radius, paint);
    }

    public void moveObject(Player player){
        this.x = player.getPlayerX();
        this.y = player.getPlayerY();
    }

    public boolean attackEnemy(float objectX, float objectY, int objectR){
        //두 원 사이의 거리
        double pointLen = Math.sqrt(
                Math.pow(this.x - objectX, 2) + Math.pow(this.y - objectY, 2)
        );
        //두 원의 반지름 합계
        int twoRadius = (objectR) + (radius);

        //충돌 감지
        if(pointLen <= twoRadius)
            return true;

        return false;
    }
}
