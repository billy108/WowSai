package com.qianfeng.wowsai.activity.find;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.DarenAdapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.tool.JsonUtils;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CustomRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by King
 * DATE 2015/4/30.
 */
public class DarenActivity extends Activity {
    @ViewInject(R.id.customRefreshListView_daren)
    private CustomRefreshListView refreshListView;
    private ListView listView_daren;
    private DarenAdapter adapter;
    private List<User> daren;
    private RequestQueue requestQueue;
    private AppCtx appCtx;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daren);
        initView();
        initData();
        addListener();
    }

    private void initView() {
        ViewUtils.inject(this);
        listView_daren = refreshListView.getListView();
    }

    private void initData() {
        appCtx = AppCtx.getInstance();
        if (appCtx != null) {
            requestQueue = appCtx.getRequestQueue();
        }
        daren = new ArrayList<>();
        adapter = new DarenAdapter(daren, this);
        listView_daren.setAdapter(adapter);
        url = StaticData.FOUND_DAREN;
        reloadListView();
    }

    private void reloadListView() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        List<User> subDaren = JsonUtils.parseDaren(s);
                        if (subDaren != null) {
                            daren.addAll(subDaren);
                            adapter.notifyDataSetChanged();
                        }
                        refreshListView.onRefreshComplete();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(appCtx, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (daren.size() != 0) {
                    String last_id = daren.get(daren.size() - 1).getCourse_time();

                    map.put("act","down");
                    map.put("last_id", last_id);
                }
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addListener() {
        refreshListView.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                daren.clear();
                reloadListView();
            }
        });
        refreshListView.setOnLastItemListener(new CustomRefreshListView.OnLastItemListener() {
            @Override
            public void onLastItem() {
                reloadListView();
            }
        });
    }
}