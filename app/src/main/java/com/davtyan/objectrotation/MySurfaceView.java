package com.davtyan.objectrotation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public static String TEXT = "";
    private Paint paint;
    private DrawThread drawThread;
    private float x,y,width,height,rotate;
    private String str;

    private boolean drag = false;
    private float moveX = 0;
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    private float rotateX,rotateY;
    private Bitmap bitmap,bitmapResult;
    private int bitmapX = 450,bitmapY = 500;
    private float moveY;

    public MySurfaceView(Context c, AttributeSet attrs) {
        this(c, attrs, 0);
    }

    public MySurfaceView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        getHolder().addCallback(this);
        paint = new Paint();
        initData();
    }

    private void initData() {
        width = metrics.widthPixels/2 + 100;
        height = metrics.heightPixels/2 + 100;
        x = metrics.widthPixels/2 - 100;
        y = metrics.heightPixels/2 - 100;

    }

    public void setBitmap(Bitmap bitmap){
        if (bitmap != null){
            this.bitmap = bitmap;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drag = true;
                moveX = event.getX();
                moveY = event.getY();
                if (event.getX() > x && event.getX() < x+85 && event.getY() > y&& event.getY() < y+85 ){
                    str = " ";
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (MainActivity.EXTRA == "select_image" && str != null){
                    bitmapX = (int) (x + bitmapX -  event.getX());
                    bitmapY = (int) (y + bitmapY - event.getY());
                    x = event.getX() ;
                    y = event.getY();

                }else {
                    rotate += (event.getX() - moveX) / 200;
                }
                break;
            case MotionEvent.ACTION_UP:
                moveX = 0;
                str = null;
                drag = false;
                break;
        }
        return true;
    }

    private int getImageX(MotionEvent event) {
        int length = (int) (Math.pow((event.getX() - moveX),2) - Math.pow((event.getY() - moveY),2));
        return (int) Math.sqrt(length);
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

    public void setRotate(float rotate) {
        this.rotate = rotate;
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
            rotateX = x + (width - x)/2;
            rotateY = y + (height - y)/2;

            switch (MainActivity.EXTRA) {
                case "draw_point":
                    canvas.rotate(rotate,x,y + 5);
                    canvas.drawPoint(x, y, paint);
                    break;
                case "draw_line":
                    canvas.rotate(rotate,rotateX,rotateY);
                    canvas.drawLine(x, y, width, height, paint);
                    break;
                case "draw_circle":
                    canvas.rotate(rotate,x,y );
                    canvas.drawCircle(x, y, 100, paint);
                    canvas.drawLine(x, y, width, height, paint);
                    break;
                case "draw_rect":
                    canvas.rotate(rotate,rotateX,rotateY);
                    canvas.drawRect(x,y,width,height, paint);
                    break;
                case "draw_text":
                    paint.setTextSize(150);
                    canvas.rotate(rotate,x + TEXT.length()*25,y + 75);
                    canvas.drawText(TEXT, x, y, paint);
                    break;
                case "select_image":
                    //canvas.rotate(rotate,x + 125,y + 150);
                    if (bitmapX != 0 || bitmapY != 0){
                        bitmapResult = Bitmap.createScaledBitmap(bitmap, bitmapX , bitmapY , true);
                        canvas.drawBitmap(bitmapResult, x, y, null);
                    }
                    break;
            }
        }
    }

}
