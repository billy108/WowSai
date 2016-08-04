package com.qianfeng.wowsai.activity.rankingList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseAllDetailActivity;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.adapter.RankCourseListAdapter;
import com.qianfeng.wowsai.adapter.RankUserListAdapter;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.tool.GetJsonInfo;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CustomRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/30
 */
public class RankListAct extends Activity {

    private CustomRefreshListView customRefreshListView;

    private List<Course> courseList;
    private List<User> userList;

    private RankCourseListAdapter courseListAdapter;
    private RankUserListAdapter userListAdapter;

    private String a;
    private String id;
    private String last_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranklist);

        initView();
        initData(1);
    }

    private void initView() {

        Bundle bundle = getIntent().getExtras();
        a = bundle.getString("a");
        id = bundle.getString("id");

        customRefreshListView = (CustomRefreshListView) findViewById(R.id.listView_ranklist_show);
        ListView listView = customRefreshListView.getListView();

        userList = new ArrayList<>();
        courseList = new ArrayList<>();

        switch (a) {
            case "person":
                userListAdapter = new RankUserListAdapter(userList, this);
                listView.setAdapter(userListAdapter);
                break;
            case "course":
                courseListAdapter = new RankCourseListAdapter(courseList, this);
                listView.setAdapter(courseListAdapter);
                break;
        }

        customRefreshListView.setOnRefreshListener(new CustomRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(1);
            }
        });

        customRefreshListView.setOnLastItemListener(new CustomRefreshListView.OnLastItemListener() {
            @Override
            public void onLastItem() {
                initData(2);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (a) {
                    case "person":
                        Intent intent = new Intent(RankListAct.this, CourseUserDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", userList.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case "course":
                        Intent intent1 = new Intent(RankListAct.this, CourseAllDetailActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("id", courseList.get(position).getHand_id());
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }

    private void initData(final int flag) {
        String url = null;
        switch (flag) {
            case 1:
                userList.clear();
                courseList.clear();
                url = StaticData.RANKING + "&a=" + a + "&id=" + id;
                break;
            case 2:
                url = StaticData.RANKING + "&a=" + a + "&id=" + id + "&last_id=" + last_id;
                break;
        }

        HttpUtil.getStringData(url, this, new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                if (result != null) {
                    switch(a){
                        case "person":
                            List<User> rankUserListInfo = GetJsonInfo.getRankUserListInfo(result);
                            userList.addAll(rankUserListInfo);
                            userListAdapter.notifyDataSetChanged();
                            last_id = userList.size()+"";
                            customRefreshListView.onRefreshComplete();
                            break;
                        case "course":
                            List<Course> rankCourseListInfo = GetJsonInfo.getRankCourseListInfo(result);
                            courseList.addAll(rankCourseListInfo);
                            courseListAdapter.notifyDataSetChanged();
                            last_id = courseList.size()+"";
                            customRefreshListView.onRefreshComplete();
                            break;
                    }
                }
            }
        });
    }
}