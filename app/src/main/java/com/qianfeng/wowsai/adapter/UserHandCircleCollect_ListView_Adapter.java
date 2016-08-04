package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.qianfeng.wowsai.view.custom.MyGridView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sky on 2015/5/3.
 */
public class UserHandCircleCollect_ListView_Adapter extends MyBaseAdapter {
    Context context;
    List<HandCircle> totalList;
    ImageLoader imageLoader ;

    public UserHandCircleCollect_ListView_Adapter(Context context, List<HandCircle> totalList) {
        this.context = context;
        this.totalList = totalList;
        imageLoader = AppCtx.getInstance().getImageLoader();
    }

    @Override
    public void addList(List list) {
        if (list!=null) {
            this.totalList.addAll(list);
            notifyDataSetChanged();
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
            view= LayoutInflater.from(context).inflate(R.layout.item_user_hand_circle_collect,viewGroup,false);
            viewHolder.item_user_hand_circle_collect_userHead= (CircleImageView)view.findViewById(R.id.item_user_hand_circle_collect_userHead);
            viewHolder.item_user_hand_circle_collect_userName= (TextView) view.findViewById(R.id.item_user_hand_circle_collect_userName);
            viewHolder.item_user_hand_circle_collect_content= (TextView) view.findViewById(R.id.item_user_hand_circle_collect_content);
            viewHolder.item_user_hand_circle_collect_addTime= (TextView) view.findViewById(R.id.item_user_hand_circle_collect_addTime);
            viewHolder.item_user_hand_circle_collect_gridView= (MyGridView) view.findViewById(R.id.item_user_hand_circle_collect_gridView);
            view.setTag(viewHolder);
        }else viewHolder= (ViewHolder) view.getTag();
        HandCircle handCircle = totalList.get(i);
        User user = handCircle.getUser();
        imageLoader.get(handCircle.getHost_pic(), ImageLoader.getImageListener(viewHolder.item_user_hand_circle_collect_userHead, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
        //Picasso.with(context).load(handCircle.getHost_pic()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).resize(40, 40).into(viewHolder.item_user_hand_circle_collect_userHead);
        viewHolder.item_user_hand_circle_collect_userName.setText(user.getUser_name());
        viewHolder.item_user_hand_circle_collect_content.setText(handCircle.getSubject());
        viewHolder.item_user_hand_circle_collect_addTime.setText(handCircle.getAdd_time());
        List<String> pics = handCircle.getPics();
        UserHandCircleCollect_GridView_Adapter adapter = new UserHandCircleCollect_GridView_Adapter(pics, context);
        viewHolder.item_user_hand_circle_collect_gridView.setAdapter(adapter);
        return view;
    }
    class ViewHolder{
        CircleImageView item_user_hand_circle_collect_userHead;
        TextView item_user_hand_circle_collect_userName;
        TextView item_user_hand_circle_collect_content;
        TextView item_user_hand_circle_collect_addTime;
        MyGridView item_user_hand_circle_collect_gridView;

    }
}
