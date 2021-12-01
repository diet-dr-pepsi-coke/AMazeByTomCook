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

public class MazePanel extends View implements P5PanelF21 {

    private Paint paint = new Paint();
    private Bitmap bitmap;
    private Canvas canvas_bm;

    public MazePanel(Context context) {
        super(context);
        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        canvas_bm = new Canvas(bitmap);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        canvas_bm = new Canvas(bitmap);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        canvas_bm = new Canvas(bitmap);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        canvas_bm = new Canvas(bitmap);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        myTestImage(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSizeAndState(getSuggestedMinimumWidth(), widthMeasureSpec, 1);
        int height = resolveSizeAndState(MeasureSpec.getSize(width), heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    /**
     * Method to draw the buffer image on a graphics object that is
     * obtained from the superclass.
     * Warning: do not override getGraphics() or drawing might fail.
     */
    public void update() {
        /*
        paint(getGraphics());

         */
    }

    public void addLine(int startX, int startY, int endX, int endY) {
        canvas_bm.drawLine((float) startX, (float) startY, (float) endX, (float) endY, paint);
    }

    @Override
    public void commit() {
        /*
        paint(getGraphics());

         */

    }

    @Override
    public boolean isOperational() {
        /*
        Graphics g = this.getBufferGraphics();
        if (g != null) {
            return true;
        }
        else {
            return false;
        }

         */
        return true;
    }

    @Override
    public void setColor(int rgb) {
        /*
        Graphics g = this.getBufferGraphics();
        Color c = new Color(rgb);
        g.setColor(c);


         */
    }

    /**
     * Provides an alternate way to set the color used for drawing
     * @param r the red value 0-255
     * @param g the green value 0-255
     * @param b the blue value 0-255
     */
    public void setColor(int r, int g, int b) {
        /*
        Graphics gc = this.getBufferGraphics();
        Color c = new Color(r, g, b);
        gc.setColor(c);

         */
    }

    /**
     * Provides an alternate way to set the color used for drawing
     * @param r a multiplier used on 255 to determine red value
     * @param g a multiplier used on 255 to determine green value
     * @param b a multiplier used on 255 to determine blue value
     * @param a a multiplier used on 255 to determine alpha value
     */
    public void setColor(float r, float g, float b, float a) {
        /*
        Graphics gc = this.getBufferGraphics();
        Color c = new Color(r, g, b, a);
        gc.setColor(c); */
    }

    @Override
    public int getColor() {
        /*
        Graphics g = this.getBufferGraphics();
        Color col = g.getColor();
        return col.getRGB();
         */
        return 0;
    }

    @Override
    public void addBackground(float percentToExit) {

    }

    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        /*
        Graphics g = this.getBufferGraphics();
        g.fillRect(x, y, width, height);

         */

    }

    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        /*
        Graphics g = this.getBufferGraphics();
        g.fillPolygon(xPoints, yPoints, nPoints);

         */

    }

    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        /*
        Graphics g = this.getBufferGraphics();
        g.drawPolygon(xPoints, yPoints, nPoints);
         */

    }

    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        /*
        Graphics g = this.getBufferGraphics();
        g.fillOval(x, y, width, height);

         */

    }

    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        /*
        Graphics g = this.getBufferGraphics();
        g.drawArc(x, y, width, height, startAngle, arcAngle);

         */

    }

    @Override
    public void addMarker(float x, float y, String str) {
        /*
        Graphics2D g = (Graphics2D) this.getBufferGraphics();
        GlyphVector gv = markerFont.createGlyphVector(g.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        // need to update x, y by half of rectangle width, height
        // to serve as x, y coordinates for drawing a GlyphVector
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;

        g.drawGlyphVector(gv, x, y);

         */
    }

    @Override
    public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {
        /*
        Graphics2D g = (Graphics2D) this.getBufferGraphics();
        g.setRenderingHint(convertP5toGraphicHint(hintKey), convertP5toGraphicObject(hintValue));

         */

    }

    private void myTestImage(Canvas c) {
        addLine(0,0,c.getHeight(), c.getWidth());
        c.drawBitmap(bitmap, (float) 0, (float) 0, paint);
    }

}
