package com.davtyan.objectrotation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by COMP on 06.02.2018.
 */

public class MyDrawView extends View {
    public static String TEXT = "";
    private Paint paint;
    private float x = 300;
    private float y = 450;
    private int size = 300;

    boolean drag = false;
    float dragX = 0;
    float dragY = 0;


    public MyDrawView(Context c) {
        this(c, null);
    }

    public MyDrawView(Context c, AttributeSet attrs) {
        this(c, attrs, 0);
    }

    public MyDrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawFigure(canvas);
    }

    private void onDrawFigure(Canvas canvas) {
        canvas.drawARGB(80, 102, 204, 255);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

        switch (MainActivity.EXTRA) {
            case "draw_point":
                canvas.drawPoint(x, y, paint);
                invalidate();
                break;
            case "draw_line":
                canvas.drawLine(x, y, x + 300, y - 50, paint);
                invalidate();
                break;
            case "draw_circle":
                canvas.drawCircle(x, y, size, paint);
                invalidate();
                break;
            case "draw_rect":
                canvas.drawRect(x, y, x + 300, y + 300, paint);
                invalidate();
                break;
            case "draw_text":
                paint.setTextSize(150);
                canvas.drawText(TEXT, x, y, paint);
                invalidate();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float evX = event.getX();
        float evY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (evX >= x && evX <= x + size && evY >= y && evY <= y + size) {
                    drag = true;
                    dragX = evX - x;
                    dragY = evY - y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (drag) {
                    x = evX - dragX;
                    y = evY - dragY;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                drag = false;
                break;
        }
        return true;
    }


}
