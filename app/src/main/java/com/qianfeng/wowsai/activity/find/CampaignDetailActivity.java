package com.qianfeng.wowsai.activity.find;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.ContentFragmentPagerAdapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.fragment.CampaignInstructFragment;
import com.qianfeng.wowsai.fragment.WorkListFragment;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.ScllorTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/2.
 */
public class CampaignDetailActivity extends FragmentActivity implements View.OnClickListener {

    @ViewInject(R.id.scllorTabView_tab_underline)
    private ScllorTabView scllorTabView_tab_underline;

    @ViewInject(R.id.viewPager_campaindetail_content)
    private ViewPager viewPager;

    @ViewInject(R.id.textView_main_cnt_tab1)
    private TextView textView_main_cnt_tab1;

    @ViewInject(R.id.textView_main_cnt_tab2)
    private TextView textView_main_cnt_tab2;

    @ViewInject(R.id.textView_main_cnt_tab3)
    private TextView textView_main_cnt_tab3;

    @ViewInject(R.id.imageView_title_search)
    private ImageView imageView_title_search;

    private List<Fragment> fragmentList;
    ;

    private String campaignId;
    private Resources res;
    private String status;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaigndetail);
        ViewUtils.inject(this);

        initView();

        initData();

        addListener();
    }

    public void initView() {
        scllorTabView_tab_underline.setSelectedColor(Color.RED, Color.YELLOW);
        scllorTabView_tab_underline.setTabNum(3);
        viewPager.setOffscreenPageLimit(2);


    }

    public void initData() {

        res = AppCtx.getInstance().getRes();
        Bundle bundle = getIntent().getExtras();
        campaignId = bundle.getString("cid");
        status = bundle.getString("status");
        fragmentList = new ArrayList<>();
        loadData();
        ContentFragmentPagerAdapter adapter = new ContentFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        changeColor(0);


    }

    public void addListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                scllorTabView_tab_underline.setOffset(i, v);
            }

            @Override
            public void onPageSelected(int i) {
                changeColor(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        textView_main_cnt_tab1.setOnClickListener(this);
        textView_main_cnt_tab2.setOnClickListener(this);
        textView_main_cnt_tab3.setOnClickListener(this);
        imageView_title_search.setOnClickListener(this);

    }

    private void loadData() {

        addFragment(CampaignInstructFragment.class, StaticData.ACTIVITY_PIC + campaignId);

        addFragment(WorkListFragment.class, StaticData.ACTIVITY_ZUIXIN + campaignId);

        addFragment(WorkListFragment.class, StaticData.VOTES + campaignId);


    }

    private <T extends Fragment> void addFragment(Class<T> clz, String url) {
        try {
            T fragment = clz.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("status",status);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 改变选项卡的颜色并加动画
     *
     * @param index
     */
    private void changeColor(int index) {
        switch (index) {
            case 0:
                textView_main_cnt_tab1.setTextColor(res.getColor(R.color.red));
                textView_main_cnt_tab2.setTextColor(res.getColor(R.color.black));
                textView_main_cnt_tab3.setTextColor(res.getColor(R.color.black));
                break;
            case 1:
                textView_main_cnt_tab1.setTextColor(res.getColor(R.color.black));
                textView_main_cnt_tab3.setTextColor(res.getColor(R.color.black));
                textView_main_cnt_tab2.setTextColor(res.getColor(R.color.red));
                break;
            case 2:
                textView_main_cnt_tab1.setTextColor(res.getColor(R.color.black));
                textView_main_cnt_tab3.setTextColor(res.getColor(R.color.red));
                textView_main_cnt_tab2.setTextColor(res.getColor(R.color.black));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_main_cnt_tab1:
                viewPager.setCurrentItem(0);
                changeColor(0);
                break;
            case R.id.textView_main_cnt_tab2:
                viewPager.setCurrentItem(1);
                changeColor(1);
                break;
            case R.id.textView_main_cnt_tab3:
                viewPager.setCurrentItem(2);
                changeColor(2);
                break;
            case R.id.imageView_title_search:
                Intent intent = new Intent(this, FindSearchAct.class);
                startActivity(intent);
                break;
        }
    }
}