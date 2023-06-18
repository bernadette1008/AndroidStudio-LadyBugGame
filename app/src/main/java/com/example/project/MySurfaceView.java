package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.Timer;
import java.util.TimerTask;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private Context context;

    private int score;

    private Timer timer;

    TimerTask scoreTask = new TimerTask() {
        @Override
        public void run() {
            score++;
        }
    };

    TimerTask enemySpawn = new TimerTask(){
        @Override
        public void run(){
            for (int i = 0; i < eCnt; i++){
                Enemy eny = new Enemy(50, width, height, player);
                enemies.add(eny);
            }
        }
    };

    TimerTask itemSpawn = new TimerTask() {
        @Override
        public void run() {
            Item item = new Item(50, width, height);
            items.add(item);
        }
    };

    public void setTimerTask(Timer t, TimerTask task, long delay, long period){
        t.schedule(task, delay, period);
    }

    private boolean isGameOver = false;

    static int eCnt = 6;
    public static List<Enemy> enemies;
    public static List<Item> items;

    public AT myAT;
    public List<MagneticField> myMFs;

    private MyThread thread;

    private Player player;

    long spawnTime = 5000; // 잡몹 소환
    long itemSpawnTime = 7000;

    boolean isATOn = false;
    boolean isMFOn = false;

    long itemOnTime = 3500; //아이템의 지속시간 3.5초
    long ATElapsedTime = 0;
    long ATStartTime;

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
        this.context = context;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
                return true;
            }
        });

        width = getScreenWidth(context);
        height = getScreenHeight(context);

        //플레이어 생성
        player = new Player(50, width, height, width/2, height/2);

        System.out.println("width : "+ width + " height : "+ height);

        enemies = new ArrayList<>();
        items = new ArrayList<>();
        myMFs = new ArrayList<>();


        score = 0;
        timer = new Timer();
//        timer.schedule(scoreTask,0, 100);
        setTimerTask(timer, scoreTask, 0, 100);
        setTimerTask(timer, enemySpawn, 0, spawnTime);
        setTimerTask(timer, itemSpawn, 0, itemSpawnTime);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new MyThread(holder);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //폐기한 움직임 코드
//        gestureDetector.onTouchEvent(event);
        
        //새로운 움직임 코드
        switch(event.getAction()){

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
//                long currentTime = System.currentTimeMillis();
//                elapsedTime += currentTime - startTime;
//                startTime = currentTime;
                Paint p = new Paint();

                int randSpawnCnt = (int) (Math.random() * 5 + 1);
                try{
                    c = mSurfaceHolder.lockCanvas(null);
                    if(c != null){
                        c.drawColor(Color.BLACK);
                        p.setColor(Color.WHITE);
                        p.setTextSize(100);
                        p.setTextAlign(Paint.Align.CENTER);
                        String userScore = "SCORE : "+ (score);

                        synchronized (mSurfaceHolder){
                            if(!isGameOver){
                                drawGameScreen(c);
                                c.drawText(userScore,width/5, 110,p);
                            }
                            else{
                                timer.cancel();
                                drawGameOverScreen(c);
                                break;
                            }
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
            synchronized (enemies) {
                Iterator<Enemy> enemyIterator = enemies.iterator();
                while (enemyIterator.hasNext()) {
                    Enemy enemy = enemyIterator.next();
                    enemy.paintObject(c, player);

                    // 플레이어가 적과 닿았을 때
                    if (enemy.encounter(player) && !isATOn) {

                        isGameOver = true;
                    }
                    if (isATOn){
                        if(enemy.attackedByAT(myAT)){
                            enemyIterator.remove();
                            score += 10;
                        }
                    }
                    if (isMFOn){
                        synchronized (myMFs){
                            Iterator<MagneticField> mfIterator = myMFs.iterator();
                            while(mfIterator.hasNext()){
                                MagneticField mf = mfIterator.next();
                                if(enemy.attackedByMF(mf)){
                                    synchronized (enemies){
                                        enemyIterator.remove();
                                    }
                                    score += 10;
                                }
                            }
                        }
                    }
                }
            }

            synchronized (items) {
                if (!items.isEmpty()) {
                    Iterator<Item> itemIterator = items.iterator();
                    while (itemIterator.hasNext()) {
                        Item item = itemIterator.next();
                        if (item.encounter(player)) {
                            if(item.itemType == 1){//AT
                                isATOn = true;
                                ATStartTime = System.currentTimeMillis();
                                ATElapsedTime = 0;
                                myAT = new AT(player);
                            }
                            else {//자기장
                                isMFOn = true;
                                myMFs.add(new MagneticField(player, 0L, System.currentTimeMillis()));

                            }
                            itemIterator.remove();
                        } else {
                            item.paintObject(c);
                        }
                    }
                }
            }

            if(isATOn){
                long currentTime = System.currentTimeMillis();
                ATElapsedTime += currentTime - ATStartTime;
                ATStartTime = currentTime;
                if(ATElapsedTime <= itemOnTime){
                    myAT.moveObject(player);
                    myAT.paintObject(c);
                }else{
                    myAT = null;
                    isATOn = false;
                }
            }

            synchronized (myMFs){
                if(isMFOn){
                    Iterator<MagneticField> mfIterator = myMFs.iterator();
                    int cnt = 0;
                    while(mfIterator.hasNext()){
                        MagneticField mf = mfIterator.next();
                        long currentTime = System.currentTimeMillis();
                        mf.mfElapsedTime += currentTime - mf.mfStartTime;
                        mf.mfStartTime = currentTime;
                        if(mf.mfElapsedTime <= itemOnTime){
                            mf.paintObject(c);
                        }else{
                            mfIterator.remove();
                        }
                    }
                }
            }

            if (player != null) {
                player.paintPlayer(c);
            }

        }

        private void drawGameOverScreen(Canvas c){
//            c.drawColor(Color.BLACK);
//            Paint p = new Paint();
//            p.setColor(Color.WHITE);
//            p.setTextSize(150);
//            p.setTextAlign(Paint.Align.CENTER);
//            c.drawText("GAME OVER", width/2, height/2, p);
            Intent intent = new Intent(context, Gameover.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        public void setRunning(boolean b){
            mRun = b;
        }
    }
}