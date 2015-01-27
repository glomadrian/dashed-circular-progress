package com.github.glomadrian.circularanimatedprogressbar.painter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Adrián García Lomas
 */
public class ExternalCirclePainter implements Painter {

    //TODO expose in constructor
    private RectF externalCircle;
    private Paint externalCirclePaint;
    private int externalColor;
    private int externalStrokeWidth = 4;
    private int startAngle = 279;
    private int finishAngle = 341;
    private int width;
    private int height;
    private float marginTop = 11;

    public ExternalCirclePainter(int externalColor, int width, int height) {
        this.externalColor = externalColor;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        initExternalCirclePainter();
        initExternalCircle();
    }

    private void initExternalCirclePainter() {
        externalCirclePaint = new Paint();
        externalCirclePaint.setAntiAlias(true);
        externalCirclePaint.setStrokeWidth(externalStrokeWidth);
        externalCirclePaint.setColor(externalColor);
        externalCirclePaint.setStyle(Paint.Style.STROKE);
    }

    private void initExternalCircle() {
        externalCircle = new RectF();
        externalCircle.set(externalStrokeWidth, externalStrokeWidth * marginTop,
                width - externalStrokeWidth, height - externalStrokeWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawArc(externalCircle, startAngle, finishAngle, false, externalCirclePaint);
    }
}
