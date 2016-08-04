package com.qianfeng.wowsai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.MyBaseAdapter;
import com.qianfeng.wowsai.adapter.UserMyCourse_GrideView_Adapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.tool.Json2model;
import com.qianfeng.wowsai.tool.StaticData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sky on 2015/5/2.
 */
public class UserReleaseCourseFragment extends Fragment {

    private String uid;
    private GridView fragment_user_release_gridView;
    private MyBaseAdapter adapter;
    private List<Map<String, String>> totalList;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        uid = arguments.getString("uid");
        type = arguments.getInt("type");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_user_release_course,container,false);
        fragment_user_release_gridView = (GridView) view.findViewById(R.id.fragment_user_release_gridView);
        totalList = new ArrayList<Map<String, String>>();
        if (type==1){
            adapter=new UserMyCourse_GrideView_Adapter(getActivity(),totalList);
        }else {
            //TODO
            adapter=new UserMyCourse_GrideView_Adapter(getActivity(),totalList);
        }
        fragment_user_release_gridView.setAdapter(adapter);
        fragment_user_release_gridView.requestDisallowInterceptTouchEvent(true);
        loadData();
        return view;
    }

    private void loadData() {
        String url= null;
        if (type==1){
            url=StaticData.USER_MYCOURSE+uid;
        }else {
            url=StaticData.USER_COLLECT_COURSE+uid;
        }
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Map<String, String>> comment_list = Json2model.getMyComment_list(response);
                if (comment_list!=null) {
                    adapter.addList(comment_list);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppCtx.getInstance(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
            }
        });
        AppCtx.getInstance().getRequestQueue().add(request);
    }

}
