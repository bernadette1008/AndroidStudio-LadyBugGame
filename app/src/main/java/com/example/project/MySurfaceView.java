package com.example.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private boolean isGameOver = false;
    static int eCnt = 6;
    public static List<Enemy> enemies;

    private MyThread thread;

    private Player player;

    long spawnTime = 5000; // 잡몹 소환
    long elapsedTime = 0; // 경과 시간
    long startTime = System.currentTimeMillis(); // 시작시간

    static int width, height;

    private GestureDetector gestureDetector;

    private float preX;
    private float preY;

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public MySurfaceView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
                //여기다가 하면 손을 땐 후에만 움직임 폐기처리~

                // 스와이프 동작 처리
//                float deltaX = e2.getX() - e1.getX();
//                float deltaY = e2.getY() - e1.getY();

                //플레이어 이동

//                int maxSpeed = 100;
//
//                if(Math.abs(deltaX) > maxSpeed){
//                    if(deltaX > 0){
//                        player.move(10, 0);
//                    }else{
//                        player.move(-10, 0);
//                    }
//                }
//                if(Math.abs(deltaY) > maxSpeed){
//                    if(deltaY > 0){
//                        player.move(0, 10);
//                    }else{
//                        player.move(0, -10);
//                    }
//                }
//                player.move(deltaX, deltaY);

                return true;
            }
        });
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new MyThread(holder);

        enemies = new ArrayList<>();

        width = getScreenWidth(context);
        height = getScreenHeight(context);

        System.out.println("width : "+ width + " height : "+ height);

        //플레이어 생성
        player = new Player(50, width, height, width/2, height/2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //폐기한 움직임 코드
//        gestureDetector.onTouchEvent(event);
        
        //새로운 움직임 코드
        switch( event.getAction()){

            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                //여기에 이동 구현
                float deltaX = event.getX() - preX;
                float deltaY = event.getY() - preY;
                player.move(deltaX, deltaY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        preX = event.getX();
        preY = event.getY();
        gestureDetector.onTouchEvent(event);

        return true;
    }

    public MyThread getThread(){
        return thread;
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        //Ball.WIDTH = width;
        //Ball.HEIGHT = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;

        thread.setRunning(false);
        while(retry){
            try{
                thread.join();
                retry = false;
            }catch (InterruptedException e){

            }
        }
    }

    public class MyThread extends Thread{
        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;

        public MyThread(SurfaceHolder surfaceHolder){
            mSurfaceHolder = surfaceHolder;
        }

        @Override
        public void run(){
            while (mRun){
                Canvas c = null;
                long currentTime = System.currentTimeMillis();
                elapsedTime += currentTime - startTime;
                startTime = currentTime;

                Paint p = new Paint();

                int randSpawnCnt = (int) (Math.random() * 5 + 1);
                try{
                    c = mSurfaceHolder.lockCanvas(null);
                    c.drawColor(Color.BLACK);
                    p.setColor(Color.WHITE);
                    p.setTextSize(50);
                    p.setTextAlign(Paint.Align.CENTER);
                    String XY = String.valueOf(player.getPlayerX())+ ", "+ String.valueOf(player.getPlayerY());
                    c.drawText(XY,width/2, height/2,p);

                    synchronized (mSurfaceHolder){
                        if(!isGameOver){
                            drawGameScreen(c);
//                            System.out.println(player.getPlayerX());
//                            System.out.println(player.getPlayerY());
                        }
                        else{
                            drawGameOverScreen(c);

                        }

                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        private void drawGameScreen(Canvas c){
            if(elapsedTime >= spawnTime){
                for (int i = 0; i < eCnt; i++){
                    Enemy eny = new Enemy(50, width, height, player);
                    enemies.add(eny);
                }
                elapsedTime = 0;
            }
            Iterator<Enemy> iterator = enemies.iterator();
            while(iterator.hasNext()){
                Enemy enemy = iterator.next();
                enemy.paintObject(c, player);
                if(enemy.encounter(player)){
                    isGameOver = true;
                }
            }
            if(player != null){
                player.paintPlayer(c);
            }

        }

        private void drawGameOverScreen(Canvas c){
            c.drawColor(Color.BLACK);
            Paint p = new Paint();
            p.setColor(Color.WHITE);
            p.setTextSize(50);
            p.setTextAlign(Paint.Align.CENTER);
            c.drawText("GAME OVER", width/2, height/2, p);
        }
        public void setRunning(boolean b){
            mRun = b;
        }
    }
}