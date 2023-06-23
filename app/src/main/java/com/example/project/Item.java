package com.example.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;

public class Item implements Object{
    int x, y;
    int radius;

    int xInc, yInc;

    int WIDTH, HEIGHT;
    
    int itemType = 0; // 1:AT필드 2:자기장

    int type;

    Bitmap itemIcon;

    public Item(int d, int width, int height, Bitmap itemIcon1, Bitmap itemIcon2){
        this.radius = d;
        this.WIDTH = width;
        this.HEIGHT = height;

        this.itemType = (int) (Math.random() * 2)+1;

        int randSpawn = (int) (Math.random() * 4);
        int randx = (int) ((Math.random() * 5)+3);
        int randy = (int) ((Math.random() * 7)+3);

        type = (int) (Math.random()*2);

        if(itemType == 1){
            this.itemIcon = itemIcon1;
        }
        else{
            this.itemIcon = itemIcon2;
        }

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

        if(itemType == 1){
            paint.setColor(Color.rgb(178,235,244));
        }
        else if(itemType == 2){
            paint.setColor(Color.rgb(209,178,255));
        }

        Bitmap resizePlayerIcon = Bitmap.createScaledBitmap(itemIcon, radius*3, radius*3, false);
        g.drawCircle(x, y, radius, paint);
        g.drawBitmap(resizePlayerIcon, (float) (x-(radius*1.5)), (float) (y-(radius*1.5)), null);
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
