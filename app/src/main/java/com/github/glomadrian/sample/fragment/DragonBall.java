package com.github.glomadrian.sample.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.glomadrian.dashedcircularprogress.DashedCircularProgress;
import com.github.glomadrian.dashedcircularprogress.utils.ViewPagerTransformer;
import com.github.glomadrian.sample.R;

/**
 * @author Adrián García Lomas
 */
public class DragonBall extends Fragment {

    private View dragonBallView;
    private DashedCircularProgress dashedCircularProgress;
    private ViewPager viewPager;
    private CharFragment goku;
    private CharFragment vegeta;
    private CharFragment gohan;
    private CharFragment krillin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dragon_ball, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dashedCircularProgress = (DashedCircularProgress) view.
                findViewById(R.id.dragon_ball_progress);
        dragonBallView = view.findViewById(R.id.dragon_ball);
        viewPager = (ViewPager) view.findViewById(R.id.dragon_ball_pager);
        viewPager.setPageTransformer(true, new ViewPagerTransformer());
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        transform(900, goku.getPalette());
                        break;
                    case 1:
                        transform(600, vegeta.getPalette());
                        break;
                    case 2:
                        transform(950, gohan.getPalette());
                        break;
                    case 3:
                        transform(30, krillin.getPalette());
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Empty
            }
        });
    }

    private void transform(int value, Palette palette) {
        dashedCircularProgress.setValue(value);
        try {
            dashedCircularProgress.setExternalColor(palette.getPallete().get(5).getRgb());
            dashedCircularProgress.setProgressColor(palette.getVibrantColor().getRgb());
            animateBackgroundColor(palette.getDarkMutedColor().getRgb());

        } catch (NullPointerException e) {
            Log.e(DragonBall.class.getName(), "Error getting color from palette");
        }
    }

    private void animateBackgroundColor(int color) {
        int previousColor = Color.TRANSPARENT;
        Drawable background = dragonBallView.getBackground();

        if (background instanceof ColorDrawable)
            previousColor = ((ColorDrawable) background).getColor();

        final ObjectAnimator animator = ObjectAnimator
                .ofInt(dragonBallView, "backgroundColor",
                        previousColor, color)
                .setDuration(1000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    public static DragonBall getInstance() {
        return new DragonBall();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            goku = CharFragment.newInstance(R.drawable.goku);
            vegeta = CharFragment.newInstance(R.drawable.vegeta);
            gohan = CharFragment.newInstance(R.drawable.gohan);
            krillin = CharFragment.newInstance(R.drawable.krillin);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return goku;
                case 1:
                    return vegeta;
                case 2:
                    return gohan;
                case 3:
                    return krillin;
            }

            return CharFragment.newInstance(R.drawable.ic_launcher);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public static class CharFragment extends Fragment {

        public static final String IMAGE_ARG = "character_image";
        private ImageView charImageView;
        private int imageResource;
        private Palette palette;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.char_layout, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            getBundleArgs();
            charImageView = (ImageView) view.findViewById(R.id.char_image);
            charImageView.setImageResource(imageResource);
            palette = Palette.generate(((BitmapDrawable) charImageView.getDrawable()).getBitmap(),
                    32);
        }

        public Palette getPalette() {
            return palette;
        }

        public void getBundleArgs() {
            imageResource = getArguments().getInt(IMAGE_ARG);
        }

        public static CharFragment newInstance(int characterImage) {
            CharFragment charFragment = new CharFragment();
            Bundle args = new Bundle();
            args.putInt(IMAGE_ARG, characterImage);
            charFragment.setArguments(args);
            return charFragment;
        }
    }


}
