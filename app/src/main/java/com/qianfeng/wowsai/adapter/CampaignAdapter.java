package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.model.Campaign;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class CampaignAdapter extends BaseAdapter {

    private List<Campaign> list;
    private Context context;
    private LayoutInflater inflater;
    private int width;
    private int height;

    public CampaignAdapter(List<Campaign> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        width = context.getResources().getDisplayMetrics().widthPixels;
        height = width*230/640;
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
            convertView = inflater.inflate(R.layout.item_campaign,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView_item_campaign_logo);
            viewHolder.textView_name = (TextView) convertView.findViewById(R.id.textView_item_campaign_cName);
            viewHolder.textView_status = (TextView) convertView.findViewById(R.id.textView_item_campaign_status);
            viewHolder.textView_time = (TextView) convertView.findViewById(R.id.textView_item_campaign_time);
            viewHolder.imageView.setLayoutParams(new LinearLayout.LayoutParams(width,height));
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Campaign campaign = list.get(position);
        viewHolder.textView_time.setText(campaign.getC_time());
        viewHolder.textView_name.setText(campaign.getC_name());
        viewHolder.textView_status.setText(campaign.getStatusString());
        Picasso.with(context).load(campaign.getM_logo()).placeholder(R.drawable.sgk_img_default_big).error(R.drawable.sgk_img_default_big).into(viewHolder.imageView);


        return convertView;
    }


    class ViewHolder{
        ImageView imageView;
        TextView textView_name;
        TextView textView_time;
        TextView textView_status;
    }
}
