package com.qianfeng.wowsai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.MyBaseAdapter;
import com.qianfeng.wowsai.adapter.UserHandCircleCollect_ListView_Adapter;
import com.qianfeng.wowsai.adapter.UserHandCircle_ListView_Adapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.tool.Json2model;
import com.qianfeng.wowsai.tool.StaticData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sky on 2015/5/2.
 */
public class UserHandCircleFragment extends Fragment {
    private ListView fragment_user_hand_circle_listView;
    private String uid;
    private String userHead;
    private MyBaseAdapter adapter;
    private List<HandCircle> totalList;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        uid = arguments.getString("uid");
        userHead=arguments.getString("userHead");
        type = arguments.getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_user_hand_circle,container,false);
        fragment_user_hand_circle_listView= (ListView) view.findViewById(R.id.fragment_user_hand_circle_listView);
        totalList = new ArrayList<HandCircle>();
        if (type==1) {
            adapter = new UserHandCircle_ListView_Adapter(getActivity(), totalList, userHead);
        }else {
            adapter = new UserHandCircleCollect_ListView_Adapter(getActivity(), totalList);
        }
        fragment_user_hand_circle_listView.setAdapter(adapter);
        fragment_user_hand_circle_listView.requestDisallowInterceptTouchEvent(true);
        loadData();
        return view;
    }

    private void loadData() {

        String url= StaticData.USER_CIRCLE+uid;
        if (type==2){
            url=url+"&collect=1";
        }
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<HandCircle> handCircles = Json2model.json2Circle_list(response);
                if (handCircles!=null) {
                    adapter.addList(handCircles);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppCtx.getInstance(),"网络异常，请检查网络！",Toast.LENGTH_SHORT).show();
            }
        });
        AppCtx.getInstance().getRequestQueue().add(request);
    }
}
