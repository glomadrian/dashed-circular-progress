package com.github.glomadrian.dashedcircularprogress.painter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * @author Adrián García Lomas
 */
public class IconPainter implements Painter {

    private Bitmap image;
    private int centreX;
    private int centreY;
    private int width;
    private int height;
    private int imageWith;
    private int imageHeihgt;

    public IconPainter(Bitmap image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
//        this.centreX = (width - image.getWidth()) / 2;
//        this.centreY = (height - image.getHeight()) / 2;
        this.centreX = (width - 64) / 2;
        this.centreY = (height - 64) / 2;
    }

    private void calculateImageDimmensions() {
        imageHeihgt = height / 11;
        imageWith = width / 11;
        image = Bitmap.createScaledBitmap(image, 64, 64, false);
    }

    @Override
    public void draw(Canvas canvas) {
        calculateImageDimmensions();

        canvas.drawBitmap(image, centreX, 0, new Paint());
    }

    @Override
    public void setColor(int color) {
        //Empty
    }

    @Override
    public int getColor() {
        return 0;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
