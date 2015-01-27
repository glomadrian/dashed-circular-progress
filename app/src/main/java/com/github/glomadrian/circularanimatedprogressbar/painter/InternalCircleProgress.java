package com.github.glomadrian.circularanimatedprogressbar.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Adrián García Lomas
 */
public class InternalCircleProgress implements Painter {

    private RectF internalCircle;
    private Paint internalCirclePaint;
    private int color = Color.RED;

    private int startAngle = 271;
    private float plusAngle = 0;
    private int internalStrokeWidth = 48;
    private int dashWith = 5;
    private int dashSpace = 8;
    private float marginTop = 45;
    private float padding;
    private float min;
    private float max;
    private int width;
    private int height;

    public InternalCircleProgress(int color, float min, float max, int width, int height) {
        this.color = color;
        this.min = min;
        this.max = max;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        initInternalCirclePainter();
        initInternalCircle();

    }

    private void initInternalCirclePainter() {
        internalCirclePaint = new Paint();
        internalCirclePaint.setAntiAlias(true);
        internalCirclePaint.setStrokeWidth(internalStrokeWidth);
        internalCirclePaint.setColor(color);
        internalCirclePaint.setStyle(Paint.Style.STROKE);
        internalCirclePaint.setPathEffect(new DashPathEffect(new float[]{dashWith, dashSpace},
                dashSpace));
    }

    private void initInternalCircle() {
        internalCircle = new RectF();
        padding = internalStrokeWidth * 1.7f;
        internalCircle.set(padding, padding + marginTop, width - padding, height - padding);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawArc(internalCircle, startAngle, plusAngle, false, internalCirclePaint);
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setValue(float value) {
        this.plusAngle = (360 * value) / max;
    }
}
