package com.qianfeng.wowsai.activity.course;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;
import com.android.volley.toolbox.ImageLoader;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.UserDetailViewPagerAdapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.fragment.UserHandCircleFragment;
import com.qianfeng.wowsai.fragment.UserReleaseCourseFragment;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.qianfeng.wowsai.view.custom.Myviewpage;
import com.qianfeng.wowsai.view.custom.ScllorTabView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sky on 2015/4/30.
 */
public class CourseUserDetailActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.activity_course_user_detail_scrollView)
    ScrollView activity_course_user_detail_scrollView;
    @ViewInject(R.id.activity_course_user_detail_fen)
    TextView activity_course_user_detail_fen;
    @ViewInject(R.id.activity_course_user_detail_guan)
    TextView activity_course_user_detail_guan;
    @ViewInject(R.id.activity_course_user_detail_viewPager)
    Myviewpage activity_course_user_detail_viewPager;
    @ViewInject(R.id.activity_course_user_detail_sendLetter)
    ImageView activity_course_user_detail_sendLetter;
    @ViewInject(R.id.activity_course_user_detail_attent_no)
    ImageView activity_course_user_detail_attent_no;
    @ViewInject(R.id.activity_course_user_detail_userName)
    TextView activity_course_user_detail_userName;
    @ViewInject(R.id.activity_course_user_detail_userHeadPic)
    CircleImageView activity_course_user_detail_userHeadPic;

    @ViewInject(R.id.activity_course_user_detail_hand_circle_txt)
    TextView activity_course_user_detail_hand_circle_txt;
    @ViewInject(R.id.activity_course_user_detail_release_course_txt)
    TextView activity_course_user_detail_release_course_txt;
    @ViewInject(R.id.activity_course_user_detail_hand_circle_collect_txt)
    TextView activity_course_user_detail_hand_circle_collect_txt;
    @ViewInject(R.id.activity_course_user_detail_collect_course_txt)
    TextView activity_course_user_detail_collect_course_txt;

    @ViewInject(R.id.activity_course_user_detail_hand_circle_linear)
    LinearLayout activity_course_user_detail_hand_circle_linear;
    @ViewInject(R.id.activity_course_user_detail_release_course_linear)
    LinearLayout activity_course_user_detail_release_course_linear;
    @ViewInject(R.id.activity_course_user_detail_hand_circle_collect_linear)
    LinearLayout activity_course_user_detail_hand_circle_collect_linear;
    @ViewInject(R.id.activity_course_user_detail_collect_course_linear)
    LinearLayout activity_course_user_detail_collect_course_linear;
    @ViewInject(R.id.activity_course_user_detail_collect_course_bottomBar)
    ScllorTabView activity_course_user_detail_collect_course_bottomBar;
    @ViewInject(R.id.activity_course_user_detail_topView)
    LinearLayout activity_course_user_detail_topView;

    private User user;
    private UserDetailViewPagerAdapter adapter;
    private List<Fragment> totalList;
    ImageLoader imageLoader ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_user_detail);
        ViewUtils.inject(this);
        imageLoader = AppCtx.getInstance().getImageLoader();

        //获取传过来的数据
        Intent intent = getIntent();
        user = (User) intent.getExtras().get("user");

        totalList = new ArrayList<Fragment>();
        activity_course_user_detail_collect_course_bottomBar.setTabNum(4);
        activity_course_user_detail_collect_course_bottomBar.setSelectedColor(Color.RED, Color.RED);
        initView();
        addListener();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity_course_user_detail_topView.setFocusable(true);
        activity_course_user_detail_topView.setFocusableInTouchMode(true);
        activity_course_user_detail_topView.requestFocus();

    }

    private void initView() {

        imageLoader.get(user.getHead_pic(), ImageLoader.getImageListener(activity_course_user_detail_userHeadPic, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
//        Picasso.with(this).load(user.getHead_pic()).into(activity_course_user_detail_userHeadPic);
        activity_course_user_detail_userName.setText(user.getUser_name());


    }

    private void loadData() {
        String user_id = user.getUser_id();
        Bundle bundle = new Bundle();
        bundle.putString("uid", user_id);
        bundle.putString("userHead", user.getHead_pic());
        bundle.putInt("type", 1);
        UserHandCircleFragment userHandCircleFragment = new UserHandCircleFragment();
        userHandCircleFragment.setArguments(bundle);
        UserReleaseCourseFragment userReleaseCourseFragment = new UserReleaseCourseFragment();
        userReleaseCourseFragment.setArguments(bundle);
        Bundle bundle2 = new Bundle();
        bundle2.putString("uid", user_id);
        bundle2.putString("userHead", user.getHead_pic());
        bundle2.putInt("type", 2);
        UserHandCircleFragment userHandCircleFragment2 = new UserHandCircleFragment();
        userHandCircleFragment2.setArguments(bundle2);
        UserReleaseCourseFragment userReleaseCourseFragment2 = new UserReleaseCourseFragment();
        userReleaseCourseFragment2.setArguments(bundle2);
        totalList.add(userHandCircleFragment);
        totalList.add(userReleaseCourseFragment);
        totalList.add(userHandCircleFragment2);
        totalList.add(userReleaseCourseFragment2);
        adapter = new UserDetailViewPagerAdapter(this.getSupportFragmentManager(), totalList);
        activity_course_user_detail_viewPager.setAdapter(adapter);

    }

    private void addListener() {
        activity_course_user_detail_fen.setOnClickListener(this);
        activity_course_user_detail_guan.setOnClickListener(this);
        activity_course_user_detail_sendLetter.setOnClickListener(this);
        activity_course_user_detail_attent_no.setOnClickListener(this);
        activity_course_user_detail_hand_circle_linear.setOnClickListener(this);
        activity_course_user_detail_release_course_linear.setOnClickListener(this);
        activity_course_user_detail_hand_circle_collect_linear.setOnClickListener(this);
        activity_course_user_detail_collect_course_linear.setOnClickListener(this);

        activity_course_user_detail_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                activity_course_user_detail_collect_course_bottomBar.setOffset(i, v);
            }

            @Override
            public void onPageSelected(int i) {
                resetText(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    //对字体进行颜色设置
    private void resetText(int i) {
        activity_course_user_detail_hand_circle_txt.setTextColor(Color.BLACK);
        activity_course_user_detail_release_course_txt.setTextColor(Color.BLACK);
        activity_course_user_detail_hand_circle_collect_txt.setTextColor(Color.BLACK);
        activity_course_user_detail_collect_course_txt.setTextColor(Color.BLACK);

        switch (i) {
            case 0:
                activity_course_user_detail_hand_circle_txt.setTextColor(Color.RED);

                break;
            case 1:
                activity_course_user_detail_release_course_txt.setTextColor(Color.RED);

                break;
            case 2:
                activity_course_user_detail_hand_circle_collect_txt.setTextColor(Color.RED);
                break;
            case 3:
                activity_course_user_detail_collect_course_txt.setTextColor(Color.RED);
                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_course_user_detail_fen:
                //TODO 跳转到列表
                break;
            case R.id.activity_course_user_detail_guan:
                //TODO 跳转到列表
                break;
            case R.id.activity_course_user_detail_sendLetter:
                Toast.makeText(AppCtx.getInstance(), "您的等级还不够哦，抓紧升级吧！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_course_user_detail_attent_no:
                Toast.makeText(AppCtx.getInstance(), "关注成功！", Toast.LENGTH_SHORT).show();
                break;
            //点击标题，页面发生变化
            case R.id.activity_course_user_detail_hand_circle_linear:
                resetText(0);
                activity_course_user_detail_viewPager.setCurrentItem(0, true);
                break;
            case R.id.activity_course_user_detail_release_course_linear:
                resetText(1);
                activity_course_user_detail_viewPager.setCurrentItem(1);
                break;
            case R.id.activity_course_user_detail_hand_circle_collect_linear:
                resetText(2);
                activity_course_user_detail_viewPager.setCurrentItem(2);
                break;
            case R.id.activity_course_user_detail_collect_course_linear:
                resetText(3);
                activity_course_user_detail_viewPager.setCurrentItem(3);
                break;

        }
    }
}