package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/2
 */
public class RankUserListAdapter extends BaseAdapter {
    private List<User> userList;
    private Context context;
    private LayoutInflater inflater;
    private int[] levels;

    public RankUserListAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.levels = new int[]{R.drawable.sgk_leve_lv1, R.drawable.sgk_leve_lv2, R.drawable.sgk_leve_lv3, R.drawable.sgk_leve_lv4, R.drawable.sgk_leve_lv5,
                R.drawable.sgk_leve_lv6, R.drawable.sgk_leve_lv7, R.drawable.sgk_leve_lv8, R.drawable.sgk_leve_lv9, R.drawable.sgk_leve_lv10};
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (userList != null) {
            ret = userList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        User ret = null;
        if (userList != null) {
            ret = userList.get(position);
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
            ret = inflater.inflate(R.layout.item_listview_ranklist_person, parent, false);
        }

        ViewHolder holder = (ViewHolder) ret.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.textView_rank_person_num = (TextView) ret.findViewById(R.id.textView_rank_person_num);
            holder.textView_rank_person_name = (TextView) ret.findViewById(R.id.textView_rank_person_name);
            holder.textView_rank_person_viewnum = (TextView) ret.findViewById(R.id.textView_rank_person_viewnum);
            holder.imageView_rank_person_icon = (ImageView) ret.findViewById(R.id.imageView_rank_person_icon);
            holder.imageView_rank_person_vip = (ImageView) ret.findViewById(R.id.imageView_rank_person_vip);
            holder.imageView_rank_person_level = (ImageView) ret.findViewById(R.id.imageView_rank_person_level);
            holder.imageView_rank_person_numpic = (ImageView) ret.findViewById(R.id.imageView_rank_person_numpic);
            ret.setTag(holder);
        }

        User user = userList.get(position);
        if (position == 0 && user.getLast_id().equals("1")) {
            holder.imageView_rank_person_numpic.setImageResource(R.drawable.blue_ciecle);
        } else if (position == 1 && user.getLast_id().equals("2")) {
            holder.imageView_rank_person_numpic.setImageResource(R.drawable.green_ciecle);
        } else {
            holder.imageView_rank_person_numpic.setImageResource(R.drawable.red_ciecle);
        }
        holder.textView_rank_person_num.setText((position + 1) + "");
        holder.textView_rank_person_name.setText(user.getUser_name());
        holder.textView_rank_person_viewnum.setText(user.getView_num() + "人气");
        Picasso.with(context).load(user.getHead_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.ic_launcher).into(holder.imageView_rank_person_icon);
        holder.imageView_rank_person_level.setImageResource(levels[Integer.parseInt(user.getLevel()) - 1]);
        if (user.getDaren().equals("1")) {
            holder.imageView_rank_person_vip.setVisibility(View.VISIBLE);
        } else {
            holder.imageView_rank_person_vip.setVisibility(View.GONE);
        }

        return ret;
    }

    class ViewHolder {
        TextView textView_rank_person_num;
        TextView textView_rank_person_name;
        TextView textView_rank_person_viewnum;
        ImageView imageView_rank_person_icon;
        ImageView imageView_rank_person_vip;
        ImageView imageView_rank_person_level;
        ImageView imageView_rank_person_numpic;
    }
}
