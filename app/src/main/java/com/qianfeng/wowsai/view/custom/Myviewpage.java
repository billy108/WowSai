package com.qianfeng.wowsai.view.custom;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class Myviewpage extends ViewPager {
    PointF downP = new PointF();
    PointF curP = new PointF();
    OnSingleTouchListener onSingleTouchListener;


    public Myviewpage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Myviewpage(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {

        getParent().requestDisallowInterceptTouchEvent(true);

        int x, y;
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {

            downP.x = arg0.getX();
            downP.y = arg0.getY();
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
            x = (int)(curP.x - downP.x);
            y = (int)(curP.y - downP.y);
            int count = this.getAdapter().getCount()-1;
            if (Math.abs(x) > Math.abs(y)) {
                if (x > 0 && getCurrentItem() == 0) {
                    //getParent().requestDisallowInterceptTouchEvent(false);
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else if (x < 0 && getCurrentItem() == count) {//
                    //getParent().requestDisallowInterceptTouchEvent(false);
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            }else if(Math.abs(x) < Math.abs(y)) {

            }

        }

        if (arg0.getAction() == MotionEvent.ACTION_UP) {
            if (downP.x == curP.x && downP.y == curP.y) {
                onSingleTouch();
                return false;
            }
        }

        return super.onTouchEvent(arg0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListener(
            OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

}