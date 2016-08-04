package com.qianfeng.wowsai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.toolbox.ImageLoader;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.Cate;
import com.qianfeng.wowsai.model.Cates;
import com.qianfeng.wowsai.model.EventCate;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import de.greenrobot.event.EventBus;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/5
 */
public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    private List<Cates> catesList;
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private String gcate;

    public ExpandListViewAdapter(List<Cates> catesList, Context context) {
        this.catesList = catesList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = AppCtx.getInstance().getImageLoader();
    }

    @Override
    public int getGroupCount() {
        return catesList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return catesList.get(groupPosition).getChild().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return catesList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return catesList.get(groupPosition).getChild().get(childPosition);
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
        View ret;
        if (convertView == null) {
            ret = inflater.inflate(R.layout.item_expand_title, parent, false);
        } else {
            ret = convertView;
        }

        TitleView holder = (TitleView) ret.getTag();
        if (holder == null) {
            holder = new TitleView();
            holder.imageView_cate_ico = (CircleImageView) ret.findViewById(R.id.imageView_cate_ico);
            holder.textView_cate_title = (TextView) ret.findViewById(R.id.textView_cate_title);
            ret.setTag(holder);
        }

        imageLoader.get(catesList.get(groupPosition).getIco(), ImageLoader.getImageListener(holder.imageView_cate_ico, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
        holder.textView_cate_title.setText(catesList.get(groupPosition).getName());

        return ret;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View ret;

        if (catesList.get(groupPosition).getChild().get(childPosition).getCate3s().size() > 0) {
            gcate = catesList.get(groupPosition).getId();
            final ExpandableListView examGuidView = getExpandableListView();
            examGuidView.setDivider(null);
            examGuidView.setGroupIndicator(null);
            final Cate cate = catesList.get(groupPosition).getChild().get(childPosition);
            ExpandListViewAdapter2 adapter2 = new ExpandListViewAdapter2(cate, context);
            examGuidView.setAdapter(adapter2);
            examGuidView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPositionTwo, int childPosition, long id) {

                    // TODO 刷新教程
                    EventCate c = new EventCate();
                    c.setId(cate.getCate3s().get(childPosition).getId());
                    c.setGcate(gcate);
                    EventBus.getDefault().postSticky(c);
                    return true;
                }
            });

            /**
             * 重点：没有这个方法第三级会没有位置，所以会看不到，误以为ExpandableListView失效
             * 关键点：第二级菜单展开时通过取得节点数来设置第三级菜单的大小
             */
            examGuidView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int i = 0;

                @Override
                public void onGroupExpand(int groupPosition2) {
                    i = (cate.getCate3s().size() + 1) * 90;
                    AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i);
                    examGuidView.setLayoutParams(lp);
                }
            });
            /**
             * 第二级菜单回收时设置为标准Item大小
             */
            examGuidView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);
                    examGuidView.setLayoutParams(lp);
                }
            });
            return examGuidView;

        } else {
            ret = inflater.inflate(R.layout.item_expand_cate, parent, false);
            CateView holder = (CateView) ret.getTag();
            if (holder == null) {
                holder = new CateView();
                holder.textView_cate_cate = (TextView) ret.findViewById(R.id.textView_cate_cate);
                holder.imageView_cate_haveMore = (ImageView) ret.findViewById(R.id.imageView_cate_haveMore);
                ret.setTag(holder);
            }

            holder.textView_cate_cate.setText(catesList.get(groupPosition).getChild().get(childPosition).getName());

            holder.imageView_cate_haveMore.setVisibility(View.GONE);

        }

        return ret;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class TitleView {
        CircleImageView imageView_cate_ico;
        TextView textView_cate_title;
    }

    static class CateView {
        TextView textView_cate_cate;
        ImageView imageView_cate_haveMore;
    }

    public ExpandableListView getExpandableListView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);
        ExpandableListView superTreeView = new ExpandableListView(context);
        superTreeView.setLayoutParams(lp);
        return superTreeView;
    }

}