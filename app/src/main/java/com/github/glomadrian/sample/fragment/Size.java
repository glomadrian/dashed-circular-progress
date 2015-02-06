package com.github.glomadrian.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.github.glomadrian.dashedcircularprogress.DashedCircularProgress;
import com.github.glomadrian.sample.R;

/**
 * @author Adrián García Lomas
 */
public class Size extends Fragment {

    private Button restartButton;
    private DashedCircularProgress dashedCircularProgress;
    private ImageView androidImage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.size, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dashedCircularProgress = (DashedCircularProgress) view.findViewById(R.id.size);
        androidImage = (ImageView) view.findViewById(R.id.android_image);
        restartButton = (Button) view.findViewById(R.id.restart);
        dashedCircularProgress.setInterpolator(new AccelerateInterpolator());
        dashedCircularProgress.setValue(5);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
            }
        });

        dashedCircularProgress.setOnValueChangeListener(new DashedCircularProgress.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                androidImage.setScaleX(dashedCircularProgress.getScaleX() + value / 3);
                androidImage.setScaleY(dashedCircularProgress.getScaleY() + value / 3);
            }
        });

    }

    private void animate() {
        dashedCircularProgress.reset();
        dashedCircularProgress.setValue(5);
    }

    public static Size getInstance() {
        return new Size();
    }
}
