package com.qianfeng.wowsai.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.ActionAdapter;
import com.qianfeng.wowsai.model.Action;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.JsonUtils;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CustomRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class FindActionFragment extends Fragment {

    private CustomRefreshListView refreshListView;
    private ListView listView_action;
    private List<Action> actions;
    private ActionAdapter adapter;
    private String url;
    private String url_page;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findaction, container, false);
        refreshListView = (CustomRefreshListView) view.findViewById(R.id.fragment_action_listview);
        initData();
        addListener();
        return view;
    }

    private void initData() {
        listView_action = refreshListView.getListView();
        actions = new ArrayList<>();
        adapter = new ActionAdapter(getActivity(), actions);
        listView_action.setAdapter(adapter);
        url = StaticData.FIND_DONGTAI;
        url_page = StaticData.FIND_DONGTAI_PAGE;
        initListView();
    }

    private void addListener() {
        refreshListView.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initListView();
            }
        });
        refreshListView.setOnLastItemListener(new CustomRefreshListView.OnLastItemListener() {
            @Override
            public void onLastItem() {
                reloadListView();
            }
        });
    }

    private void reloadListView() {
        if (actions != null && actions.size() > 1) {
            String last_id = actions.get(actions.size()-1).getPmid();
            HttpUtil.getStringData(url_page + last_id, getActivity(), new HttpUtil.OnSuccessListener() {
                @Override
                public void loadData(String result) {
                    List<Action> subActions = JsonUtils.parseAction(result);
                    if (subActions != null) {
                        actions.addAll(subActions);
                        adapter.notifyDataSetChanged();
                    }

                }
            });
        }

    }

    private void initListView() {
        HttpUtil.getStringData(url, getActivity(), new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                List<Action> subActions = JsonUtils.parseAction(result);
                if (subActions != null) {
                    actions.clear();
                    actions.addAll(subActions);
                    adapter.notifyDataSetChanged();
                }
                refreshListView.onRefreshComplete();
            }
        });
    }
}