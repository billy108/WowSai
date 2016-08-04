package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.qianfeng.wowsai.activity.rankingList.RankListAct;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/30
 */

public class RankInfoAdapter extends BaseAdapter {
    private List<Map<String, Object>> rankList;
    private Context context;
    private LayoutInflater inflater;

    public RankInfoAdapter(List<Map<String, Object>> rankList, Context context) {
        this.rankList = rankList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (rankList != null) {
            ret = rankList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return rankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View ret;
        final Map<String, Object> map = rankList.get(position);

        if (convertView != null) {
            ret = convertView;
        } else {
            ret = inflater.inflate(R.layout.item_listview_rank, parent, false);
        }

        ViewHolder holder = (ViewHolder) ret.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.linearLayout_rank_more = (LinearLayout) ret.findViewById(R.id.linearLayout_rank_more);
            holder.textView_rank_title = (TextView) ret.findViewById(R.id.textView_rank_title);
            holder.linearLayout_rank_show = (LinearLayout) ret.findViewById(R.id.linearLayout_rank_show);
            holder.imageView_rank_icon = (ImageView) ret.findViewById(R.id.imageView_rank_icon);
            ret.setTag(holder);
        }

        holder.linearLayout_rank_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RankListAct.class);
                Bundle bundle = new Bundle();
                bundle.putString("a",map.get("a").toString());
                bundle.putString("id",map.get("id").toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.textView_rank_title.setText(map.get("title").toString());

        // 显示Icon
        if (map.get("key").equals("all")) {
            holder.imageView_rank_icon.setImageResource(R.drawable.rank_a);
            holder.textView_rank_title.setTextColor(context.getResources().getColor(R.color.orange));
        } else {
            holder.imageView_rank_icon.setImageResource(R.drawable.rank_m);
            holder.textView_rank_title.setTextColor(context.getResources().getColor(R.color.green));
        }

        //清空用于展示的LinearLayout
        holder.linearLayout_rank_show.removeAllViews();

        //设置Course内容
        if (map.get("a").equals("course")) {
            final List<Course> list = (List<Course>) map.get("list_show");
            for (int i = 0; i < list.size(); i++) {
                final int a = i;
                View v = inflater.inflate(R.layout.rank_person, null);
                ImageView img = (ImageView) v.findViewById(R.id.imageView_rank_course);
                Picasso.with(context).load(list.get(i).getCover_pic()).placeholder(R.drawable.sgk_img_default_steppic).into(img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CourseAllDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",list.get(a).getHand_id());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });

                ViewGroup pp = (ViewGroup) img.getParent();
                if (pp != null) {
                    pp.removeAllViewsInLayout();
                }

                holder.linearLayout_rank_show.addView(img);
                if (i != 2) {
                    View view = new View(context);
                    view.setLayoutParams(new ViewGroup.LayoutParams(5, ViewGroup.LayoutParams.MATCH_PARENT));
                    view.setBackgroundColor(Color.WHITE);
                    holder.linearLayout_rank_show.addView(view);
                }
            }
        }
        //设置User内容
        else {
            final List<User> list = (List<User>) map.get("list_show");
            for (int i = 0; i < list.size(); i++) {
                final int a = i;
                View v = inflater.inflate(R.layout.rank_person, null);
                LinearLayout l = (LinearLayout) v.findViewById(R.id.linearLayout_rank_person);
                ImageView img = (ImageView) l.findViewById(R.id.imageView_rank_usericon);
                TextView txt = (TextView) l.findViewById(R.id.textView_rank_username);

                Picasso.with(context).load(list.get(i).getHead_pic()).placeholder(R.drawable.sgk_img_default_big).into(img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CourseUserDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",list.get(a));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                txt.setText(list.get(i).getUser_name());

                ViewGroup pp = (ViewGroup) l.getParent();
                if (pp != null) {
                    pp.removeAllViewsInLayout();
                }

                holder.linearLayout_rank_show.addView(l);
            }
        }

        return ret;
    }

    static class ViewHolder {
        LinearLayout linearLayout_rank_more;    //获得更多评论
        ImageView imageView_rank_icon;  //图标
        TextView textView_rank_title;   //标题
        LinearLayout linearLayout_rank_show;    //显示图片的LinearLayout
    }

}