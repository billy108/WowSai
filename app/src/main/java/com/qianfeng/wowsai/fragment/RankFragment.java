package com.qianfeng.wowsai.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.RankInfoAdapter;
import com.qianfeng.wowsai.tool.GetJsonInfo;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CustomRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class RankFragment extends Fragment {
    private int tabIndex;
    private List<Map<String, Object>> rankList;
    private RankInfoAdapter adapter;
    private CustomRefreshListView customRefreshListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabIndex = getArguments().getInt("tabIndex");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_rank, container, false);

        initView(ret);

        initData();

        return ret;
    }

    private void initView(View ret) {
        customRefreshListView = (CustomRefreshListView) ret.findViewById(R.id.listView_rankFragment_show);
        ListView listView = customRefreshListView.getListView();
        rankList = new ArrayList<>();
        adapter = new RankInfoAdapter(rankList, getActivity());
        listView.setAdapter(adapter);
        customRefreshListView.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    private void initData() {
        HttpUtil.getStringData(StaticData.RANKING, getActivity(), new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String s) {
                if (s != null) {
                    if (tabIndex == 0) {
                        List<Map<String, Object>> rankInfo = GetJsonInfo.getMonthRankInfo(s);
                        setView(rankInfo);
                    } else {
                        List<Map<String, Object>> rankInfo = GetJsonInfo.getAllRankInfo(s);
                        setView(rankInfo);
                    }
                }
            }
        });
    }

    private void setView(List<Map<String, Object>> rankInfo) {
        if (rankInfo != null) {
            rankList.clear();
            rankList.addAll(rankInfo);
            adapter.notifyDataSetChanged();
            customRefreshListView.onRefreshComplete();
        }
    }
}