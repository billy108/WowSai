package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sky on 2015/5/4.
 */
public class EventDetailLaudPersonAdapter extends MyBaseAdapter {
    Context context;
    List<User> totalList;

    public EventDetailLaudPersonAdapter(Context context, List<User> totalList) {
        this.context = context;
        this.totalList = totalList;
    }

    @Override
    public void addList(List list) {
        if (list!=null) {
            this.totalList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return totalList.size();
    }

    @Override
    public Object getItem(int i) {
        return totalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view=(new CircleImageView(context));
        }
        Picasso.with(context).load(totalList.get(i).getHead_pic()).resize(40,40).into((CircleImageView) view);
        return view;
    }
}
