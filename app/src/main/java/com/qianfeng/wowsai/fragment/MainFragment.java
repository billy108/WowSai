package com.qianfeng.wowsai.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.find.FindSearchAct;
import com.qianfeng.wowsai.adapter.ContentFragmentPagerAdapter;
import com.qianfeng.wowsai.adapter.ExpandListViewAdapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.Cates;
import com.qianfeng.wowsai.model.EventCate;
import com.qianfeng.wowsai.view.custom.ScllorTabView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private AppCtx appCtx;
    private Resources res;

    @ViewInject(R.id.textView_title_name)
    private TextView textView_title_name;

    @ViewInject(R.id.imageView_title_write)
    private ImageView imageView_title_write;

    @ViewInject(R.id.imageView_title_search)
    private ImageView imageView_title_search;

    @ViewInject(R.id.textView_main_cnt_tab1)
    private TextView textView_main_cnt_tab1;

    @ViewInject(R.id.textView_main_cnt_tab2)
    private TextView textView_main_cnt_tab2;

    @ViewInject(R.id.viewPager_fragment_content)
    private ViewPager viewPager_fragment_content;

    @ViewInject(R.id.scllorTabView_title_underline)
    private ScllorTabView scllorTabView_title_underline;

    @ViewInject(R.id.linearLayout_title_cate)
    private LinearLayout linearLayout_title_cate;

    @ViewInject(R.id.imageView_title_arrow)
    private ImageView imageView_title_arrow;

    @ViewInject(R.id.listView_course_cate)
    private ExpandableListView expandableListView;

    @ViewInject(R.id.scrollView_cate)
    private LinearLayout scrollView_cate;

    private List<Fragment> fragmentList;
    private int tabIndex;
    private boolean isClicked;

    private boolean isArrowUp;
    private List<Cates> catesList;
    private ExpandListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabIndex = getArguments().getInt("tabIndex");
        fragmentList = new ArrayList<>();

        appCtx = AppCtx.getInstance();
        res = appCtx.getRes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret;

        ret = inflater.inflate(R.layout.fragment_main, container, false);
        ViewUtils.inject(this, ret);

        EventBus.getDefault().registerSticky(this, "closeCate", EventCate.class);

        initView();

        addListener();

        changeColor(0);

        return ret;
    }

    /**
     * 设置不同Fragment的文字和ViewPager
     */
    private void initView() {
        scllorTabView_title_underline.setSelectedColor(Color.RED, Color.YELLOW);
        scllorTabView_title_underline.setTabNum(2);

        expandableListView.setGroupIndicator(null);
        catesList = appCtx.getCatesList();

        adapter = new ExpandListViewAdapter(catesList, getActivity());
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!(catesList.get(groupPosition).getChild().get(childPosition).getCate3s().size() > 0)) {
                    String gcate = catesList.get(groupPosition).getId();
                    String idd = catesList.get(groupPosition).getChild().get(childPosition).getId();
                    EventCate c = new EventCate();
                    c.setGcate(gcate);
                    c.setId(idd);
                    EventBus.getDefault().post(c);
                    EventBus.getDefault().postSticky(c);
                    return true;
                }
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0){
                    EventCate c = new EventCate();
                    c.setGcate("");
                    c.setId("");
                    EventBus.getDefault().postSticky(c);
                    return true;
                }
                return false;
            }
        });

        fragmentList.clear();
        switch (tabIndex) {
            case 0:
                textView_title_name.setText(res.getString(R.string.base_tab_find));
                textView_main_cnt_tab1.setText(res.getString(R.string.find_shouye));
                textView_main_cnt_tab2.setText(res.getString(R.string.find_dongtai));

                FindHomeFragment fragment2 = new FindHomeFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("tabIndex", 0);
                fragment2.setArguments(bundle2);
                fragmentList.add(fragment2);

                FindActionFragment fragment = new FindActionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("tabIndex", 1);
                fragment.setArguments(bundle);
                fragmentList.add(fragment);

                break;
            case 1:
                textView_title_name.setText(res.getString(R.string.base_tab_course));
                textView_main_cnt_tab1.setText(res.getString(R.string.course_zuixin));
                textView_main_cnt_tab2.setText(res.getString(R.string.course_zuire));

                imageView_title_search.setVisibility(View.GONE);
                linearLayout_title_cate.setVisibility(View.VISIBLE);

                for (int i = 0; i < 2; i++) {
                    CourseFragment courseFragment = new CourseFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("tabIndex", i);
                    courseFragment.setArguments(bundle3);
                    fragmentList.add(courseFragment);
                }
                break;
            case 2:
                textView_title_name.setText(res.getString(R.string.base_tab_rank));
                textView_main_cnt_tab1.setText(res.getString(R.string.rank_yuebang));
                textView_main_cnt_tab2.setText(res.getString(R.string.rank_zongbang));

                imageView_title_write.setVisibility(View.GONE);
                imageView_title_search.setVisibility(View.GONE);

                for (int i = 0; i < 2; i++) {
                    RankFragment fragment4 = new RankFragment();
                    Bundle bundle4 = new Bundle();
                    bundle4.putInt("tabIndex", i);
                    fragment4.setArguments(bundle4);
                    fragmentList.add(fragment4);
                }
                break;
            case 3:
                textView_title_name.setText(res.getString(R.string.base_tab_mine));
                textView_main_cnt_tab1.setText(res.getString(R.string.mine_tongzhi));
                textView_main_cnt_tab2.setText(res.getString(R.string.mine_shezhi));

                imageView_title_write.setVisibility(View.GONE);
                imageView_title_search.setVisibility(View.GONE);

                MessageFragment fragment5 = new MessageFragment();
                Bundle bundle5 = new Bundle();
                bundle5.putInt("tabIndex", 0);
                fragment5.setArguments(bundle5);
                fragmentList.add(fragment5);

                SettingFragment fragment6 = new SettingFragment();
                Bundle bundle6 = new Bundle();
                bundle6.putInt("tabIndex", 1);
                fragment6.setArguments(bundle6);
                fragmentList.add(fragment6);

                break;
        }

        if (tabIndex == 3) {
            viewPager_fragment_content.setOffscreenPageLimit(0);
        }
        viewPager_fragment_content.setAdapter(new ContentFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
        viewPager_fragment_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                scllorTabView_title_underline.setOffset(i, v);
            }

            @Override
            public void onPageSelected(int i) {
                changeColor(i);
                if (tabIndex == 0 && i == 0) {
                    textView_title_name.setText(res.getString(R.string.base_title_find));
                }
                if (tabIndex == 0 && i == 1) {
                    textView_title_name.setText(res.getString(R.string.base_title_find2));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    /**
     * 添加监听
     */
    private void addListener() {
        textView_main_cnt_tab1.setOnClickListener(this);
        textView_main_cnt_tab2.setOnClickListener(this);
        imageView_title_write.setOnClickListener(this);
        imageView_title_search.setOnClickListener(this);
        linearLayout_title_cate.setOnClickListener(this);
    }

    /**
     * 具体点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_main_cnt_tab1:
                viewPager_fragment_content.setCurrentItem(0);
                changeColor(0);
                if (tabIndex == 0) {
                    textView_title_name.setText(res.getString(R.string.base_title_find));
                }
                break;
            case R.id.textView_main_cnt_tab2:
                viewPager_fragment_content.setCurrentItem(1);
                changeColor(1);
                if (tabIndex == 0) {
                    textView_title_name.setText(res.getString(R.string.base_title_find2));
                }
                break;
            case R.id.imageView_title_write:

                break;
            case R.id.imageView_title_search:
                Intent intent = new Intent(getActivity(), FindSearchAct.class);
                startActivity(intent);
                break;
            case R.id.linearLayout_title_cate:
                if (!isArrowUp) {
                    imageView_title_arrow.setImageResource(R.drawable.arrow_up);
                    isArrowUp = true;
                    catesList = appCtx.getCatesList();
                    adapter.notifyDataSetChanged();
                    scrollView_cate.setVisibility(View.VISIBLE);
                } else {
                    imageView_title_arrow.setImageResource(R.drawable.arrow_down);
                    isArrowUp = false;
                    for (int i = 0; i < catesList.size(); i++) {
                        if (expandableListView.isGroupExpanded(i)) {
                            expandableListView.collapseGroup(i);
                        }
                    }
                    scrollView_cate.setVisibility(View.GONE);
                }
                break;
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
                break;
            case 1:
                isClicked = true;
                textView_main_cnt_tab1.setTextColor(res.getColor(R.color.black));
                textView_main_cnt_tab2.setTextColor(res.getColor(R.color.red));
                break;
        }
    }

    /**
     * 关闭分类选项
     *
     * @param cate
     */
    private void closeCate(EventCate cate) {
        imageView_title_arrow.setImageResource(R.drawable.arrow_down);
        isArrowUp = false;
        for (int i = 0; i < catesList.size(); i++) {
            if (expandableListView.isGroupExpanded(i)) {
                expandableListView.collapseGroup(i);
            }
        }
        scrollView_cate.setVisibility(View.GONE);
    }

}