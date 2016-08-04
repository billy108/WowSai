package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by Sky on 2015/5/1.
 */
public class CourseAllDetail_ListView_Adapter extends BaseAdapter {
    private List<Map<String, String>> list;
    Context context;
    ImageLoader imageLoader ;
    public CourseAllDetail_ListView_Adapter(Context context,List<Map<String, String>> comments) {
        list=comments;
        this.context=context;
        imageLoader = AppCtx.getInstance().getImageLoader();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_course_all_detail_last,viewGroup,false);
            viewHolder=new ViewHolder();
            viewHolder.item_course_all_detail_last_listView_userName= (TextView) view.findViewById(R.id.item_course_all_detail_last_listView_userName);
            viewHolder.item_course_all_detail_last_listView_userPhoto= (CircleImageView) view.findViewById(R.id.item_course_all_detail_last_listView_userPhoto);
            viewHolder.item_course_all_detail_last_listView_userText= (TextView) view.findViewById(R.id.item_course_all_detail_last_listView_userText);
            viewHolder.item_course_all_detail_last_listView_addTime= (TextView) view.findViewById(R.id.item_course_all_detail_last_listView_addTime);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        Map<String, String> map = list.get(i);
        viewHolder.item_course_all_detail_last_listView_userName.setText(map.get("user_name"));
        viewHolder.item_course_all_detail_last_listView_userText.setText(map.get("comment"));
        viewHolder.item_course_all_detail_last_listView_addTime.setText(map.get("add_time"));
        imageLoader.get(map.get("avatar"), ImageLoader.getImageListener(viewHolder.item_course_all_detail_last_listView_userPhoto, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
        //Picasso.with(context).load(map.get("avatar")).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).resize(40,40).into(viewHolder.item_course_all_detail_last_listView_userPhoto);
        return view;
    }
    class ViewHolder{
        com.qianfeng.wowsai.view.custom.CircleImageView item_course_all_detail_last_listView_userPhoto;
        TextView item_course_all_detail_last_listView_userName;
        TextView item_course_all_detail_last_listView_userText;
        TextView item_course_all_detail_last_listView_addTime;
    }

}
