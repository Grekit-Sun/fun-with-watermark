package com.yifan.funwithwatermark.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.yifan.funwithwatermark.viewdata.PieData;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-07
 */
public class PieView extends View {

    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private Paint mPaint = new Paint();
    // 宽高
    private int mWidth, mHeight;

    // 数据
    private ArrayList<PieData> mData;

    // 饼状图初始绘制角度
    private float mStartAngle = 0;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        ArrayList<PieData> pieData = new ArrayList<>();
        PieData data1 = new PieData("sloop", 60);
        PieData data2 = new PieData("sloop", 30);
        PieData data3 = new PieData("sloop", 40);
        PieData data4 = new PieData("sloop", 20);
        PieData data5 = new PieData("sloop", 20);
        pieData.add(data1);
        pieData.add(data2);
        pieData.add(data3);
        pieData.add(data4);
        pieData.add(data5);
        setData(pieData);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
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
        if (null == mData) {
            return;
        }
        float currentStartAngle = mStartAngle;
        canvas.translate(mWidth / 2, mHeight / 2);       // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-r, -r, r, r);

        for (PieData data : mData) {
            mPaint.setColor(data.getColor());
            canvas.drawArc(rectF, currentStartAngle, data.getAngle(), true, mPaint);
        }
    }

    // 设置起始角度
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();   // 刷新
    }

    // 设置数据
    public void setData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();   // 刷新
    }

    // 初始化数据
    private void initData(ArrayList<PieData> mData) {
        if (null == mData || mData.size() == 0) {// 数据有问题 直接返回
            return;
        }

        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            sumValue += pie.getValue();       //计算数值和

            int j = i % mColors.length;       //设置颜色
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            float percentage = pie.getValue() / sumValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度

            pie.setPercentage(percentage);                  // 记录百分比
            pie.setAngle(angle);                            // 记录角度大小
            sumAngle += angle;

            Log.i("angle", "" + pie.getAngle());
        }
    }

    private int measure(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        //default value
        int res = 500;
        if (mode == MeasureSpec.AT_MOST) {
            res = size;
        } else if (mode == MeasureSpec.EXACTLY) {
            res = size;
        }
        return res;
    }
}
