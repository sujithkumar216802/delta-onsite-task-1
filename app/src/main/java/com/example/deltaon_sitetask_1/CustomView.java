package com.example.deltaon_sitetask_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {


    static int left, top, right, bottom;
    static Paint rectanglepaint = new Paint();
    static boolean start = true;
    static int dragmode = 0;
    static int cx = 0, cy = 0, clickx = 0, clicky = 0;

    //0 means nothing
    //1 is left and top
    // 2 is left bottom
    //3 is right and bottom
    //4 is right top
    //5 is entire rectangle
    //6 left edge
    //7 top edge
    // 8 right edge
    // 9 bottom edge


    public CustomView(Context context) {
        super(context);


        rectanglepaint.setStrokeWidth(5);
        rectanglepaint.setColor(Color.RED);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (start) {
            left = (getWidth() / 2) - 100;
            right = (getWidth() / 2) + 100;
            top = (getHeight() / 2) - 100;
            bottom = (getHeight() / 2) + 100;
            start = false;
        }

        canvas.drawRect(left, top, right, bottom, rectanglepaint);

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean temp = super.onTouchEvent(event);
        postInvalidate();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                //left top
                if (Math.abs(event.getX() - left) < 25 && Math.abs(event.getY() - top) < 25)
                    dragmode = 1;

                    //left bottom
                else if (Math.abs(event.getX() - left) < 25 && Math.abs(event.getY() - bottom) < 25)
                    dragmode = 2;

                    //right bottom
                else if (Math.abs(event.getX() - right) < 25 && Math.abs(event.getY() - bottom) < 25)
                    dragmode = 3;

                    //right top
                else if (Math.abs(event.getX() - right) < 25 && Math.abs(event.getY() - top) < 25)
                    dragmode = 4;

                    //left
                else if (Math.abs(event.getX() - left) < 25 && ((event.getY() > bottom && event.getY() < top) || (event.getY() < bottom && event.getY() > top)))
                    dragmode = 6;

                    //top
                else if (Math.abs(event.getY() - top) < 25 && ((event.getX() > left && event.getX() < right) || (event.getX() < left && event.getX() > right)))
                    dragmode = 7;
                    //right
                else if (Math.abs(event.getX() - right) < 25 && ((event.getY() > bottom && event.getY() < top) || (event.getY() < bottom && event.getY() > top)))
                    dragmode = 8;

                    //bottom
                else if (Math.abs(event.getY() - bottom) < 25 && ((event.getX() > left && event.getX() < right) || (event.getX() < left && event.getX() > right)))
                    dragmode = 9;


                    //center
                else if (((event.getX() > left && event.getX() < right) || (event.getX() < left && event.getX() > right)) && ((event.getY() > bottom && event.getY() < top) || (event.getY() < bottom && event.getY() > top))) {
                    dragmode = 5;
                    clickx = (int) event.getX();
                    clicky = (int) event.getY();
                } else
                    dragmode = 0;

                return true;
            }
            case MotionEvent.ACTION_MOVE: {

                switch (dragmode) {
                    case 1:
                        left = (int) event.getX();
                        top = (int) event.getY();
                        break;
                    case 2:

                        left = (int) event.getX();
                        bottom = (int) event.getY();
                        break;
                    case 3:
                        right = (int) event.getX();
                        bottom = (int) event.getY();
                        break;
                    case 4:
                        right = (int) event.getX();
                        top = (int) event.getY();
                        break;
                    case 5:
                        left += event.getX() - clickx;
                        right += event.getX() - clickx;
                        top += event.getY() - clicky;
                        bottom += event.getY() - clicky;
                        clickx = (int) event.getX();
                        clicky = (int) event.getY();
                        break;
                    case 6:
                        left = (int) event.getX();
                        break;
                    case 7:
                        top = (int) event.getY();
                        break;
                    case 8:
                        right = (int) event.getX();
                        break;
                    case 9:
                        bottom = (int) event.getY();
                        break;

                }
                return true;

            }
            case MotionEvent.ACTION_UP: {
                dragmode = 0;
                clickx = 0;
                clicky = 0;
            }
        }

        return temp;

    }
}
