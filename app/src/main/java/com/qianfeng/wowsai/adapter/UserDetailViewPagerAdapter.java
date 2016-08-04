package com.qianfeng.wowsai.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Sky on 2015/5/2.
 */
public class UserDetailViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> totalList;
    public UserDetailViewPagerAdapter(FragmentManager fm,List<Fragment> totalList) {
        super(fm);
        this.totalList=totalList;
    }
    @Override
    public android.support.v4.app.Fragment getItem(int i) {
        return totalList.get(i);
    }
    @Override
    public int getCount() {
        return totalList.size();
    }
    public void addList(List<Fragment> list){
        totalList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
