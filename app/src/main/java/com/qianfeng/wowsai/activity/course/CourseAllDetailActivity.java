package com.qianfeng.wowsai.activity.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.CourseAllDetail_ListView_Adapter;
import com.qianfeng.wowsai.adapter.CourseAllDetail_ViewPager_Adapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.tool.Json2model;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sky on 2015/4/30.
 */
public class CourseAllDetailActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.activity_course_all_detail_back)
    private LinearLayout activity_course_all_detail_back;
    @ViewInject(R.id.activity_course_all_detail_comment)
    private RelativeLayout activity_course_all_detail_comment;
    @ViewInject(R.id.activity_course_all_detail_collect)
    private LinearLayout activity_course_all_detail_collect;
    @ViewInject(R.id.activity_course_all_detail_share)
    private LinearLayout activity_course_all_detail_share;


    @ViewInject(R.id.activity_course_all_detail_viewPager)
    private ViewPager activity_course_all_detail_viewPager;
    @ViewInject(R.id.activity_course_all_detail_comment_number)
    private TextView activity_course_all_detail_comment_number;
    @ViewInject(R.id.activity_course_all_detail_collectImg)
    private ImageView activity_course_all_detail_collectImg;

    private String commentNumber="0";
    private String id;
    private List<View> totalList;
    private CourseAllDetail_ViewPager_Adapter adapter;
    private Course course;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_all_detail);
        ViewUtils.inject(this);
        //获取传过来的数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        totalList=new ArrayList<View>();
        initView();
        addListener();
        loadData();
    }

    private void loadData() {
        StringRequest request=new StringRequest(StaticData.COURSE_DETIAL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                course = Json2model.json2Course(response);
                activity_course_all_detail_comment_number.setText(course.getComment_num());
                ImageView imgView=new ImageView(AppCtx.getInstance());
                Picasso.with(CourseAllDetailActivity.this).load(course.getBg_pic()).into(imgView);
                List<View> tempList=new ArrayList<View>();
                //加载第一个页面
                View viewFirst= LayoutInflater.from(AppCtx.getInstance()).inflate(R.layout.activity_course_all_detail_first,null);
                initFirstView(viewFirst);
                tempList.add(viewFirst);
                //加载第二个界面
                LinearLayout viewSecond= (LinearLayout) LayoutInflater.from(AppCtx.getInstance()).inflate(R.layout.activity_course_all_detail_second,null);
                initSecondView(viewSecond);
                tempList.add(viewSecond);
                //加载步骤界面
                List<Map<String, String>> step = course.getStep();
                for (int i = 0; i < step.size(); i++) {
                    View viewStep= LayoutInflater.from(AppCtx.getInstance()).inflate(R.layout.activity_course_all_detail_step,null);
                    initStepView(viewStep,i);
                    tempList.add(viewStep);
                }
                //加载最后一个界面
                View viewLast= LayoutInflater.from(AppCtx.getInstance()).inflate(R.layout.activity_course_all_detail_last,null);
                initLastView(viewLast);
                tempList.add(viewLast);
                //更新界面
                adapter.addList(tempList);
                activity_course_all_detail_viewPager.setBackground(imgView.getDrawable());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppCtx.getInstance(),"网络异常，请检查网络链接",Toast.LENGTH_SHORT).show();
            }
        });
        AppCtx.getInstance().getRequestQueue().add(request);
    }

    private void initLastView(View viewLast) {
        ImageView activity_course_all_detail_last_userHead= (ImageView) viewLast.findViewById(R.id.activity_course_all_detail_last_userHead);
        TextView activity_course_all_detail_last_userName= (TextView) viewLast.findViewById(R.id.activity_course_all_detail_last_userName);
        ListView activity_course_all_detail_last_comments= (ListView) viewLast.findViewById(R.id.activity_course_all_detail_last_comments);
        TextView activity_course_all_detail_last_enptyInfo= (TextView) viewLast.findViewById(R.id.activity_course_all_detail_last_enptyInfo);
        activity_course_all_detail_last_comments.setEmptyView(activity_course_all_detail_last_enptyInfo);

        User user1 = course.getUser();
        Picasso.with(this).load(user1.getHead_pic()).into(activity_course_all_detail_last_userHead);
        activity_course_all_detail_last_userName.setText(user1.getUser_name());
        List<Map<String, String>> comment = course.getComment();
        activity_course_all_detail_last_comments.setAdapter(new CourseAllDetail_ListView_Adapter(this,comment));
    }

    private void initStepView(View viewStep, int i) {
        Map<String, String> strMap = course.getStep().get(i);
        ImageView activity_pic_news_detail_photoView= (ImageView) viewStep.findViewById(R.id.activity_pic_news_detail_photoView);
        TextView activity_course_all_detail_step_content= (TextView) viewStep.findViewById(R.id.activity_course_all_detail_step_content);
        TextView activity_course_all_detail_step_number= (TextView) viewStep.findViewById(R.id.activity_course_all_detail_step_number);
        Picasso.with(this).load(strMap.get("pic")).into(activity_pic_news_detail_photoView);
        activity_course_all_detail_step_content.setText(strMap.get("content"));
        activity_course_all_detail_step_number.setText((i+1)+"/"+(course.getStep().size()+1));

    }

    private void initSecondView(LinearLayout viewSecond) {
        List<Map<String, String>> material = course.getMaterial();
        for (int i = 0; i < material.size(); i++) {
            Map<String, String> map = material.get(i);
            View tempView=LayoutInflater.from(AppCtx.getInstance()).inflate(R.layout.item_course_all_detail_second, null);
            TextView detail_second_textView1= (TextView) tempView.findViewById(R.id.detail_second_textView1);
            TextView detail_second_textView2= (TextView) tempView.findViewById(R.id.detail_second_textView2);
            detail_second_textView1.setText(map.get("name"));
            detail_second_textView2.setText(map.get("num"));
            viewSecond.addView(tempView);
        }
        TextView needTools=new TextView(this);
        needTools.setText("所需工具");

        needTools.setGravity(View.TEXT_ALIGNMENT_CENTER);
        needTools.setPadding(10,10,10,10);
        viewSecond.setGravity(View.TEXT_ALIGNMENT_CENTER);
        viewSecond.addView(needTools);

        List<Map<String, String>> tools = course.getTools();
        for (int i = 0; i < tools.size(); i++) {
            Map<String, String> map = tools.get(i);
            View tempView=LayoutInflater.from(AppCtx.getInstance()).inflate(R.layout.item_course_all_detail_second, null);
            TextView detail_second_textView1= (TextView) tempView.findViewById(R.id.detail_second_textView1);
            TextView detail_second_textView2= (TextView) tempView.findViewById(R.id.detail_second_textView2);
            detail_second_textView1.setText(map.get("name"));
            detail_second_textView2.setText(map.get("num"));
            viewSecond.addView(tempView);
        }


    }

    private void initFirstView(View viewFirst) {
        TextView activity_course_all_detail_subject= (TextView) viewFirst.findViewById(R.id.activity_course_all_detail_subject);
        TextView activity_course_all_detail_summary= (TextView) viewFirst.findViewById(R.id.activity_course_all_detail_summary);
        com.qianfeng.wowsai.view.custom.CircleImageView activity_course_all_detail_userPhoto= (CircleImageView) viewFirst.findViewById(R.id.activity_course_all_detail_userPhoto);
        TextView activity_course_all_detail_userName= (TextView) viewFirst.findViewById(R.id.activity_course_all_detail_userName);
        TextView activity_course_all_detail_details= (TextView) viewFirst.findViewById(R.id.activity_course_all_detail_details);

        activity_course_all_detail_subject.setText(course.getSubject());
        activity_course_all_detail_summary.setText(course.getSummary());
        User user1 = course.getUser();
        Picasso.with(this).load(user1.getHead_pic()).into(activity_course_all_detail_userPhoto);
        activity_course_all_detail_userName.setText(user1.getUser_name());
        String detailStr = course.getView_num()+"人气|"+course.getCollect_num()+"收藏|"+course.getComment_num()+"评论|"+course.getLaud()+"赞";
        activity_course_all_detail_details.setText(detailStr);

    }

    //初始化页面的控件
    private void initView() {
        //根据数据库中是否有数据来判断收藏图片
        if (hasData()){
            activity_course_all_detail_collectImg.setEnabled(true);
            deleteData();
        }else {
            activity_course_all_detail_collectImg.setEnabled(false);
            addData();
        }
        //给品论控件添加数值
        activity_course_all_detail_comment_number.setText(commentNumber);
        //初始化viewpager
        adapter = new CourseAllDetail_ViewPager_Adapter(totalList);
        activity_course_all_detail_viewPager.setAdapter(adapter);
    }

    private void addListener() {
        activity_course_all_detail_back.setOnClickListener(this);
        activity_course_all_detail_comment.setOnClickListener(this);
        activity_course_all_detail_collect.setOnClickListener(this);
        activity_course_all_detail_share.setOnClickListener(this);
        activity_course_all_detail_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                //TODO
            }
            @Override
            public void onPageScrolled(int i, float v, int i1) {}
            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_course_all_detail_back:
                finish();
                break;
            case R.id.activity_course_all_detail_comment:
                activity_course_all_detail_viewPager.setCurrentItem(totalList.size());
                break;
            case R.id.activity_course_all_detail_collect:
                // TODO save data into db
                //change photo state
                if (hasData()){
                    activity_course_all_detail_collectImg.setEnabled(true);
                    deleteData();
                }else {
                    activity_course_all_detail_collectImg.setEnabled(true);
                    addData();
                }
                break;
            case R.id.activity_course_all_detail_share:
                showShare();
                break;

        }
    }
    //分享代码
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }
    /***************************对数据库的操作***************************/
    //添加数据库中的数据
    private void addData() {

    }

    //删除数据库中的数据
    private void deleteData() {

    }

    //判断数据库中是否有数据
    private boolean hasData() {
        return false;
    }
}