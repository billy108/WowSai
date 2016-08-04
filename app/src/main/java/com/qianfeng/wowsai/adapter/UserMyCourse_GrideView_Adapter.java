package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.app.AppCtx;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by Sky on 2015/5/3.
 */
public class UserMyCourse_GrideView_Adapter extends MyBaseAdapter{
    private Context context;
    private List<Map<String, String>> list;
    ImageLoader imageLoader ;
    public UserMyCourse_GrideView_Adapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
        imageLoader = AppCtx.getInstance().getImageLoader();
    }

    @Override
    public void addList(List list) {
        if (list!=null) {
            list.removeAll(this.list);
            if (list!=null) {
                this.list.addAll(list);
                notifyDataSetChanged();
            }
        }
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
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item_user_my_course,viewGroup,false);
            viewHolder.item_user_my_courser_comment= (TextView) view.findViewById(R.id.item_user_my_courser_comment);
            viewHolder.item_user_my_courser_title= (TextView) view.findViewById(R.id.item_user_my_courser_title);
            viewHolder.item_user_my_courser_img= (ImageView) view.findViewById(R.id.item_user_my_courser_img);
            view.setTag(viewHolder);
        }else viewHolder= (ViewHolder) view.getTag();
        Map<String, String> map = list.get(i);

        viewHolder.item_user_my_courser_comment.setText(map.get("view")+"人气/"+map.get("laud")+"赞");
        viewHolder.item_user_my_courser_title.setText(map.get("subject"));
        imageLoader.get(map.get("host_pic_ss"), ImageLoader.getImageListener(viewHolder.item_user_my_courser_img, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
        //Picasso.with(context).load(map.get("host_pic_ss")).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).resize(150, 150).into(viewHolder.item_user_my_courser_img);
        return view;
    }
    class ViewHolder{
        ImageView item_user_my_courser_img;
        TextView item_user_my_courser_title;
        TextView item_user_my_courser_comment;

    }
}
