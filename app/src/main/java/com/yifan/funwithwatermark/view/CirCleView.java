package com.yifan.funwithwatermark.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-07
 */
public class CirCleView extends View {

    private Picture mPicture = new Picture();
    private Paint mPaint = new Paint();
    // 宽高
    private int mWidth, mHeight;

    public CirCleView(Context context) {
        super(context);
    }

    public CirCleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        recording();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setColor(Color.BLACK);
//        canvas.translate(200, 200);
//        canvas.drawCircle(0, 0, 100, mPaint);
//
//        mPaint.setColor(Color.BLUE);
//        canvas.translate(200, 200);
//        canvas.drawCircle(0, 0, 100, mPaint);
//        canvas.drawPicture(mPicture);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(50);
        String str = "ABCDEFG";
        canvas.translate(mWidth/2,mHeight/2);
        canvas.scale(1,-1);
        Path path = new Path();
        path.lineTo(100,100);

        RectF rectF = new RectF(0, 0, 300, 300);
        path.addArc(rectF,0,270);
        Path src = new Path();
        canvas.drawPath(path,paint);



        paint.setColor(Color.RED);
        canvas.drawLine(0,0,1000,0,paint);
        canvas.drawLine(0,0,0,1000,paint);
        canvas.drawLine(0,0,-1000,0,paint);
        canvas.drawLine(0,0,0,-1000,paint);
//        canvas.drawText(str, 200, 500, paint);
//        canvas.drawText(str,1,3,200,500,paint);
    }

    private void recording() {
        Canvas canvas = mPicture.beginRecording(500, 500);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
//        canvas.translate(250,250);
        canvas.drawCircle(100, 100, 100, paint);
        mPicture.endRecording();
    }



}
