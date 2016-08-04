package com.qianfeng.wowsai.activity.find;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.TopicAdapter;
import com.qianfeng.wowsai.model.Topic;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.JsonUtils;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CustomRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class TopicActivity extends Activity {

    @ViewInject(R.id.refreshListView_topicActivity)
    private CustomRefreshListView customRefreshListView;

    private ListView listView;

    private int currentPage = 1;

    private List<Topic> totalList;

    private String URL = StaticData.FOUND_TOPIC_PAGE;

    private TopicAdapter adapter;

    private boolean isLoading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ViewUtils.inject(this);

        initView();

        initData();

        addListener();

    }

    public void initView(){
        listView = customRefreshListView.getListView();
    }

    public void initData(){
        totalList = new ArrayList<>();
        adapter = new TopicAdapter(totalList,this);
        listView.setAdapter(adapter);
        loadData();

    }

    public void addListener(){
        customRefreshListView.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadData();
            }
        });

        customRefreshListView.setOnLastItemListener(new CustomRefreshListView.OnLastItemListener() {
            @Override
            public void onLastItem() {
                if(!isLoading) {
                    isLoading = true;
                    currentPage++;
                    loadData();
                }
            }
        });
    }

    private void loadData(){
        HttpUtil.getStringData(URL + currentPage, this, new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                List<Topic> list = JsonUtils.parseTopic(result);
                notifyAdapter(list);
            }
        });
    }

    private void notifyAdapter(List<Topic> list){
        if(currentPage == 1){
            totalList.clear();
            customRefreshListView.onRefreshComplete();
        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }
}