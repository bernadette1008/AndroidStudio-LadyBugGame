package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.widget.Toast;

public class Enemy implements Object{
    float x, y;
    int xInc = 1, yInc = 1;
    int diameter;
    int WIDTH = 1280, HEIGHT = 1920;

    int hp = 1 * Game.stage;

    public Enemy(int d, int width, int height){
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

        float targetX = target.getPlayerX();
        float targetY = target.getPlayerY();

        if(targetX - this.x > 0){
            x += xInc;
        }else{
            x -= xInc;
        }

        if(targetY - this.y > 0){
            y += yInc;
        }else{
            y -= yInc;
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
}

