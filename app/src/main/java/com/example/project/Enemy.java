package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.widget.Toast;

public class Enemy implements Object{
    float x, y;
    int xInc = 1, yInc = 1;
    int diameter;
    int WIDTH = 1280, HEIGHT = 1920;

    int speed = 5;
    float directionX, directionY;

    public Enemy(int d, int width, int height, Player target){
        this.diameter = d;
        this.WIDTH = width;
        this.HEIGHT = height;

        int randSpawn = (int) (Math.random() * 4);

        //스폰 위치
        if(randSpawn == 0){
            x = (int) (Math.random() * (WIDTH - d) + 3);
            y = 0;
        }else if(randSpawn == 1){
            x = (int) (Math.random() * (WIDTH - d) + 3);
            y = HEIGHT;
        }else if(randSpawn == 2){
            x = 0;
            y = (int) (Math.random() * (HEIGHT - d) + 3);
        }else if(randSpawn == 3){
            x = WIDTH;
            y = (int) (Math.random() * (HEIGHT - d) + 3);
        }

        float targetX = target.getPlayerX();
        float targetY = target.getPlayerY();

        float deltaX = targetX - this.x;
        float deltaY = targetY - this.y;

        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        this.directionX = deltaX / distance;
        this.directionY = deltaY / distance;

        xInc = yInc = 5;
    }

    @Override
    public void paintObject(Canvas g, Player target){
        Paint paint = new Paint();

//        if (x < 0 || x > (WIDTH - diameter))
//            xInc = -xInc;
//        if(y < 0 || y > ((HEIGHT) - diameter)){
//            remove(g);
//        }

//        float targetX = target.getPlayerX();
//        float targetY = target.getPlayerY();

//        if(targetX - this.x > 0){
//            x += xInc;
//        }else{
//            x -= xInc;
//        }
//
//        if(targetY - this.y > 0){
//            y += yInc;
//        }else{
//            y -= yInc;
//        }

        this.x += this.directionX * this.speed;
        this.y += this.directionY * this.speed;

        if(this.x > WIDTH || this.x < 0){
            clearObject(g);
            return;
        }
        if(this.y > HEIGHT || this.y < 0) {
            clearObject(g);
            return;
        }


        if(encounter(target))
            System.out.println("Game Over");

//        x += xInc;
//        y += yInc;

        paint.setColor(Color.rgb(255,0,0));
        g.drawCircle(x, y, diameter, paint);
    }

    @Override
    public boolean encounter(Player player){
        if(player.isTouched(this.x, this.y))
            return true;

        return false;
    }

    @Override
    public void clearObject(Canvas g){
        Paint paint = new Paint();
        Xfermode xmode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        paint.setXfermode(xmode);
        g.drawCircle(x, y, diameter, paint);
    }
}

