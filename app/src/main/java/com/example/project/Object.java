package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;

public interface Object {
    // 움직일 수 있는 판의 사이즈
    int WIDTH = 0, HEIGHT = 0;
    
    // 오브젝트의 사이즈 (기본 값 20)
    int radius = 20;
    
    // 오브젝트의 현재 위치
    float x = 0, y = 0;
    
    // 오브젝트의 이동 속도
    int xInt = 0, yInt = 0;

    // 오브젝트 그리기
    default void paintObject(Canvas g, Player target){
        Paint paint = new Paint();

        paint.setColor(Color.rgb(255,255,255));
        g.drawCircle(x, y, radius, paint);
    }

    default void paintObject(Canvas g){
        Paint paint = new Paint();

        paint.setColor(Color.rgb(255,255,255));
        g.drawCircle(x, y, radius, paint);
    }

    default void clearObject(Canvas g){
        Paint paint = new Paint();
        Xfermode xmode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        paint.setXfermode(xmode);
        g.drawCircle(x, y, radius, paint);
    }

    // 플레이어와 부딪쳤는지 체크
    default boolean encounter(Player player){
        if(player.isTouched(this.x, this.y, radius))
            return true;

        return false;
    }

    default boolean attackedByAT(AT at){
        if(at.attackEnemy(this.x, this.y, radius))
            return true;

        return false;
    }

    default boolean attackedByMF(MagneticField mf){
        if(mf.attackEnemy(this.x, this.y, radius))
            return true;

        return false;
    }

    default boolean attackedByMF(){

        return false;
    }
}
