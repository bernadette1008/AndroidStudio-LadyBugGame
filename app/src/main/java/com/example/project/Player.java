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
    int diameter;

    public Player(int d, int width, int height, int x, int y){
        WIDTH = width;
        HEIGHT = height;

        diameter = d;

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
        g.drawCircle(x, y, diameter, paint);
    }

    // 플레이어와 오브젝트가 부딪쳤는지 체크
    public boolean isTouched(float objectX, float objectY){
        float pX1 = this.x + diameter;
        float pX2 = this.x - diameter;

        float pY1 = this.y + diameter;
        float pY2 = this.y - diameter;

//        System.out.println("PX1 : " + pX1 + " PX2 : " + pX2);
//        System.out.println("PY1 : " + pY1 + " PY2 : " + pY2);

        if(objectX >= pX2 && objectX <= pX1){
            if(objectY >= pY2 && objectY <= pY1)
                return true;
        }

        return false;
    }


}
