package com.qianfeng.wowsai.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Sky on 2015/4/30.
 */
public class CourseAllDetail_ViewPager_Adapter extends PagerAdapter{
    List<View> list=null;
    public CourseAllDetail_ViewPager_Adapter(List<View> list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View child = list.get(position);
        container.addView(child);
        return child;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    public void addList(List<View> totalList) {
        list.addAll(totalList);
        notifyDataSetChanged();
    }
}
