package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.tencent.qzone.TencentSSOClientNotInstalledException;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseAllDetailActivity;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.activity.course.EventDetailActivity;
import com.qianfeng.wowsai.model.Action;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by King
 * DATE 2015/5/4.
 */
public class ActionAdapter extends BaseAdapter {

    private Context context;
    private List<Action> list;
    private LayoutInflater inflater;

    public ActionAdapter(Context context, List<Action> list) {
        this.context = context;
        this.list = list;
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
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        Action action = list.get(position);
        int ret = 0;
        String type = action.getType();
        if (type.equals("course")){
            ret = 0;
        }else if (type.equals("follow")){
            ret = 1;
        }else if (type.equals("circle")){
            ret = 2;
        }
        return ret;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;
        final Action action = list.get(position);
        String type = action.getType();
        if (type.equals("course")){
            if (convertView!=null){
                ret = convertView;
            }else {
                ret = inflater.inflate(R.layout.item_listview_action_collect,parent,false);
            }
            CourseHolder holder = (CourseHolder) ret.getTag();
            if (holder==null){
                holder = new CourseHolder();
                holder.img_collect_head = (CircleImageView) ret.findViewById(R.id.img_action_collect_head);
                holder.img_course_head = (CircleImageView) ret.findViewById(R.id.img_action_course_head);
                holder.img_collect_cover = (ImageView) ret.findViewById(R.id.img_action_collect_cover);
                holder.tv_collect_username = (TextView) ret.findViewById(R.id.tv_action_collect_username);
                holder.tv_collect_action = (TextView) ret.findViewById(R.id.tv_action_collect_action);
                holder.tv_collect_title = (TextView) ret.findViewById(R.id.tv_action_collect_title);
                holder.tv_collect_user = (TextView) ret.findViewById(R.id.tv_action_collect_name);
                holder.ll_action_collect_course = (LinearLayout) ret.findViewById(R.id.ll_action_collect_course);
                holder.tv_collect_timeline = (TextView) ret.findViewById(R.id.tv_action_collect_timeline);
                ret.setTag(holder);
            }
            User user = action.getUser();
            if (user != null) {
                holder.tv_collect_username.setText(user.getUser_name());
                Picasso.with(context).load(user.getHead_pic()).placeholder(R.drawable.sgk_img_default_big).into(holder.img_collect_head);
            }
            holder.tv_collect_action.setText(action.getEvent_action());
            holder.tv_collect_timeline.setText(action.getTimeline());
            Course course = action.getCourse();
            if (course != null) {
                holder.tv_collect_title.setText(course.getSubject());
                Picasso.with(context).load(course.getCover_pic()).placeholder(R.drawable.sgk_img_default_big).into(holder.img_collect_cover);
                User user1 = course.getUser();
                if (user1 != null) {
                    holder.tv_collect_user.setText(user1.getUser_name());
                    Picasso.with(context).load(user1.getHead_pic()).placeholder(R.drawable.sgk_img_default_big).into(holder.img_course_head);
                }
            }
            addUserListener(holder.img_collect_head,position);
            holder.ll_action_collect_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseAllDetailActivity.class);
                    Bundle bundle = new Bundle();
                    String id = action.getCourse().getHand_id();
                    bundle.putString("id",id);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("follow")){
            if (convertView!=null){
                ret = convertView;
            }else {
                ret = inflater.inflate(R.layout.item_listview_action_guan,parent,false);
            }
            FollowHolder holder = (FollowHolder) ret.getTag();
            if (holder==null){
                holder = new FollowHolder();
                holder.img_guan_head  = (CircleImageView) ret.findViewById(R.id.img_action_guan_head);
                holder.img_follow_head = (CircleImageView) ret.findViewById(R.id.img_action_follow_head);
                holder.tv_follow_username = (TextView) ret.findViewById(R.id.tv_action_guan_username);
                holder.tv_fllow_action = (TextView) ret.findViewById(R.id.tv_action_guan_action);
                holder.tv_action_guan_name = (TextView) ret.findViewById(R.id.tv_action_guan_name);
                holder.tv_guan_timeline = (TextView) ret.findViewById(R.id.tv_action_guan_timeline);
                holder.ll_action_guan_course = (LinearLayout) ret.findViewById(R.id.ll_action_guan_course);
                ret.setTag(holder);
            }
            holder.tv_guan_timeline.setText(action.getTimeline());
            holder.tv_fllow_action.setText(action.getEvent_action());
            final User user = action.getUser();
            if (user != null) {
                Picasso.with(context).load(user.getHead_pic())
                        .placeholder(R.drawable.sgk_img_default_big).into(holder.img_guan_head);
                holder.tv_follow_username.setText(user.getUser_name());
            }
            User follow = action.getFollow();
            if (follow != null) {
                Picasso.with(context).load(follow.getHead_pic())
                        .placeholder(R.drawable.sgk_img_default_big).into(holder.img_follow_head);
                holder.tv_action_guan_name.setText(follow.getUser_name());
            }
            addUserListener(holder.img_guan_head,position);
            holder.ll_action_guan_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user1 = action.getFollow();
                    Intent intent = new Intent(context,CourseUserDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",user1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }else if (type.equals("circle")){
            if (convertView != null) {
                ret = convertView;
            }else {
                ret = inflater.inflate(R.layout.item_listview_action_laud,parent,false);
            }
            CircleHolder holder = (CircleHolder) ret.getTag();
            if (holder == null) {
                holder = new CircleHolder();
                holder.img_circle_head = (CircleImageView) ret.findViewById(R.id.img_action_circle_head);
                holder.img_laud_cover = (ImageView) ret.findViewById(R.id.img_action_laud_cover);
                holder.img_laud_head = (CircleImageView) ret.findViewById(R.id.img_action_laud_head);
                holder.tv_laud_username = (TextView) ret.findViewById(R.id.tv_action_laud_username);
                holder.tv_laud_user = (TextView) ret.findViewById(R.id.tv_action_laud_name);
                holder.tv_laud_action = (TextView) ret.findViewById(R.id.tv_action_laud_action);
                holder.tv_laud_title = (TextView) ret.findViewById(R.id.tv_action_laud_title);
                holder.tv_laud_timeline = (TextView) ret.findViewById(R.id.tv_action_laud_timeline);
                holder.ll_action_laud_course = (LinearLayout) ret.findViewById(R.id.ll_action_laud_course);
                ret.setTag(holder);
            }
            holder.tv_laud_timeline.setText(action.getTimeline());
            holder.tv_laud_action.setText(action.getEvent_action());
            User user = action.getUser();
            if (user != null) {
                holder.tv_laud_username.setText(user.getUser_name());
                Picasso.with(context).load(user.getHead_pic())
                        .placeholder(R.drawable.sgk_img_default_big).into(holder.img_laud_head);
            }
            HandCircle circle = action.getCircle();
            if (circle != null) {
                holder.tv_laud_title.setText(circle.getSubject());
                Picasso.with(context).load(circle.getHost_pic())
                        .placeholder(R.drawable.sgk_img_default_big).into(holder.img_laud_cover);
                User user1 = circle.getUser();
                if (user1 != null) {
                    holder.tv_laud_user.setText(user1.getUser_name());
                    Picasso.with(context).load(user1.getHead_pic())
                            .placeholder(R.drawable.sgk_img_default_big).into(holder.img_circle_head);
                }
            }
            addUserListener(holder.img_laud_head,position);
            holder.ll_action_laud_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    String item_id = action.getCircle().getHand_id();
                    Log.i("------>","------>"+item_id);
                    Bundle bundle = new Bundle();
                    bundle.putString("type",2+"");
                    bundle.putString("item_id",item_id);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        return ret;
    }

    private class CourseHolder{
        private CircleImageView img_collect_head;
        private CircleImageView img_course_head;
        private TextView tv_collect_username;
        private TextView tv_collect_action;
        private ImageView img_collect_cover;
        private TextView tv_collect_title;
        private TextView tv_collect_user;
        private TextView tv_collect_timeline;
        private LinearLayout ll_action_collect_course;
    }

    private class CircleHolder{
        private CircleImageView img_laud_head;
        private CircleImageView img_circle_head;
        private TextView tv_laud_username;
        private TextView tv_laud_action;
        private ImageView img_laud_cover;
        private TextView tv_laud_title;
        private TextView tv_laud_user;
        private TextView tv_laud_timeline;
        private LinearLayout ll_action_laud_course;
    }

    private class FollowHolder{
        private CircleImageView img_guan_head;
        private CircleImageView img_follow_head;
        private TextView tv_follow_username;
        private TextView tv_fllow_action;
        private TextView tv_action_guan_name;
        private TextView tv_guan_timeline;
        private LinearLayout ll_action_guan_course;
    }

    private void addUserListener(View v, final int position){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = list.get(position).getUser();
                Intent intent = new Intent(context, CourseUserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

}
