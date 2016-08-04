package com.qianfeng.wowsai.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import m.framework.ui.widget.viewpager.ViewPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/5/3.
 */
public class CustomCyclePagerAdapter extends PagerAdapter {

    private List<View> list;

    public CustomCyclePagerAdapter(List<View> list) {
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % list.size();
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position%list.size()));
    }

}
