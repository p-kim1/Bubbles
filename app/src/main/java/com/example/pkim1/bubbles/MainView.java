package com.example.pkim1.bubbles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import static com.example.pkim1.bubbles.MainThread.canvas;

public class MainView extends SurfaceView implements SurfaceHolder.Callback{
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected static MainThread thread;
    private float touchX;
    private float touchY;
    private Boolean colorSet = false;

    public MainView(Context context, AttributeSet att) {
        super(context, att);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect dest = new Rect(0, 0, getWidth(), getHeight());
        Random r = new Random();
        int color = Color.argb(255,r.nextInt(256),r.nextInt(256),r.nextInt(256));
        //MainThread.canvas.drawColor(Color.RED);
        if(!colorSet) {
            paint.setColor(color);
            colorSet = true;
        }
        if(touchX != 0 && touchY != 0)
        MainThread.canvas.drawCircle(touchX, touchY, 40, paint);
        //Bitmap background = BitmapFactory.decodeResource(this.getResources(), backgroundSrc);
        //MainThread.canvas.drawBitmap(background,null,dest,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            colorSet = false;
            touchX = event.getX();
            touchY = event.getY();
        }
        return true;
    }
}