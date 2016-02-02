package pri.samarium.calendar.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by sunlishan on 16-2-1.
 */
public class MyCalendor extends View {
    private static final String TAG = "MyCalendor";

    public MyCalendor(Context context) {
        super(context);
    }

    public MyCalendor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCalendor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mPaint = null;

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw:" + canvas.getWidth() + "," + canvas.getHeight());
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        if (mPaint == null) {
            mPaint = new Paint();
        }
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        int min = Math.min(width, height);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//抗抖动
        mPaint.setARGB(255, 255, 0, 0);//画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置为空心
        mPaint.setStrokeWidth(50);//边框粗细
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        RectF rect = new RectF(0 + 10, 0 + 10, min - 10, min - 10);
//        canvas.drawRoundRect(rect, 50, 50, mPaint);
        canvas.drawLine(100,100,300,300,mPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.i(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout,changed:" + changed + ",left:" + left + ",right" + right + ",top:" + top + ",bottom:" + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.v(TAG, "onMeasure,width:" + getMeasuredWidth() + ",height:" + getMeasuredHeight());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
