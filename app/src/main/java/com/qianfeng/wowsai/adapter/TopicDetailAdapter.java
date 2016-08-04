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
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseAllDetailActivity;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.Topic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class TopicDetailAdapter extends BaseAdapter {

    private List<Course> list;
    private Context context;
    private LayoutInflater inflater;
    private int width;

    public TopicDetailAdapter(List<Course> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        width = context.getResources().getDisplayMetrics().widthPixels/2;
    }

    @Override
    public int getCount() {
        return list.size() / 2;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position * 2);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_topicdetail, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView_item_topicDetail_img1);
            viewHolder.imageView2 = (ImageView) convertView.findViewById(R.id.imageView_item_topicDetail_img2);
            viewHolder.imageView1.setLayoutParams(new LinearLayout.LayoutParams(width,width));
            viewHolder.imageView2.setLayoutParams(new LinearLayout.LayoutParams(width,width));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Course course1 = list.get(position * 2);
        final Course course2 = list.get(position * 2 + 1);



        Picasso.with(context).load(course1.getCover_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).into(viewHolder.imageView1);
        Picasso.with(context).load(course2.getCover_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).into(viewHolder.imageView2);

        viewHolder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CourseAllDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",course1.getHand_id());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
        viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CourseAllDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",course2.getHand_id());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        ImageView imageView1;
        ImageView imageView2;
    }
}
