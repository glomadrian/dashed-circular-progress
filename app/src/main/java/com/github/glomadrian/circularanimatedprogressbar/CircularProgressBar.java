package com.github.glomadrian.circularanimatedprogressbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.github.glomadrian.circularanimatedprogressbar.painter.ExternalCirclePainter;
import com.github.glomadrian.circularanimatedprogressbar.painter.IconPainter;
import com.github.glomadrian.circularanimatedprogressbar.painter.InternalCirclePainter;
import com.github.glomadrian.circularanimatedprogressbar.painter.InternalCircleProgress;
import com.github.glomadrian.circularanimatedprogressbar.painter.Painter;

/**
 * @author Adrián García Lomas
 */
public class CircularProgressBar extends RelativeLayout {

    private Painter externalCirclePainter;
    private Painter internalCirclePainter;
    private InternalCircleProgress internalCircleProgressPainter;
    private Painter iconPainter;
    private Bitmap image;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private int externalColor = Color.WHITE;
    private int internalBaseColor = Color.YELLOW;
    private int progressColor = Color.WHITE;
    private float min = 0;
    private float max = 100;
    private ValueAnimator valueAnimator;
    private OnValueChangeListener valueChangeListener;
    private float value;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            int marginLeft = (getWidth() - child.getWidth()) / 2;
            int marginRight = marginLeft - marginLeft + child.getWidth();
            int marginTop = (getHeight() - child.getHeight()) / 2;
            int marginBottom = marginTop + child.getHeight();
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            p.setMargins(marginLeft, marginTop + (25), 0, 0);
            child.requestLayout();
        }
    }

    private static void centerHorizontal(View child, LayoutParams params, int myWidth) {
        int childWidth = child.getMeasuredWidth();
        int left = (myWidth - childWidth) / 2;

        params.leftMargin = left;
        params.rightMargin = left + childWidth;
    }

    private static void centerVertical(View child, LayoutParams params, int myHeight) {
        int childHeight = child.getMeasuredHeight();
        int top = (myHeight - childHeight) / 2;

        params.topMargin = top;
        params.bottomMargin = top + childHeight;
    }

    private void init(Context context, AttributeSet attributeSet) {
        setWillNotDraw(false);
        TypedArray attributes = context.obtainStyledAttributes(attributeSet,
                R.styleable.CircularProgressBar);
        initAttributes(attributes);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.running);

    }

    private void initAttributes(TypedArray attributes) {
        externalColor = attributes.getColor(R.styleable.CircularProgressBar_external_color,
                externalColor);
        internalBaseColor = attributes.getColor(R.styleable.CircularProgressBar_base_color,
                internalBaseColor);
        progressColor = attributes.getColor(R.styleable.CircularProgressBar_progress_color,
                progressColor);
        max = attributes.getFloat(R.styleable.CircularProgressBar_max,
                max);
        min = attributes.getFloat(R.styleable.CircularProgressBar_min,
                min);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        externalCirclePainter = new ExternalCirclePainter(externalColor, getWidth(), getHeight());
        internalCirclePainter = new InternalCirclePainter(internalBaseColor, getWidth(),
                getHeight());
        internalCircleProgressPainter = new InternalCircleProgress(progressColor, min, max, w, h);
        iconPainter = new IconPainter(image, w, h);
        initValueAnimator();
        animateValue();
    }

    private void initValueAnimator() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimatorListenerImp());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        externalCirclePainter.draw(canvas);
        internalCirclePainter.draw(canvas);
        internalCircleProgressPainter.draw(canvas);
        iconPainter.draw(canvas);
        invalidate();
    }

    public void setValue(float value) {
        this.value = value;
        if (value <= max || value >= min) {
            animateValue();
        }
    }

    private void animateValue() {
        if (valueAnimator != null) {
            valueAnimator.setFloatValues(min, value);
            valueAnimator.start();
        }
    }

    public void setOnValueChangeListener(OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;

        if (valueAnimator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
        internalCircleProgressPainter.setMin(min);
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
        internalCircleProgressPainter.setMin(max);
    }

    private class ValueAnimatorListenerImp implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Float value = (Float) valueAnimator.getAnimatedValue();
            internalCircleProgressPainter.setValue(value);
            if (valueChangeListener != null) {
                valueChangeListener.onValueChange(value);
            }
        }
    }

    public interface OnValueChangeListener {
        void onValueChange(float value);
    }
}
