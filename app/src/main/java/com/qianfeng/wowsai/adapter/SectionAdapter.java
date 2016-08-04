package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseAllDetailActivity;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.activity.course.EventDetailActivity;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/4
 */
public class SectionAdapter extends SectionedBaseAdapter {
    private List<Map<String, Object>> list;
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public SectionAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = AppCtx.getInstance().getImageLoader();
    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return 3;
    }

    @Override
    public int getCountForSection(int section) {
        return 3;
    }

    @Override
    public int getItemViewType(int section, int position) {
        int ret = 0;
        String cate = list.get(section).get("cate").toString();
        switch (cate) {
            case "course":
                ret = 0;
                break;
            case "user":
                ret = 1;
                break;
            case "circle":
                ret = 2;
                break;
        }
        return ret;
    }

    @Override
    public int getItemViewTypeCount() {
        return 3;
    }

    @Override
    public View getItemView(int section, final int position, View convertView, ViewGroup parent) {
        View ret = null;

        String cate = list.get(section).get("cate").toString();
        //用户
        if (cate.equals("user")) {

            if (convertView != null) {
                ret = convertView;
            } else {
                ret = inflater.inflate(R.layout.item_listview_search_user, parent, false);
            }

            UserView holder = (UserView) ret.getTag();
            if (holder == null) {
                holder = new UserView();
                holder.imageView_search_user = (ImageView) ret.findViewById(R.id.imageView_search_user);
                holder.imageView_search_user_vip = (ImageView) ret.findViewById(R.id.imageView_search_user_vip);
                holder.imageView_search_user_guanzhu = (ImageView) ret.findViewById(R.id.imageView_search_user_guanzhu);
                holder.textView_search_user_name = (TextView) ret.findViewById(R.id.textView_search_user_name);
                holder.linearLayout_search_user_more = (LinearLayout) ret.findViewById(R.id.linearLayout_search_user_more);
                ret.setTag(holder);
            }

            final List<User> userList = (List<User>) list.get(section).get("userList");
            imageLoader.get(userList.get(position).getHead_pic(), ImageLoader.getImageListener(holder.imageView_search_user, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
            if (userList.get(position).getDaren().equals("1")) {
                holder.imageView_search_user_vip.setVisibility(View.VISIBLE);
            } else {
                holder.imageView_search_user_vip.setVisibility(View.GONE);
            }
            holder.textView_search_user_name.setText(userList.get(position).getUser_name());
            holder.textView_search_user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseUserDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", userList.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            if (section == 1 && position == 2){
                holder.linearLayout_search_user_more.setVisibility(View.VISIBLE);
            }else{
                holder.linearLayout_search_user_more.setVisibility(View.GONE);
            }

        }
        //教程
        else if (cate.equals("course")) {

            if (convertView != null) {
                ret = convertView;
            } else {
                ret = inflater.inflate(R.layout.item_listview_course, parent, false);
            }

            CourseView holder = (CourseView) ret.getTag();
            if (holder == null) {
                holder = new CourseView();
                holder.imageView_search_course_pic = (ImageView) ret.findViewById(R.id.imageView_search_course_pic);
                holder.textView_search_course_name = (TextView) ret.findViewById(R.id.textView_search_course_name);
                holder.textView_search_course_time = (TextView) ret.findViewById(R.id.textView_search_course_time);
                holder.textView_search_course_user = (TextView) ret.findViewById(R.id.textView_search_course_user);
                holder.linearLayout_search_course = (LinearLayout) ret.findViewById(R.id.linearLayout_search_course);
                holder.linearLayout_search_course_more = (LinearLayout) ret.findViewById(R.id.linearLayout_search_course_more);
                ret.setTag(holder);
            }

            final List<Course> courseList = (List<Course>) list.get(section).get("courseList");
            imageLoader.get(courseList.get(position).getCover_pic(), ImageLoader.getImageListener(holder.imageView_search_course_pic, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
            holder.textView_search_course_name.setText(courseList.get(position).getSubject());
            holder.textView_search_course_user.setText("by " + courseList.get(position).getUser().getUser_name());
            holder.textView_search_course_time.setText(courseList.get(position).getAdd_time());
            holder.linearLayout_search_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseAllDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", courseList.get(position).getHand_id());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            if (section == 0 && position == 2){
                holder.linearLayout_search_course_more.setVisibility(View.VISIBLE);
            }else{
                holder.linearLayout_search_course_more.setVisibility(View.GONE);
            }

        }
        //手工圈
        else if (cate.equals("circle")) {

            if (convertView != null) {
                ret = convertView;
            } else {
                ret = inflater.inflate(R.layout.item_listview_circle, parent, false);
            }

            CircleView holder = (CircleView) ret.getTag();
            if (holder == null) {
                holder = new CircleView();
                holder.imageView_search_circle_pic = (ImageView) ret.findViewById(R.id.imageView_search_circle_pic);
                holder.textView_search_circle_subject = (TextView) ret.findViewById(R.id.textView_search_circle_subject);
                holder.textView_search_circle_time = (TextView) ret.findViewById(R.id.textView_search_circle_time);
                holder.textView_search_circle_user = (TextView) ret.findViewById(R.id.textView_search_circle_user);
                holder.linearLayout_search_circle = (LinearLayout) ret.findViewById(R.id.linearLayout_search_circle);
                holder.linearLayout_search_circle_more = (LinearLayout) ret.findViewById(R.id.linearLayout_search_circle_more);
                ret.setTag(holder);
            }

            final List<HandCircle> circleList = (List<HandCircle>) list.get(section).get("circleList");
            imageLoader.get(circleList.get(position).getHost_pic(), ImageLoader.getImageListener(holder.imageView_search_circle_pic, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
            holder.textView_search_circle_subject.setText(circleList.get(position).getSubject());
            holder.textView_search_circle_time.setText(circleList.get(position).getAdd_time());
            holder.textView_search_circle_user.setText(circleList.get(position).getUser().getUser_name());
            holder.linearLayout_search_circle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("item_id",circleList.get(position).getHand_id());
                    bundle.putString("type","0");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            if (section == 2 && position == 2){
                holder.linearLayout_search_circle_more.setVisibility(View.VISIBLE);
            }else{
                holder.linearLayout_search_circle_more.setVisibility(View.GONE);
            }
        }
        return ret;

    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        View ret;
        if (convertView == null) {
            ret = inflater.inflate(R.layout.item_listview_section, parent, false);
        } else {
            ret = convertView;
        }

        HeaderView holder = (HeaderView) ret.getTag();
        if (holder == null) {
            holder = new HeaderView();
            holder.textView_search_section = (TextView) ret.findViewById(R.id.textView_search_section);
            ret.setTag(holder);
        }

        holder.textView_search_section.setText(list.get(section).get("title").toString());

        return ret;
    }

    static class HeaderView {
        TextView textView_search_section;
    }

    static class UserView {
        ImageView imageView_search_user;
        ImageView imageView_search_user_vip;
        ImageView imageView_search_user_guanzhu;
        TextView textView_search_user_name;
        LinearLayout linearLayout_search_user_more;
    }

    static class CourseView {
        ImageView imageView_search_course_pic;
        TextView textView_search_course_name;
        TextView textView_search_course_user;
        TextView textView_search_course_time;
        LinearLayout linearLayout_search_course;
        LinearLayout linearLayout_search_course_more;
    }

    static class CircleView {
        TextView textView_search_circle_user;
        TextView textView_search_circle_subject;
        TextView textView_search_circle_time;
        ImageView imageView_search_circle_pic;
        LinearLayout linearLayout_search_circle;
        LinearLayout linearLayout_search_circle_more;
    }
}