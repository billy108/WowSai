package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class HandCircleAdapter extends BaseAdapter {

    private List<HandCircle> list;
    private Context context;
    private LayoutInflater inflater;
    private int width;

    public HandCircleAdapter(List<HandCircle> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        width = (context.getResources().getDisplayMetrics().widthPixels-30)/2;
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
            convertView = inflater.inflate(R.layout.item_gridview_handcircle,parent,false);

            viewHolder = new ViewHolder();

            viewHolder.imageView_cover = (ImageView) convertView.findViewById(R.id.imageView_item_handCircle);

            viewHolder.textView_subject = (TextView) convertView.findViewById(R.id.textView_item_handCircle_subject);

            viewHolder.textView_username = (TextView) convertView.findViewById(R.id.textView_item_handCircle_username);

            viewHolder.textView_vote = (TextView) convertView.findViewById(R.id.textView_item_handCircle_vote);

            viewHolder.imageView_head = (CircleImageView) convertView.findViewById(R.id.circleImageView_item_handCircle_head);

            viewHolder.imageView_cover.setLayoutParams(new LinearLayout.LayoutParams(width,width));

            viewHolder.linearLayout_vote = (LinearLayout) convertView.findViewById(R.id.linearLayout_item_handCircle_vote);

            viewHolder.relativeLayout_user = (RelativeLayout) convertView.findViewById(R.id.relativeLayout_item_handCircle_user);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HandCircle handCircle = list.get(position);
        final User user = handCircle.getUser();

        String vote = handCircle.getVotes();
        if(vote == null){
            viewHolder.linearLayout_vote.setVisibility(View.GONE);
        }else{
            viewHolder.linearLayout_vote.setVisibility(View.VISIBLE);
            viewHolder.textView_vote.setText(vote);
        }
        viewHolder.textView_username.setText(user.getUser_name());
        viewHolder.textView_subject.setText(handCircle.getSubject());
        Picasso.with(context).load(handCircle.getHost_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_steppic).into(viewHolder.imageView_cover);
        Picasso.with(context).load(user.getHead_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_steppic).into(viewHolder.imageView_head);
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
        TextView textView_vote;
        TextView textView_username;
        CircleImageView imageView_head;
        LinearLayout linearLayout_vote;
        RelativeLayout relativeLayout_user;
    }
}
