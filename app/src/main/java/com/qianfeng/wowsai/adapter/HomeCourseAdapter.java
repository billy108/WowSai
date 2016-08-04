package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.toolbox.ImageLoader;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class HomeCourseAdapter extends BaseAdapter {

    private List<Course> list;
    private Context context;
    private LayoutInflater inflater;
    private int width;
    private ImageLoader imageLoader;

    public HomeCourseAdapter(List<Course> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        width = (context.getResources().getDisplayMetrics().widthPixels-30)/2;
        imageLoader = AppCtx.getInstance().getImageLoader();
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
            convertView = inflater.inflate(R.layout.item_gridview_course_home,parent,false);

            convertView.setLayoutParams(new AbsListView.LayoutParams(width,width));

            viewHolder = new ViewHolder();

            viewHolder.imageView_cover = (ImageView) convertView.findViewById(R.id.imageView_item_homeCourse);

            viewHolder.textView_subject = (TextView) convertView.findViewById(R.id.textView_item_homeCourse_subject);

            viewHolder.textView_username = (TextView) convertView.findViewById(R.id.textView_item_homeCourse_username);

            viewHolder.imageView_head = (CircleImageView) convertView.findViewById(R.id.circleImageView_item_homeCourse_head);

//            viewHolder.imageView_cover.setLayoutParams(new LinearLayout.LayoutParams(width,width));

            viewHolder.relativeLayout_user = (LinearLayout) convertView.findViewById(R.id.relativeLayout_item_homeCourse_user);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Course course = list.get(position);
        final User user = course.getUser();


        viewHolder.textView_username.setText(user.getUser_name());
        viewHolder.textView_subject.setText(course.getSubject());
        Picasso.with(context).load(course.getCover_pic()).resize(width,width).centerInside().placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_steppic).into(viewHolder.imageView_cover);
//        Picasso.with(context).load(user.getHead_pic()).resize(20,20).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_steppic).into(viewHolder.imageView_head);
        imageLoader.get(user.getHead_pic(), ImageLoader.getImageListener(viewHolder.imageView_head, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
        viewHolder.relativeLayout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CourseUserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder{
        ImageView imageView_cover;
        TextView textView_subject;
        TextView textView_username;
        CircleImageView imageView_head;
        LinearLayout relativeLayout_user;
    }
}
