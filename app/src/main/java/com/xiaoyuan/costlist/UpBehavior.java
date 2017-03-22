package com.xiaoyuan.costlist;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * 防止SnackBar遮住底部文字
 * Created by yuan on 2017/3/22.
 */
public class UpBehavior extends CoordinatorLayout.Behavior {

    private  Point displaySize;

    public UpBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        displaySize = new Point();
        WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wmManager.getDefaultDisplay().getRealSize(displaySize);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        if (dependency.getX() == displaySize.x) {
            child.setTranslationY(0);
        }
        return true;
    }
}
