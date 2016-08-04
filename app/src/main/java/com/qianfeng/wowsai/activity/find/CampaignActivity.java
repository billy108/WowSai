package com.qianfeng.wowsai.activity.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.CampaignAdapter;
import com.qianfeng.wowsai.adapter.TopicAdapter;
import com.qianfeng.wowsai.model.Campaign;
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
public class CampaignActivity extends Activity {

    @ViewInject(R.id.refreshListView_campaign)
    private CustomRefreshListView customRefreshListView;

    private ListView listView;

    private String URL = StaticData. FOUND_ACTIVITY_PAGE;

    private String lastId="";

    private boolean isFirst;

    private boolean isLoading;

    private List<Campaign> totalList;

    private CampaignAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
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
        adapter = new CampaignAdapter(totalList,this);
        listView.setAdapter(adapter);
        loadData();

    }

    public void addListener(){
        customRefreshListView.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFirst = true;
                lastId = "";
                loadData();
            }
        });

        customRefreshListView.setOnLastItemListener(new CustomRefreshListView.OnLastItemListener() {
            @Override
            public void onLastItem() {
                if(!isLoading) {
                    isLoading = true;
                    loadData();
                }
            }
        });

        final Intent intent = new Intent();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Campaign campaign = totalList.get(position);
                intent.setClass(CampaignActivity.this,CampaignDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Bundle bundle = new Bundle();
                bundle.putString("cid",campaign.getC_id());
                bundle.putString("status",campaign.getC_statuse());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        HttpUtil.getStringData(URL + lastId, this, new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                List<Campaign> list = JsonUtils.parseCampaign(result);
                isLoading = false;
                if(list == null){
                    Toast.makeText(CampaignActivity.this,"没有更多内容了",Toast.LENGTH_SHORT).show();
                    return;
                }
                lastId = list.get(list.size()-1).getId();
                notifyAdapter(list);
            }
        });
    }

    private void notifyAdapter(List<Campaign> list){
        if(isFirst){
            totalList.clear();
            customRefreshListView.onRefreshComplete();
            isFirst = false;
        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}