package com.davtyan.objectrotation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public static String TEXT = "";
    private Paint paint;
    private DrawThread drawThread;
    private float x = 250, y = 250, width = 150, height = 300;

    private boolean drag = false;
    private float moveX = 0;



    public MySurfaceView(Context c, AttributeSet attrs) {
        this(c, attrs, 0);
    }

    public MySurfaceView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        getHolder().addCallback(this);
        paint = new Paint();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drag = true;
                moveX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                switch (MainActivity.EXTRA) {
                    case "draw_point":
                        rotateFigure(event);
                        break;
                    case "draw_line":
                       rotateLine(event);
                        break;
                    case "draw_circle":
                        rotateFigure(event);
                        break;
                    case "draw_rect":
                        rotateFigure(event);
                        break;
                    case "draw_text":
                        rotateFigure(event);
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                moveX = 0;
                drag = false;
                break;
        }
        return true;
    }



    public void rotateFigure(MotionEvent event) {
        float widthCircle = 2 * width;
        if (y >= widthCircle && x <= widthCircle ) {
            x +=(event.getX() - moveX)/100;
            y +=(event.getX() - moveX)/100;

        }else if ( y >= widthCircle && x >= widthCircle){
            x += (event.getX() - moveX)/100;
            y -= (event.getX() - moveX)/100;
        }
        else if ( y <= widthCircle && x >= widthCircle){
            x -= (event.getX() - moveX)/100;
            y -= (event.getX() - moveX)/100;
        }
        else if ( y <= widthCircle && x <= widthCircle){
            x -= (event.getX() - moveX)/100;
            y += (event.getX() - moveX)/100;
        }
    }
    public void rotateLine(MotionEvent event) {
        if (y >= height && x <= width ) {
            x += (event.getX() - moveX)/100;
            y += (event.getX() - moveX)/100 ;
            width -= (event.getX() - moveX)/100;
            height -= (event.getX() - moveX)/100 ;
        }else if ( y >= height && x >= width){
            x += (event.getX() - moveX)/100;
            y -= (event.getX() - moveX)/100;
            width -= (event.getX() - moveX)/100;
            height += (event.getX() - moveX)/100;
        }
        else if ( y <= height && x >= width){
            x -= (event.getX() - moveX)/100;
            y -= (event.getX() - moveX)/100;
            width += (event.getX() - moveX)/100;
            height += (event.getX() - moveX)/100;
        }
        else if ( y <= height && x <= width){
            x -= (event.getX() - moveX)/100;
            y += (event.getX() - moveX)/100;
            width += (event.getX() - moveX)/100 ;
            height -= (event.getX() - moveX)/100;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }


        @Override
        public void run() {
            Canvas canvas = null;
            while (running) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    canvas.drawColor(Color.GRAY);
                    onDrawFigure(canvas);
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }


        }

        private void onDrawFigure(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);

            switch (MainActivity.EXTRA) {
                case "draw_point":
                    canvas.drawPoint(x, y, paint);
                    break;
                case "draw_line":
                    canvas.drawLine(x, y, width, height, paint);
                    break;
                case "draw_circle":
                    canvas.drawCircle(x, y, width, paint);
                    break;
                case "draw_rect":
                    canvas.drawRect(x,y,width,height, paint);
                    break;
                case "draw_text":
                    paint.setTextSize(width);
                    y = 450;
                    canvas.drawText(TEXT, x, y, paint);
                    break;
            }
        }
    }

}
