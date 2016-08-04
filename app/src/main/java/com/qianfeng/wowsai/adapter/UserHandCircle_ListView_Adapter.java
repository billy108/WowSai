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
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sky on 2015/5/2.
 */
public class UserHandCircle_ListView_Adapter extends MyBaseAdapter {
    Context context;
    List<HandCircle> totalList;
    String userHead;
    ImageLoader imageLoader ;
    public UserHandCircle_ListView_Adapter (Context context, List<HandCircle> totalList,String userHead) {
        this.context = context;
        this.totalList = totalList;
        this.userHead=userHead;
        imageLoader = AppCtx.getInstance().getImageLoader();
    };
    public void addList(List list){
        if (list!=null) {
            list.removeAll(this.totalList);
            if (list!=null) {
                this.totalList.addAll(list);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return totalList.size();
    }

    @Override
    public Object getItem(int i) {
        return totalList.get(i);
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
            view= LayoutInflater.from(context).inflate(R.layout.item_user_hand_circle,viewGroup,false);
            viewHolder.item_user_hand_circle_userName= (TextView) view.findViewById(R.id.item_user_hand_circle_userName);
            viewHolder.item_user_hand_circle_userHead= (CircleImageView)view.findViewById(R.id.item_user_hand_circle_userHead);
            viewHolder.item_user_hand_circle_content= (TextView) view.findViewById(R.id.item_user_hand_circle_content);
            viewHolder.item_user_hand_circle_photo= (ImageView) view.findViewById(R.id.item_user_hand_circle_photo);
            viewHolder.item_user_hand_circle_addTime= (TextView) view.findViewById(R.id.item_user_hand_circle_addTime);
            view.setTag(viewHolder);
        }else viewHolder= (ViewHolder) view.getTag();
        HandCircle handCircle = totalList.get(i);
        User user = handCircle.getUser();
        viewHolder.item_user_hand_circle_userName.setText(user.getUser_name());
        imageLoader.get(userHead, ImageLoader.getImageListener(viewHolder.item_user_hand_circle_userHead, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
        //Picasso.with(context).load(userHead).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).resize(40, 40).into(viewHolder.item_user_hand_circle_userHead);
        viewHolder.item_user_hand_circle_content.setText(handCircle.getSubject());
        Picasso.with(context).load(handCircle.getPics().get(0)).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).resize(200, 300).into(viewHolder.item_user_hand_circle_photo);
        viewHolder.item_user_hand_circle_addTime.setText(handCircle.getAdd_time());
        return view;
    }
    class ViewHolder{
        CircleImageView item_user_hand_circle_userHead;
        TextView item_user_hand_circle_userName;
        TextView item_user_hand_circle_content;
        ImageView item_user_hand_circle_photo;
        TextView item_user_hand_circle_addTime;

    }
}
