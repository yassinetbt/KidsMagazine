package com.yassinetaboubi.android.magazines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View{

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(10);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private void onStartTouch(float x, float y){
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }

    private void moveTouch(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if(dx >= TOLERANCE || dy >=TOLERANCE){
            mPath.quadTo(mX, mY, (x + mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas(){
        mPath.reset();
        invalidate();
    }

    public void changeColor(String c){
        if(c.equals("red")){
            mPaint.setColor(Color.RED);
        }
        if(c.equals("blue")){
            mPaint.setColor(Color.BLUE);
        }
        if(c.equals("yellow")){
            mPaint.setColor(Color.YELLOW);
        }
        if(c.equals("black")){
            mPaint.setColor(Color.BLACK);
        }
    }

    public void changeSize(int s){
        mPaint.setStrokeWidth(s);

    }

    private void upTouch(){
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onStartTouch(x, y);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}
