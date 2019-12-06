package com.example.geoffflappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class GeoffFlappyView extends View {
    private Bitmap geoff[] = new Bitmap[2];
    private int geoffX = 10;
    private int geoffY;
    private int geoffSpeed;

    private int canvasWidth, canvasHeight;

    private int obstacleX, obstacleY, obstacleSpeed = 16;
    private Paint obstaclePaint = new Paint();

    private int secondobsX, secondobsY, secondobsSpeed = 20;
    private Paint secondobsPaint = new Paint();

    private int score;

    private boolean touch = false;

    private Bitmap background;
    private Paint scorePaint = new Paint();
    private Bitmap lives[] = new Bitmap[2];


    public GeoffFlappyView(Context context) {
        super(context);

        geoff[0] = BitmapFactory.decodeResource(getResources(), R.drawable.geoffbird);
        geoff[1] = BitmapFactory.decodeResource(getResources(), R.drawable.geoffbird);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.siebel);

        obstaclePaint.setColor(Color.MAGENTA);
        obstaclePaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.aliveheart);
        lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.deadheart);

        geoffY = 550;
        score = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(background, 0, 0, null);

        int minGeoffY = geoff[0].getHeight();
        int maxGeoffY = canvasHeight - geoff[0].getHeight() * 3;
        geoffY = geoffY + geoffSpeed;
        if (geoffY < minGeoffY) {
            geoffY = minGeoffY;
        }
        if (geoffY > maxGeoffY) {
            geoffY = maxGeoffY;
        }
        geoffSpeed = geoffSpeed + 2;
        if (touch) {
            canvas.drawBitmap(geoff[1], geoffX, geoffY, null);
            touch = false;
        } else {
            canvas.drawBitmap(geoff[0], geoffX, geoffY, null);
        }


        obstacleX = obstacleX - obstacleSpeed;

        if (hitObstacleChecker(obstacleX, obstacleY)) {
            score += 10;
            obstacleX = - 100;
        }

        if (obstacleX < 0) {
            obstacleX = canvasWidth + 21;
            obstacleY = (int) Math.floor(Math.random() * (maxGeoffY - minGeoffY)) + minGeoffY;
        }

        canvas.drawCircle(obstacleX, obstacleY, 25, obstaclePaint);

        canvas.drawText("Score: " + score, 20, 60, scorePaint);

        canvas.drawBitmap(lives[0], 780, 10, null);
        canvas.drawBitmap(lives[0], 880, 10, null);
        canvas.drawBitmap(lives[0], 980, 10, null);

    }


    public boolean hitObstacleChecker(int x, int y) {
        if (geoffX < x && x < (geoffX + geoff[0].getWidth()) && geoffY < y && y < (geoffY + geoff[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            geoffSpeed = -22;
        }
        return true;
    }
}