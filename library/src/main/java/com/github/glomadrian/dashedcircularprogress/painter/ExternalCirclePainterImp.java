package com.github.glomadrian.dashedcircularprogress.painter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * @author Adrián García Lomas
 */
public class ExternalCirclePainterImp implements ExternalCirclePainter {

    private RectF externalCircle;
    private Paint externalCirclePaint;
    private int color;
    private int externalStrokeWidth = 4;
    private int startAngle = 279;
    private int finishAngle = 341;
    private int width;
    private int height;
    private float marginTop = 11;

    public ExternalCirclePainterImp(int externalColor) {
        this.color = externalColor;
        init();
    }

    private void init() {
        initExternalCirclePainter();
    }

    private void initExternalCirclePainter() {
        externalCirclePaint = new Paint();
        externalCirclePaint.setAntiAlias(true);
        externalCirclePaint.setStrokeWidth(externalStrokeWidth);
        externalCirclePaint.setColor(color);
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

    @Override
    public void setColor(int color) {
        this.color = color;
        externalCirclePaint.setColor(color);
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void onSizeChanged(int height, int width) {
        this.width = width;
        this.height = height;
        initExternalCircle();
    }

}
