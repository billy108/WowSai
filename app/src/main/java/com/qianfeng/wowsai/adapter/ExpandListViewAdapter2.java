package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.model.Cate;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/5
 */
public class ExpandListViewAdapter2 extends BaseExpandableListAdapter {
    private Cate cate;
    private Context context;
    private LayoutInflater inflater;

    public ExpandListViewAdapter2(Cate cate, Context context) {
        this.cate = cate;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cate.getCate3s().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cate;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return cate.getCate3s().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 1000 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View ret = inflater.inflate(R.layout.item_expand_cate, parent, false);

        CateView holder = (CateView) ret.getTag();
        if (holder == null) {
            holder = new CateView();
            holder.imageView_cate_haveMore = (ImageView) ret.findViewById(R.id.imageView_cate_haveMore);
            holder.textView_cate_cate = (TextView) ret.findViewById(R.id.textView_cate_cate);
            ret.setTag(holder);
        }

        holder.imageView_cate_haveMore.setVisibility(View.VISIBLE);
        holder.textView_cate_cate.setText(cate.getName());

        return ret;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View ret;
        if (convertView == null) {
            ret = inflater.inflate(R.layout.item_cate_3, parent, false);
        } else {
            ret = convertView;
        }

        CateView holder = (CateView) ret.getTag();
        if (holder == null) {
            holder = new CateView();
            holder.imageView_cate_haveMore = (ImageView) ret.findViewById(R.id.imageView_cate_haveMore);
            holder.textView_cate_cate = (TextView) ret.findViewById(R.id.textView_cate_cate);
            ret.setTag(holder);
        }

        holder.imageView_cate_haveMore.setVisibility(View.GONE);
        holder.textView_cate_cate.setText(cate.getCate3s().get(childPosition).getName());

        return ret;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class CateView {
        TextView textView_cate_cate;
        ImageView imageView_cate_haveMore;
    }
}
