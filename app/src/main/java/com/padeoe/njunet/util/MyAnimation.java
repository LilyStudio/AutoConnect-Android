package com.padeoe.njunet.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

/**
 * Created by padeoe on 2016/4/26.
 */
public class MyAnimation {
    public static void flip(final View front, final View back, final int duration) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            AnimatorSet set = new AnimatorSet();
            set.playSequentially(
                    ObjectAnimator.ofFloat(front, "rotationY", 90).setDuration(duration / 2),
                    ObjectAnimator.ofInt(front, "visibility", View.GONE).setDuration(0),
                    ObjectAnimator.ofFloat(back, "rotationY", -90).setDuration(0),
                    ObjectAnimator.ofInt(back, "visibility", View.VISIBLE).setDuration(0),
                    ObjectAnimator.ofFloat(back, "rotationY", 0).setDuration(duration / 2)
            );
            set.start();
        } else {
            front.animate().rotationY(90).setDuration(duration / 2).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    front.setVisibility(View.GONE);
                    back.setRotationY(-90);
                    back.setVisibility(View.VISIBLE);
                    back.animate().rotationY(0).setDuration(duration / 2).setListener(null);
                }
            });
        }
    }

    public static void fadeInTextView(TextView textView) {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        textView.startAnimation(fadeIn);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);
    }

    public static void updateBackground(View view, int initColor, int endColor, int duration) {
        ColorDrawable[] color = {new ColorDrawable(initColor), new ColorDrawable(endColor)};
        TransitionDrawable trans = new TransitionDrawable(color);
        view.setBackgroundDrawable(trans);
        trans.startTransition(duration);
    }
}
