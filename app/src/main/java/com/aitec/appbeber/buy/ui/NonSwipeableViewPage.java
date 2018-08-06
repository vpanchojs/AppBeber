package com.aitec.appbeber.buy.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by victor on 22/9/17.
 */

public class NonSwipeableViewPage extends ViewPager {

    public NonSwipeableViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonSwipeableViewPage(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
