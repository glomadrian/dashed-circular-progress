package com.github.glomadrian.dashedcircularprogress.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Adrián García Lomas
 */
public class ProgressPainterImp implements ProgressPainter {

    private RectF progressCircle;
    private Paint progressPaint;
    private int color = Color.RED;
    private float startAngle = 270f;
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

    public ProgressPainterImp(int color, float min, float max, int progressStrokeWidth) {
        this.color = color;
        this.min = min;
        this.max = max;
        this.internalStrokeWidth = progressStrokeWidth;
        init();
    }

    private void init() {
        initInternalCirclePainter();

    }

    private void initInternalCirclePainter() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(internalStrokeWidth);
        progressPaint.setColor(color);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setPathEffect(new DashPathEffect(new float[]{dashWith, dashSpace},
                dashSpace));
    }

    private void initInternalCircle() {
        progressCircle = new RectF();
        padding = internalStrokeWidth * 1.7f;
        progressCircle.set(padding, padding + marginTop, width - padding, height - padding);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawArc(progressCircle, startAngle, plusAngle, false, progressPaint);
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
        this.plusAngle = (359.8f * value) / max;
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.width = width;
        this.height = height;
        initInternalCircle();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        progressPaint.setColor(color);
    }
}
