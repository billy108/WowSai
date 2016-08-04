package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.model.Course;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/2
 */
public class RankCourseListAdapter extends BaseAdapter {
    private List<Course> courseList;
    private Context context;
    private LayoutInflater inflater;

    public RankCourseListAdapter(List<Course> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (courseList != null) {
            ret = courseList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        Course ret = null;
        if (courseList != null) {
            ret = courseList.get(position);
        }
        return ret;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret;
        if (convertView != null) {
            ret = convertView;
        } else {
            ret = inflater.inflate(R.layout.item_listview_ranklist_course, parent, false);
        }

        ViewHolder holder = (ViewHolder) ret.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.textView_rank_course_num = (TextView) ret.findViewById(R.id.textView_rank_course_num);
            holder.textView_rank_course_title = (TextView) ret.findViewById(R.id.textView_rank_course_title);
            holder.textView_rank_course_username = (TextView) ret.findViewById(R.id.textView_rank_course_username);
            holder.textView_rank_course_viewnum = (TextView) ret.findViewById(R.id.textView_rank_course_viewnum);
            holder.imageView_rank_course_pic = (ImageView) ret.findViewById(R.id.imageView_rank_course_pic);
            holder.imageView_rank_course_numpic = (ImageView) ret.findViewById(R.id.imageView_rank_course_numpic);
            ret.setTag(holder);
        }

        Course course = courseList.get(position);
        if (position == 0 && course.getLast_id().equals("1")) {
            holder.imageView_rank_course_numpic.setImageResource(R.drawable.blue_ciecle);
        } else if (position == 1 && course.getLast_id().equals("2")) {
            holder.imageView_rank_course_numpic.setImageResource(R.drawable.green_ciecle);
        } else {
            holder.imageView_rank_course_numpic.setImageResource(R.drawable.red_ciecle);
        }
        holder.textView_rank_course_num.setText((position + 1) + "");
        holder.textView_rank_course_title.setText(course.getSubject());
        holder.textView_rank_course_username.setText(course.getUser().getUser_name());
        holder.textView_rank_course_viewnum.setText(course.getView_num() + "人气");
        Picasso.with(context).load(course.getCover_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.ic_launcher).into(holder.imageView_rank_course_pic);

        return ret;
    }

    class ViewHolder {
        TextView textView_rank_course_num;
        TextView textView_rank_course_title;
        TextView textView_rank_course_username;
        TextView textView_rank_course_viewnum;
        ImageView imageView_rank_course_pic;
        ImageView imageView_rank_course_numpic;
    }
}
