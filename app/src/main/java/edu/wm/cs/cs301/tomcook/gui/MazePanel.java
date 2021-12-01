package edu.wm.cs.cs301.tomcook.gui;

import static edu.wm.cs.cs301.tomcook.R.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MazePanel extends View  {

    private Paint paint = new Paint();
    private Bitmap bitmap;

    public MazePanel(Context context) {
        super(context);
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("done");
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, 396, 344, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSizeAndState(getSuggestedMinimumWidth(), widthMeasureSpec, 1);
        int height = resolveSizeAndState(MeasureSpec.getSize(width), heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }
}
