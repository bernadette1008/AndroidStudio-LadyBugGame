package com.example.project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;

public class Item implements Object{
    int x, y;
    int diameter;

    int xInc, yInc;

    int WIDTH, HEIGHT;

    public Item(int d, int width, int height){
        this.diameter = d;
        this.WIDTH = width;
        this.HEIGHT = height;

        int randSpawn = (int) (Math.random() * 4);
        int rand = (int) ((Math.random() * 9)-5);

        //스폰 위치
        if(randSpawn == 0){
            x = (int) (Math.random() * (WIDTH - d) + 3);
            y = 0;

            xInc = (int) (Math.random() * rand);
            yInc = (int) (Math.random() * 4);
        }else if(randSpawn == 1){
            x = (int) (Math.random() * (WIDTH - d) + 3);
            y = HEIGHT;
            xInc = (int) (Math.random() * rand);
            yInc = (int) (Math.random() * -4);
        }else if(randSpawn == 2){
            x = 0;
            y = (int) (Math.random() * (HEIGHT - d) + 3);
            xInc = (int) (Math.random() * 4);
            yInc = (int) (Math.random() * rand);
        }else if(randSpawn == 3){
            x = WIDTH;
            y = (int) (Math.random() * (HEIGHT - d) + 3);
            xInc = (int) (Math.random() * -4);
            yInc = (int) (Math.random() * rand);
        }

    }

    @Override
    public void paintObject(Canvas g, Player target){
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

        paint.setColor(Color.rgb(0,255,0));
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
        //해당 코드는 그냥 안보이도록 수정하는 거 같다.
//        Paint paint = new Paint();
//        Xfermode xmode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
//        paint.setXfermode(xmode);
//        g.drawCircle(x, y, diameter, paint);

        MySurfaceView.items.remove(this);
    }
}
