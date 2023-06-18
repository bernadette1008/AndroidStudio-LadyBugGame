package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;

public class Item implements Object{
    int x, y;
    int radius;

    int xInc, yInc;

    int WIDTH, HEIGHT;
    
    int itemType = 0; // 1:AT필드 2:자기장

    public Item(int d, int width, int height){
        this.radius = d;
        this.WIDTH = width;
        this.HEIGHT = height;

        this.itemType = (int) (Math.random() * 2)+1;

        int randSpawn = (int) (Math.random() * 4);
        int randx = (int) ((Math.random() * 5)+3);
        int randy = (int) ((Math.random() * 7)+3);

        //스폰 위치
        //상
        if(randSpawn == 0){
            x = (int) (Math.random() * (WIDTH - d) + 3);
            y = 0;

            xInc = x > (WIDTH/2) ? (int) (randx * -1) : (int) randx;
//            xInc = (int) randx;
            yInc = (int) randy;
        }
        //하
        else if(randSpawn == 1){
            x = (int) (Math.random() * (WIDTH - d) + 3);
            y = HEIGHT;

            xInc = x > (WIDTH/2) ? (int) (randx * -1) : (int) randx;
            yInc = (int) randy*-1;
        }
        //좌
        else if(randSpawn == 2){
            x = 0;
            y = (int) (Math.random() * (HEIGHT - d) + 3);

            xInc = (int) randx;
            yInc = y > (HEIGHT/2) ? (int) (randy*-1) : (int) (randy);
        }
        //우
        else if(randSpawn == 3){
            x = WIDTH;
            y = (int) (Math.random() * (HEIGHT - d) + 3);

            xInc = (int) randx * -1;
            yInc = y > (HEIGHT/2) ? (int) (randy*-1) : (int) (randy);
        }

    }

    @Override
    public void paintObject(Canvas g){
        Paint paint = new Paint();

        this.x += xInc;
        this.y += yInc;

        if(this.x > WIDTH || this.x < 0){
            clearObject(g);
            return;
        }
        if(this.y > HEIGHT || this.y < 0) {
            clearObject(g);
            return;
        }

        if(itemType == 1)
            paint.setColor(Color.rgb(178,235,244));
        else if(itemType == 2)
            paint.setColor(Color.rgb(209,178,255));
        g.drawCircle(x, y, radius, paint);
    }

    @Override
    public boolean encounter(Player player){
        if(player.isTouched(this.x, this.y, radius))
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
