package com.qianfeng.wowsai.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.EventDetailActivity;
import com.qianfeng.wowsai.adapter.HandCircleAdapter;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.JsonUtils;
import com.qianfeng.wowsai.view.custom.CustomRefreshGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class WorkListFragment extends Fragment {

    @ViewInject(R.id.customRefreshGridView_workListFragment)
    private CustomRefreshGridView customRefreshGridView;

    @ViewInject(R.id.imageView_campaignStatus)
    private ImageView imageView_status;

    private GridView gridView;

    private HandCircleAdapter adapter;

    private List<HandCircle> totalList;

    private Context context;

    private String URL;

    private String lastId = "";

    private boolean isFirst = true;

    private boolean isLoading;

    private boolean isShowing = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worklist, container, false);

        ViewUtils.inject(this, view);

        initView();

        initData();

        addListener();

        return view;
    }

    public void initView() {

        gridView = customRefreshGridView.getGridView();

    }

    public void initData() {

        context = getActivity();
        URL = getArguments().getString("url");
        String status = getArguments().getString("status");

        totalList = new ArrayList<>();
        adapter = new HandCircleAdapter(totalList, context);
        gridView.setAdapter(adapter);

        loadData();

//        switch (status) {
//            case "0":
//                imageView_status.setImageResource(R.drawable.event_prepare_bt);
//                break;
//            case "1":
//                imageView_status.setImageResource(R.drawable.event_join_bt);
//                break;
//            case "2":
//                imageView_status.setImageResource(R.drawable.event_over_bt);
//                break;
//        }
    }

    public void addListener() {
        customRefreshGridView.setOnRefreshListener(new CustomRefreshGridView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFirst = true;
                lastId = "";
                loadData();
            }
        });

        customRefreshGridView.setOnLastItemListener(new CustomRefreshGridView.OnLastItemListener() {
            @Override
            public void onLastItem() {
                if (!isLoading) {
                    isLoading = true;
                    loadData();
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 跳转手工圈页面
                HandCircle circle = totalList.get(position);
                Intent intent = new Intent();
                intent.setClass(context,EventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("item_id",circle.getHand_id());
                bundle.putString("type","1");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

//        gridView.setOnTouchListener(new View.OnTouchListener() {
//            int y = gridView.getScrollY();
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        y = gridView.getScrollY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int currentY = gridView.getScrollY();
//                        Log.e("hahahahah","currentY : "+currentY);
//                        if (y < currentY && isShowing) {
//                            imgDismiss();
//                            isShowing = false;
//                        } else if (y > currentY && !isShowing) {
//                            imgShow();
//                            isShowing = true;
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private void loadData() {
        HttpUtil.getStringData(getUrl(), context, new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                List<HandCircle> list = JsonUtils.parseCampaignDetial(result);
                if (list == null) {
                    Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    return;
                }
                lastId = list.get(list.size() - 1).getLast_id();
                notifyAdapter(list);
            }
        });
    }

    private void notifyAdapter(List<HandCircle> list) {
        if (isFirst) {
            totalList.clear();
            customRefreshGridView.onRefreshComplete();
            isFirst = false;
        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    private String getUrl() {
        if (isFirst) {
            return URL;
        } else {
            return URL + "&last_id=" + lastId;
        }
    }

//    private void imgDismiss() {
//        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 300);
//        animation.setDuration(1000);
//        animation.setFillAfter(true);
//        imageView_status.setAnimation(animation);
//    }
//
//    private void imgShow() {
//        int y = imageView_status.getTop();
//        int x = imageView_status.getLeft();
//        TranslateAnimation animation = new TranslateAnimation(0, 0, 300, 0);
//        animation.setDuration(1000);
//        animation.setFillAfter(true);
//        imageView_status.setAnimation(animation);
//
//    }
}