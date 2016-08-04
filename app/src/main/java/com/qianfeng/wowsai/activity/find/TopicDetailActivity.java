package com.qianfeng.wowsai.activity.find;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.TopicDetailAdapter;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.Topic;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.JsonUtils;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CustomRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class TopicDetailActivity extends Activity {

    @ViewInject(R.id.refreshListView_topicDetail)
    private CustomRefreshListView customRefreshListView;

    @ViewInject(R.id.textView_topicDetail_title)
    private TextView textViewTitle;

    private ListView listView;

    private String URL = StaticData.TOPIC_DETIAL;

    private String topicId;

    private List<Course> totalList;

    private int currentPage = 1;

    private TopicDetailAdapter adapter;

    private boolean isLoading;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topicdetail);
        ViewUtils.inject(this);

        initView();
        initData();
        addListener();

    }

    public void initView(){
        listView = customRefreshListView.getListView();
    }

    public void initData(){
        Bundle bundle = getIntent().getExtras();
        topicId = bundle.getString("topicId");
        String topicSubject = bundle.getString("topicSubject");
        totalList = new ArrayList<>();

        adapter = new TopicDetailAdapter(totalList,this);

        listView.setAdapter(adapter);

        textViewTitle.setText(topicSubject);

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
        HttpUtil.getStringData(getUrl(), this, new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                Topic topic = JsonUtils.parseTopicDetail(result);
                if(topic == null){
                    Toast.makeText(TopicDetailActivity.this,"已经是全部内容了",Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Course> list = topic.getCourseList();
                notifyAdapter(list);
            }
        });
    }

    private void notifyAdapter(List<Course> list){
        if(currentPage == 1){
            totalList.clear();
            customRefreshListView.onRefreshComplete();
        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    private String getUrl(){
        return URL+topicId+"&page="+currentPage;
    }
}