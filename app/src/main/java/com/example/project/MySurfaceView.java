package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;

    private String userName = "user";
    private int score;
    private DatabaseHelper dbHelper;

    private Bitmap itemBitmap1;
    private Bitmap itemBitmap2;

    private Timer timer;
    private TimerTask scoreTask;
    private TimerTask enemySpawn;
    private TimerTask itemSpawn;

    private boolean isGameOver = false;

    private static int eCnt = 6;
    private List<Enemy> enemies;
    private List<Item> items;

    private AT myAT;
    private List<MagneticField> myMFs;

    private MyThread thread;

    private Player player;
    private Bitmap playerBitmap;

    private long spawnTime = 5000;
    private long itemSpawnTime = 7000;

    private boolean isATOn = false;
    private boolean isMFOn = false;

    private long itemOnTime = 3500;
    private long ATElapsedTime = 0;
    private long ATStartTime;

    private static int width, height;

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

    public void setTimerTask(Timer timer, TimerTask task, long delay, long period) {
        timer.schedule(task, delay, period);
    }

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;

        dbHelper = new DatabaseHelper(context);

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });

        width = getScreenWidth(context);
        height = getScreenHeight(context);

        playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_icon);
        player = new Player(50, width, height, width / 2, height / 2, playerBitmap);

        itemBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.at_icon);
        itemBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.mf_icon);

        enemies = new ArrayList<>();
        items = new ArrayList<>();
        myMFs = new ArrayList<>();

        score = 0;
        timer = new Timer();

        scoreTask = new TimerTask() {
            @Override
            public void run() {
                score++;
            }
        };

        enemySpawn = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < eCnt; i++) {
                    Enemy eny = new Enemy(50, width, height, player);
                    enemies.add(eny);
                }
            }
        };

        itemSpawn = new TimerTask() {
            @Override
            public void run() {
                Item item = new Item(50, width, height, itemBitmap1, itemBitmap2);
                items.add(item);
            }
        };

        setTimerTask(timer, scoreTask, 0, 100);
        setTimerTask(timer, enemySpawn, 0, spawnTime);
        setTimerTask(timer, itemSpawn, 0, itemSpawnTime);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new MyThread(holder);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - preX;
                float deltaY = event.getY() - preY;
                float newRotation = (float) Math.toDegrees(Math.atan2(deltaX, -deltaY));
                player.pRotate(newRotation);
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

    public MyThread getThread() {
        return thread;
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;

        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    public class MyThread extends Thread {
        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;

        public MyThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                Paint p = new Paint();

                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    if (c != null) {
                        c.drawColor(Color.BLACK);
                        p.setColor(Color.WHITE);
                        p.setTextSize(100);
                        p.setTextAlign(Paint.Align.CENTER);
                        String userScore = "SCORE : " + score;

                        synchronized (mSurfaceHolder) {
                            if (!isGameOver) {
                                drawGameScreen(c);
                                c.drawText(userScore, width/3, 110, p);
                            } else {
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

        private void drawGameScreen(Canvas c) {
            synchronized (enemies) {
                for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext(); ) {
                    Enemy enemy = enemyIterator.next();
                    enemy.paintObject(c, player);

                    if (enemy.encounter(player) && !isATOn) {
                        isGameOver = true;
                    }

                    if (isATOn && enemy.attackedByAT(myAT)) {
                        enemyIterator.remove();
                        score += 10;
                    }

                    if (isMFOn) {
                        for (Iterator<MagneticField> mfIterator = myMFs.iterator(); mfIterator.hasNext(); ) {
                            MagneticField mf = mfIterator.next();
                            if (enemy.attackedByMF(mf)) {
                                enemyIterator.remove();
                                score += 10;
                            }
                        }
                    }
                }
            }

            synchronized (items) {
                if (!items.isEmpty()) {
                    for (Iterator<Item> itemIterator = items.iterator(); itemIterator.hasNext(); ) {
                        Item item = itemIterator.next();
                        if (item.encounter(player)) {
                            handleItemEncounter(item);
                            itemIterator.remove();
                        } else {
                            item.paintObject(c);
                        }
                    }
                }
            }

            updateAndDrawAT(c);
            updateAndDrawMFs(c);

            if (player != null) {
                player.paintPlayer(c);
            }
        }

        private void handleItemEncounter(Item item) {
            if (item.itemType == 1) {
                isATOn = true;
                ATStartTime = System.currentTimeMillis();
                ATElapsedTime = 0;
                myAT = new AT(player);
            } else {
                isMFOn = true;
                myMFs.add(new MagneticField(player, 0L, System.currentTimeMillis()));
            }
        }

        private void updateAndDrawAT(Canvas c) {
            if (isATOn) {
                long currentTime = System.currentTimeMillis();
                ATElapsedTime += currentTime - ATStartTime;
                ATStartTime = currentTime;
                if (ATElapsedTime <= itemOnTime) {
                    myAT.moveObject(player);
                    myAT.paintObject(c);
                } else {
                    myAT = null;
                    isATOn = false;
                }
            }
        }

        private void updateAndDrawMFs(Canvas c) {
            synchronized (myMFs) {
                if (isMFOn) {
                    for (Iterator<MagneticField> mfIterator = myMFs.iterator(); mfIterator.hasNext(); ) {
                        MagneticField mf = mfIterator.next();
                        long currentTime = System.currentTimeMillis();
                        mf.mfElapsedTime += currentTime - mf.mfStartTime;
                        mf.mfStartTime = currentTime;
                        if (mf.mfElapsedTime <= itemOnTime) {
                            mf.paintObject(c);
                        } else {
                            mfIterator.remove();
                        }
                    }
                }
            }
        }

        private void drawGameOverScreen(Canvas c) {
            dbHelper.saveScore(userName, score);
            Intent intent = new Intent(context, Gameover.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        public void setRunning(boolean b) {
            mRun = b;
        }
    }
}