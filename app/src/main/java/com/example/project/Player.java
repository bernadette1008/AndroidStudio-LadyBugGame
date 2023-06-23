package com.example.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Player{
    int WIDTH, HEIGHT;

    int life = 3;
    int dmg = 1;
//    int power = 2; // 필살기 사용 가능 횟수
    float x, y;
    int radius; //지름이 아닌 반지름임
    Bitmap playerIcon;

    private float rotationAngle = 0.0f;

    public void pRotate(float angle){
        rotationAngle = angle;

        if(rotationAngle < 0){
            rotationAngle += 360;
        } else if(rotationAngle >= 360){
            rotationAngle -= 360;
        }
    }

    public Player(int d, int width, int height, int x, int y, Bitmap playerIcon){
        WIDTH = width;
        HEIGHT = height;

        radius = d;

        this.playerIcon = playerIcon;

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
        paint.setColor(Color.argb(0,0,0,255));

        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAngle, x, y);

        g.drawCircle(x, y, radius, paint);
        Bitmap resizePlayerIcon = Bitmap.createScaledBitmap(playerIcon, radius*3, radius*3, false);

        Bitmap rotatedPlayerIcon = Bitmap.createBitmap(resizePlayerIcon, 0, 0, resizePlayerIcon.getWidth(), resizePlayerIcon.getHeight(), matrix, true);
        g.drawBitmap(rotatedPlayerIcon, (float) (x-(radius*1.5)), (float) (y-(radius*1.5)), null);
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
