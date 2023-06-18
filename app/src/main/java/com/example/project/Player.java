package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player{
    int WIDTH, HEIGHT;

    int life = 3;
    int dmg = 1;
//    int power = 2; // 필살기 사용 가능 횟수
    float x, y;
    int radius; //지름이 아닌 반지름임

    public Player(int d, int width, int height, int x, int y){
        WIDTH = width;
        HEIGHT = height;

        radius = d;

        this.x = x;
        this.y = y;
    }

    public void move(float mx, float my){
        this.x += mx;
        this.y += my;

        if(x < 0)
            x = 0;
        if(y < 0)
            y = 0;
        if(x > WIDTH)
            x = WIDTH;
        if(y > HEIGHT)
            y = HEIGHT;
    }

    public float getPlayerX(){
        return x;
    }

    public float getPlayerY(){
        return y;
    }

    public void paintPlayer(Canvas g){
        Paint paint = new Paint();

        paint.setColor(Color.rgb(0,0,255));
        g.drawCircle(x, y, radius, paint);
    }

    // 플레이어와 오브젝트가 부딪쳤는지 체크
    public boolean isTouched(float objectX, float objectY, int objectR){
        //두 원 사이의 거리
        double pointLen = Math.sqrt(
                Math.pow(getPlayerX() - objectX, 2) + Math.pow(getPlayerY() - objectY, 2)
        );
        //두 원의 반지름 합계
        int twoRadius = (objectR) + (radius);
    
        //충돌 감지
        if(pointLen <= twoRadius)
            return true;

        return false;
    }
}
