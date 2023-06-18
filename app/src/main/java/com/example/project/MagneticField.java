package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MagneticField implements Object{
    float x, y;
    int radius;

    Long mfElapsedTime;
    Long mfStartTime;

    public MagneticField(Player player, Long mfElapsedTime, Long mfStartTime){
        this.x = player.getPlayerX();
        this.y = player.getPlayerY();
        this.radius = player.radius*10;
        this.mfElapsedTime = mfElapsedTime;
        this.mfStartTime = mfStartTime;
    }

    @Override
    public void paintObject(Canvas g){
        Paint paint = new Paint();

        paint.setColor(Color.argb(100,209,178,255));
        g.drawCircle(x, y, radius, paint);
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
