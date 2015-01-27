package com.github.glomadrian.circularanimatedprogressbar.painter;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Adrián García Lomas
 */
public class InternalCirclePainter implements Painter {

    private RectF internalCircle;
    private Paint internalCirclePaint;
    private int color;

    private int startAngle = 0;
    private int finishAngle = 360;
    private int width;
    private int height;
    private int internalStrokeWidth = 48;
    private int dashWith = 5;
    private int dashSpace = 8;
    private float marginTop = 45;

    public InternalCirclePainter(int color, int width, int height) {
        this.color = color;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        initExternalCirclePainter();
        initExternalCircle();
    }

    private void initExternalCirclePainter() {
        internalCirclePaint = new Paint();
        internalCirclePaint.setAntiAlias(true);
        internalCirclePaint.setStrokeWidth(internalStrokeWidth);
        internalCirclePaint.setColor(color);
        internalCirclePaint.setStyle(Paint.Style.STROKE);
        internalCirclePaint.setPathEffect(new DashPathEffect(new float[]{dashWith, dashSpace},
                dashSpace));
    }

    private void initExternalCircle() {
        internalCircle = new RectF();
        float padding = internalStrokeWidth * 1.7f;
        internalCircle.set(padding, padding + marginTop, width - padding, height - padding);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawArc(internalCircle, startAngle, finishAngle, false, internalCirclePaint);
    }
}
