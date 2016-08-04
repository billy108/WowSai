package com.qianfeng.wowsai.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseAllDetailActivity;
import com.qianfeng.wowsai.adapter.CourseAdapter;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.EventCate;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.JsonUtils;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CustomRefreshListView;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/29.
 */
public class CourseFragment extends Fragment {

    @ViewInject(R.id.customRefreshListView_courseFragment)
    private CustomRefreshListView customRefreshListView;

    private ListView listView;

    private int tabIndex;

    private String URL = "";

    private String lastId;

    private Context context;

    private Map<String, Object> map;

    private List<Course> totalList;

    private boolean isFirst;

    private CourseAdapter adapter;

    private boolean isLoading;

    private String cate="";

    private String cateId="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course, container, false);
        ViewUtils.inject(this, view);

        EventBus.getDefault().registerSticky(this,"refreshByCate",EventCate.class);

        initView();
        initData();
        addListener();

        return view;
    }

    public void initView() {
        listView = customRefreshListView.getListView();

        customRefreshListView.setBackToTop(true);
    }

    public void initData() {
        Bundle bundle = getArguments();
        tabIndex = bundle.getInt("tabIndex");
        context = getActivity();

        switch (tabIndex) {
            case 0:
                URL = StaticData.COURSE_LIST;
                break;
            case 1:
                URL = StaticData.HOT_COURSE_LIST;
                break;
        }

        totalList = new ArrayList<>();
        adapter = new CourseAdapter(totalList, context);
        listView.setAdapter(adapter);

        loadData(URL);

    }

    public void addListener() {
        customRefreshListView.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFirst = true;
                lastId = "";
                loadData(getUrl());
            }
        });
        customRefreshListView.setOnLastItemListener(new CustomRefreshListView.OnLastItemListener() {
            @Override
            public void onLastItem() {
                if(!isLoading) {
                    isLoading = true;
                    if (lastId == null || lastId.equals("")) {
                        Toast.makeText(context, "没有更多内容了", Toast.LENGTH_SHORT).show();
                    } else {
                        loadData(getUrl());
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Course course = totalList.get(position);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("id",course.getHand_id());
                intent.setClass(context, CourseAllDetailActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void loadData(String url) {
        HttpUtil.getStringData(url, context, new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                map = JsonUtils.parseCourseList(result);
                String last = (String) map.get("lastid");
                if (last.equals(lastId) && !isFirst) {
                    lastId = null;
                }else{
                    lastId = last;
                }
                List<Course> list = (List<Course>) map.get("course");

                if (list != null) {
                    notifyAdapter(list);
                }
            }
        });
    }

    private void notifyAdapter(List<Course> list) {
        if (isFirst) {
            totalList.clear();
            customRefreshListView.onRefreshComplete();
            isFirst = false;
        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    private String getUrl(){
        return URL+lastId+ "&gcate="+cate+"&id="+cateId;
    }

    private void refreshByCate(EventCate scate){
        cate = scate.getGcate();
        cateId = scate.getId();
        isFirst = true;
        loadData(getUrl());
    }
}