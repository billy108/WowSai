package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.android.volley.toolbox.ImageLoader;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.app.AppCtx;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sky on 2015/5/3.
 */
public class UserHandCircleCollect_GridView_Adapter extends BaseAdapter{
    List<String> totalList;
    Context context;
    ImageLoader imageLoader ;
    public UserHandCircleCollect_GridView_Adapter(List<String> totalList, Context context) {
        this.totalList = totalList;
        this.context = context;
        imageLoader = AppCtx.getInstance().getImageLoader();
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
        if (view==null){
            view=(new ImageView(context));
            ((ImageView)view).setAdjustViewBounds(true);
            ((ImageView)view).setMaxHeight(100);
            ((ImageView)view).setScaleType(ImageView.ScaleType.FIT_XY);

        }
        imageLoader.get(totalList.get(i), ImageLoader.getImageListener((ImageView) view, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
        //Picasso.with(context).load(totalList.get(i)).into((ImageView) view);
        return view;
    }

}
