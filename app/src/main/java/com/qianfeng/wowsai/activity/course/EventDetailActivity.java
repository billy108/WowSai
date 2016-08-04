package com.qianfeng.wowsai.activity.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.find.FindSearchAct;
import com.qianfeng.wowsai.adapter.CourseAllDetail_ListView_Adapter;
import com.qianfeng.wowsai.adapter.EventDetailLaudPersonAdapter;
import com.qianfeng.wowsai.adapter.UserHandCircleCollect_GridView_Adapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.tool.Json2model;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.qianfeng.wowsai.view.custom.MyGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sky on 2015/5/3.
 */
public class EventDetailActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.activity_event_detail_title_write)
    ImageView activity_event_detail_title_write;
    @ViewInject(R.id.activity_event_detail_title_name)
    TextView activity_event_detail_title_name;
    @ViewInject(R.id.activity_event_detail_title_search)
    ImageView activity_event_detail_title_search;
    @ViewInject(R.id.activity_event_detail_userHead)
    CircleImageView activity_event_detail_userHead;
    @ViewInject(R.id.activity_event_detail_userName)
    TextView activity_event_detail_userName;
    @ViewInject(R.id.activity_event_detail_submit)
    TextView activity_event_detail_submit;
    @ViewInject(R.id.activity_event_detail_photo)
    ImageView activity_event_detail_photo;
    @ViewInject(R.id.activity_event_detail_addTime)
    TextView activity_event_detail_addTime;
    @ViewInject(R.id.activity_event_detail_collect)
    TextView activity_event_detail_collect;
    @ViewInject(R.id.activity_event_detail_laud)
    TextView activity_event_detail_laud;
    @ViewInject(R.id.activity_event_detail_eventName)
    TextView activity_event_detail_eventName;
    @ViewInject(R.id.activity_event_detail_eventDetail)
    ImageView activity_event_detail_eventDetail;
    @ViewInject(R.id.activity_event_detail_eventVote)
    TextView activity_event_detail_eventVote;
    @ViewInject(R.id.activity_event_detail_laudNum)
    TextView activity_event_detail_laudNum;
    @ViewInject(R.id.activity_event_detail_laudPerson)
    MyGridView activity_event_detail_laudPerson;
    @ViewInject(R.id.activity_event_detail_commentNum)
    TextView activity_event_detail_commentNum;
    @ViewInject(R.id.activity_event_detail_commentPerson)
    ListView activity_event_detail_commentPerson;
    @ViewInject(R.id.activity_event_detail_bottom_back)
    ImageView activity_event_detail_bottom_back;
    @ViewInject(R.id.activity_event_detail_bottom_editText)
    EditText activity_event_detail_bottom_editText;
    @ViewInject(R.id.activity_event_detail_bottom_send)
    ImageView activity_event_detail_bottom_send;
    @ViewInject(R.id.noUseRelativeLayout1)
    RelativeLayout noUseRelativeLayout1;
    private String item_id;
    private HandCircle handCircle;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ViewUtils.inject(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Bundle bundle = getIntent().getExtras();
        item_id = bundle.getString("item_id");
        type = bundle.getString("type");
        //item_id="150700_201505_5";
        initView();
        addListener();
        loadData();
    }

    private void loadData() {
        String url= StaticData.ACTIVITY_DETIAL+item_id;
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                handCircle = Json2model.json2HandCircle(response);
                if (handCircle!=null) {
                    activity_event_detail_eventVote.setText(handCircle.getVotes());
                    Picasso.with(EventDetailActivity.this).load(handCircle.getUser().getHead_pic()).into(activity_event_detail_userHead);
                    activity_event_detail_userName.setText(handCircle.getUser().getUser_name());
                    activity_event_detail_submit.setText(handCircle.getSubject());
                    Picasso.with(EventDetailActivity.this).load(handCircle.getPics().get(0)).into(activity_event_detail_photo);
                    activity_event_detail_addTime.setText(handCircle.getAdd_time());
                    activity_event_detail_eventName.setText(handCircle.getC_name());
                    activity_event_detail_laudNum.setText(handCircle.getLaud_num());
                    List<User> laudList = new ArrayList<User>();
                    if (handCircle.getLaudList()!=null) {
                        laudList = handCircle.getLaudList();
                    }
                    EventDetailLaudPersonAdapter adapter = new EventDetailLaudPersonAdapter( EventDetailActivity.this,laudList);
                    activity_event_detail_laudPerson.setAdapter(adapter);
                    activity_event_detail_commentNum.setText(handCircle.getComment_num());

                    List<Map<String, String>> commentList22 =new ArrayList<Map<String, String>>();
                    if (handCircle.getCommentList22()!=null) {
                        commentList22 = handCircle.getCommentList22();
                    }
                    CourseAllDetail_ListView_Adapter adapter2=new CourseAllDetail_ListView_Adapter(EventDetailActivity.this,commentList22);
                    activity_event_detail_commentPerson.setAdapter(adapter2);

                    int totalHeight = 0;
                    for (int i = 0, len = adapter2.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
                        View listItem = adapter2.getView(i, null, activity_event_detail_commentPerson);
                        listItem.measure(0, 0);  //计算子项View 的宽高
                        totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
                    }

                    ViewGroup.LayoutParams params = activity_event_detail_commentPerson.getLayoutParams();
                    params.height = totalHeight + (activity_event_detail_commentPerson.getDividerHeight() * (adapter2.getCount() - 1));
                    activity_event_detail_commentPerson.setLayoutParams(params);
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

    private void addListener() {
        activity_event_detail_title_write.setOnClickListener(this);
        activity_event_detail_title_search.setOnClickListener(this);
        activity_event_detail_collect.setOnClickListener(this);
        activity_event_detail_laud.setOnClickListener(this);
        activity_event_detail_eventDetail.setOnClickListener(this);
        activity_event_detail_bottom_back.setOnClickListener(this);
        activity_event_detail_bottom_send.setOnClickListener(this);
    }

    private void initView() {
        if (type.equals("1")){
            noUseRelativeLayout1.setVisibility(View.VISIBLE);
        }else{
            noUseRelativeLayout1.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_event_detail_title_write:
                //TODO 调用相应的界面
                break;
            case R.id.activity_event_detail_title_search:
                startActivity(new Intent(this,FindSearchAct.class));
                break;
            case R.id.activity_event_detail_collect:
                Toast.makeText(this,"恭喜您，收藏成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_event_detail_laud:
                Toast.makeText(this,"恭喜您，点赞成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_event_detail_eventDetail:
                finish();
                break;
            case R.id.activity_event_detail_bottom_back:
                finish();
                break;
            case R.id.activity_event_detail_bottom_send:
                activity_event_detail_bottom_editText.setText("");
                Toast.makeText(this,"恭喜您，评论成功！",Toast.LENGTH_SHORT).show();
                break;

        }
    }


}