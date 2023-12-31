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

    int radius;
    int WIDTH = 1280, HEIGHT = 1920;

    int speed = 5;
    float directionX, directionY;

    public Enemy(int d, int width, int height, Player target){
        this.radius = d;
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
    }

    @Override
    public void paintObject(Canvas g, Player target){
        Paint paint = new Paint();

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

        paint.setColor(Color.rgb(255,0,0));
        g.drawCircle(x, y, radius, paint);
    }

    @Override
    public boolean encounter(Player player){
        if(player.isTouched(this.x, this.y, this.radius))
            return true;

        return false;
    }

    @Override
    public boolean attackedByAT(AT at){
        if(at.attackEnemy(this.x, this.y, radius))
            return true;

        return false;
    }

    @Override
    public boolean attackedByMF(MagneticField mf){
        if(mf.attackEnemy(this.x, this.y, radius))
            return true;

        return false;
    }

    @Override
    public void clearObject(Canvas g){
        Paint paint = new Paint();
        Xfermode xmode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        paint.setXfermode(xmode);
        g.drawCircle(x, y, radius, paint);
    }
}

