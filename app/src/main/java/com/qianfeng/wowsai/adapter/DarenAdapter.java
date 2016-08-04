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
import com.qianfeng.wowsai.activity.course.CourseAllDetailActivity;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by King
 * DATE 2015/5/3.
 */
public class DarenAdapter extends BaseAdapter {

    private List<User> list;
    private Context context;
    private LayoutInflater inflater;

    public DarenAdapter(List<User> list, Context context) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listview_daren, parent, false);
            holder = new ViewHolder();
            holder.user_container = (LinearLayout) convertView.findViewById(R.id.item_daren_user_container);
            holder.head_img = (CircleImageView) convertView.findViewById(R.id.imageview_daren_head);
            holder.tv_username = (TextView) convertView.findViewById(R.id.item_daren_username);
            holder.tv_circle_count = (TextView) convertView.findViewById(R.id.item_daren_circle_count);
            holder.tv_course_count = (TextView) convertView.findViewById(R.id.item_daren_course_count);
            holder.img_course1 = (ImageView) convertView.findViewById(R.id.item_daren_course1);
            setImageHeight(holder.img_course1);
            holder.img_course2 = (ImageView) convertView.findViewById(R.id.item_daren_course2);
            setImageHeight(holder.img_course2);
            holder.img_course3 = (ImageView) convertView.findViewById(R.id.item_daren_course3);
            setImageHeight(holder.img_course3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = list.get(position);
        Picasso.with(context).load(user.getHead_pic()).placeholder(R.drawable.sgk_img_default_big).into(holder.head_img);
        holder.tv_username.setText(user.getUser_name());
        holder.tv_course_count.setText(user.getCourse_count());
        holder.tv_circle_count.setText(user.getOpus_count());
        List<Course> releaseCourseList = user.getReleaseCourseList();
        if (releaseCourseList != null && releaseCourseList.size() >= 3) {
            Picasso.with(context).load(releaseCourseList.get(0).getCover_pic()).placeholder(R.drawable.sgk_img_default_big).into(holder.img_course1);
            Picasso.with(context).load(releaseCourseList.get(1).getCover_pic()).placeholder(R.drawable.sgk_img_default_big).into(holder.img_course2);
            Picasso.with(context).load(releaseCourseList.get(2).getCover_pic()).placeholder(R.drawable.sgk_img_default_big).into(holder.img_course3);
        }
        addListener(holder.img_course1,position,0);
        addListener(holder.img_course2,position,1);
        addListener(holder.img_course3,position,2);
        setUserListener(holder.user_container,position);
        return convertView;
    }

    private class ViewHolder {
        private CircleImageView head_img;
        private TextView tv_username;
        private TextView tv_course_count;
        private TextView tv_circle_count;
        private ImageView img_course1;
        private ImageView img_course2;
        private ImageView img_course3;
        private LinearLayout user_container;
    }

    private void addListener(View v, final int position, final int position2) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseAllDetailActivity.class);
                Bundle bundle = new Bundle();
                String id = list.get(position).getReleaseCourseList().get(position2).getHand_id();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    private void setImageHeight(View view){
        int width = context.getResources().getDisplayMetrics().widthPixels/3;
        int height = width;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
        view.setLayoutParams(layoutParams);
    }

    private void setUserListener(View v, final int position){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = list.get(position);
                Intent intent = new Intent(context, CourseUserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

}
