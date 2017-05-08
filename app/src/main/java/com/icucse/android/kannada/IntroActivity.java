package com.icucse.android.kannada;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Kannada for Noobs",
                "Kannada in your fingertips",R.drawable.kannada_logo,
                Color.argb(1,200,120,20)));

        addSlide(AppIntroFragment.newInstance("Colours",
                "Learn about basic colours",R.drawable.color_red,
                Color.argb(1,210,130,35)));

        addSlide(AppIntroFragment.newInstance("Family",
                "Learn about family members and many more things",
                R.drawable.family_father, Color.argb(1,255,146,50)));

        setFadeAnimation();


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
