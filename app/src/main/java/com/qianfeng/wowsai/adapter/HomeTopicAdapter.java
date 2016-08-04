package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.find.TopicDetailActivity;
import com.qianfeng.wowsai.model.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class HomeTopicAdapter extends BaseAdapter {

    private List<Topic> list;

    private Context context;

    private LayoutInflater inflater;

    public HomeTopicAdapter(List<Topic> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_gridview_topic_home,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textView_subject = (TextView) convertView.findViewById(R.id.textView_item_topicHome_subject);
            viewHolder.imageViews[0] = (ImageView) convertView.findViewById(R.id.imageView_item_topicHome_pic0);
            viewHolder.imageViews[1] = (ImageView) convertView.findViewById(R.id.imageView_item_topicHome_pic1);
            viewHolder.imageViews[2] = (ImageView) convertView.findViewById(R.id.imageView_item_topicHome_pic2);
            viewHolder.imageViews[3] = (ImageView) convertView.findViewById(R.id.imageView_item_topicHome_pic3);
            viewHolder.imageViews[4] = (ImageView) convertView.findViewById(R.id.imageView_item_topicHome_pic4);

            for (int i = 0; i < viewHolder.imageViews.length; i++) {
                setHeight(viewHolder.imageViews[i],i);
            }

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Topic topic = list.get(position);
        final String topicSubject = topic.getSubject();
        viewHolder.textView_subject.setText(topicSubject);

        List<String> pics = topic.getPic();

        for (int i = 0; i < pics.size(); i++) {
            String pic = pics.get(i);
            Picasso.with(context).load(pic).resize(300,300).centerInside().placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).into(viewHolder.imageViews[i]);
        }


        return convertView;
    }


    private void setHeight(ImageView imageView,int position){
        int width = getWidth(position);
        int height = width;
        if (position == 0){
            height = height+2;
        }
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width,height));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private int getWidth(int position){
        int wholeWidth = context.getResources().getDisplayMetrics().widthPixels-25;
        if(position == 0){
            return wholeWidth/2;
        }else{
            return wholeWidth/4-1;
        }
    }

    class ViewHolder{
        TextView textView_subject;
        ImageView[] imageViews = new ImageView[5];
    }
}
