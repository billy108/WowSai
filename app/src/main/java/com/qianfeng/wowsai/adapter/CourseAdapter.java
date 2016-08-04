package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2015/4/30.
 */
public class CourseAdapter extends BaseAdapter {
    private List<Course> list;

    private Context context;

    private LayoutInflater inflater;

    public CourseAdapter(List<Course> list, Context context) {
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
            convertView = inflater.inflate(R.layout.item_coursefragment,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView_host = (ImageView) convertView.findViewById(R.id.networkImageView_courseFragment_host);
            viewHolder.imageView_user = (CircleImageView) convertView.findViewById(R.id.circleImageView_item_courseFragment_head);
            viewHolder.textView_otherInfo = (TextView) convertView.findViewById(R.id.textView_item_courseFragment_otherInfo);
            viewHolder.textView_subject = (TextView) convertView.findViewById(R.id.textView_item_courseFragment_subject);
            viewHolder.textView_username = (TextView) convertView.findViewById(R.id.textView_item_courseFragment_username);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Course course = list.get(position);

        final User user = course.getUser();

        viewHolder.textView_username.setText(user.getUser_name());
        Picasso.with(context).load(user.getHead_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).into(viewHolder.imageView_user);
        Picasso.with(context).load(course.getCover_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).into(viewHolder.imageView_host);
        viewHolder.textView_subject.setText(course.getSubject());
        String otherInfo = course.getView_num()+"人气/"+course.getLaud()+"赞/"+course.getCollect_num()+"收藏/"+course.getComment_num()+"评论";
        viewHolder.textView_otherInfo.setText(otherInfo);
        viewHolder.imageView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                intent.setClass(context, CourseUserDetailActivity.class);
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView textView_username;
        CircleImageView imageView_user;
        ImageView imageView_host;
        TextView textView_subject;
        TextView textView_otherInfo;
    }
}
