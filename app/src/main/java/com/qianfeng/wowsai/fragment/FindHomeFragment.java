package com.qianfeng.wowsai.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.toolbox.ImageLoader;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseAllDetailActivity;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.activity.find.*;
import com.qianfeng.wowsai.adapter.CustomCyclePagerAdapter;
import com.qianfeng.wowsai.adapter.HomeCourseAdapter;
import com.qianfeng.wowsai.adapter.HomeTopicAdapter;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.*;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.JsonUtils;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import com.qianfeng.wowsai.view.custom.MyGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/4/29.
 */
public class FindHomeFragment extends Fragment {

    @ViewInject(R.id.swipeRefreshLayout_findHomeFragment)
    private SwipeRefreshLayout swipeRefreshLayout;

    @ViewInject(R.id.imageView_findHomeFragment_loading)
    private ImageView imageView_loading;

    @ViewInject(R.id.scrollView_findHomeFragment)
    private ScrollView scrollView;

    @ViewInject(R.id.viewPager_findHomeFragment_slide)
    private ViewPager viewPager;

    @ViewInject(R.id.linearLayout_findHomeFragment_dots)
    private LinearLayout linearLayout_dots;

    @ViewInject(R.id.circleImageView_findHomeFragment_nav1)
    private CircleImageView circleImageView_nav1;
    @ViewInject(R.id.circleImageView_findHomeFragment_nav2)
    private CircleImageView circleImageView_nav2;
    @ViewInject(R.id.circleImageView_findHomeFragment_nav3)
    private CircleImageView circleImageView_nav3;

    @ViewInject(R.id.textView_findHomeFragment_nav1)
    private TextView textView_nav1;
    @ViewInject(R.id.textView_findHomeFragment_nav2)
    private TextView textView_nav2;
    @ViewInject(R.id.textView_findHomeFragment_nav3)
    private TextView textView_nav3;

    @ViewInject(R.id.imageView_findHomeFragment_advImg1)
    private ImageView imageView_adv1;
    @ViewInject(R.id.imageView_findHomeFragment_advImg2)
    private ImageView imageView_adv2;

    @ViewInject(R.id.linearLayout_findHomeFragment_daren)
    private LinearLayout linearLayout_daren;
    @ViewInject(R.id.linearLayout_findHomeFragment_topic)
    private LinearLayout linearLayout_topic;
    @ViewInject(R.id.linearLayout_findHomeFragment_course)
    private LinearLayout linearLayout_course;

    @ViewInject(R.id.linearLayout_findHomeFragment_darenBg)
    private LinearLayout linearLayout_darenBg;

    @ViewInject(R.id.circleImageView_findHomeFragment_daren)
    private CircleImageView circleImageView_daren;

    @ViewInject(R.id.textView_findHomeFragment_uName)
    private TextView textView_uName;

    @ViewInject(R.id.textView_findHomeFragment_region)
    private TextView textView_region;

    @ViewInject(R.id.textView_findHomeFragment_otherInfo)
    private TextView textView_otherInfo;

    @ViewInject(R.id.textView_findHomeFragment_guanStatus)
    private TextView textView_guanStatus;

    @ViewInject(R.id.myGridView_findHomeFragment_topic)
    private MyGridView myGridView_topic;

    @ViewInject(R.id.myGridView_findHomeFragment_course)
    private MyGridView myGridView_course;

    @ViewInject(R.id.imageView_findHomeFragment_darenBg)
    private ImageView imageView_darenBg;

    private CircleImageView[] circleImageViewNAVs;
    private TextView[] textViewNAVs;
    private ImageView[] imageViewADVs;
    private ImageView[] dots;

    private Context context;

    private Home home;
    private List<View> viewPagerList;

    private ImageLoader imageLoader;

    private boolean isSending;

    private AnimationDrawable animationDrawable;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (dots != null) {
                        int currentItem = viewPager.getCurrentItem();
                        int toItem = (currentItem + 1) % viewPagerList.size();
                        viewPager.setCurrentItem(toItem, true);
                    }
                    // 每2秒钟发送一个message，用于切换viewPager中的图片
                    this.sendEmptyMessageDelayed(1, 2000);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findhome, container, false);
        ViewUtils.inject(this, view);

        initView();

        initData();

        addListener();

        return view;
    }

    public void initView() {
        context = getActivity();

        //加载动画
        imageView_loading.setBackgroundResource(R.anim.ball_jump);
        animationDrawable= (AnimationDrawable) imageView_loading.getBackground();
        animationDrawable.start();
        swipeRefreshLayout.setVisibility(View.INVISIBLE);



        viewPager.setFocusable(true);
        viewPager.setFocusableInTouchMode(true);
        viewPager.requestFocus();


        int scaleWidth = context.getResources().getDisplayMetrics().widthPixels;
        int height = scaleWidth * 275 / 640;

        viewPager.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        int width = scaleWidth / 2;
        height = width / 2;
        imageView_adv1.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        imageView_adv2.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        swipeRefreshLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);


    }

    public void initData() {
        circleImageViewNAVs = new CircleImageView[4];
        circleImageViewNAVs[0] = circleImageView_nav1;
        circleImageViewNAVs[2] = circleImageView_nav2;
        circleImageViewNAVs[3] = circleImageView_nav3;

        textViewNAVs = new TextView[4];
        textViewNAVs[0] = textView_nav1;
        textViewNAVs[2] = textView_nav2;
        textViewNAVs[3] = textView_nav3;

        imageViewADVs = new ImageView[2];
        imageViewADVs[0] = imageView_adv1;
        imageViewADVs[1] = imageView_adv2;

        imageLoader = AppCtx.getInstance().getImageLoader();

        loadData();
    }

    public void addListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                selectDot(i % viewPagerList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        linearLayout_daren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,DarenActivity.class);
                startActivity(intent);
            }
        });

        linearLayout_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,DarenActivity.class);
                startActivity(intent);
            }
        });

        linearLayout_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,TopicActivity.class);
                startActivity(intent);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData(){
        HttpUtil.getStringData(StaticData.FIND_SHOUYE, context, new HttpUtil.OnSuccessListener() {
            @Override
            public void loadData(String result) {
                home = JsonUtils.parseHome(result);
                if (home != null) {
                    Log.e("hahhaha","ffff");
                    loadUI();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(context, "数据错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUI() {

        initSlide();
        initNAVList();
        initADVList();
        initDaren();
        initTopic();
        initCourse();

        if (animationDrawable != null){
            animationDrawable.stop();
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            imageView_loading.setVisibility(View.GONE);
            scrollView.scrollTo(0,-200);
        }
    }

    private void initSlide() {
        linearLayout_dots.removeAllViews();
        List<Slide> slideList = home.getSlide();
        dots = new ImageView[slideList.size()];
        viewPagerList = new ArrayList<>();
        for (int i = 0; i < slideList.size(); i++) {
            final Slide slide = slideList.get(i);
            ImageView imageView = new ImageView(context);
//            Picasso.with(context).load(slide.getHost_pic()).placeholder(R.drawable.sgk_img_default_big).into(imageView);
            imageLoader.get(slide.getHost_pic(), ImageLoader.getImageListener(imageView,R.drawable.sgk_img_default_big,R.drawable.sgk_img_default_big));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewPagerList.add(imageView);
            ImageView dotImg = new ImageView(context);
            dotImg.setImageResource(R.drawable.dot);
            dots[i] = dotImg;
            linearLayout_dots.addView(dotImg);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (slide.getItemtype()){
                        case "topic":
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("topicId",slide.getHand_id());
                            bundle.putString("topicSubject",slide.getSubject());
                            intent.setClass(context, TopicDetailActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case "course":
                            Intent intent1 = new Intent();
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("id",slide.getHand_id());
                            intent1.setClass(context, CourseAllDetailActivity.class);
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                            break;
                        case "event":
                            Intent intent2 = new Intent();
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("cid",slide.getHand_id());
                            bundle2.putString("status","1");
                            intent2.setClass(context, CampaignDetailActivity.class);
                            intent2.putExtras(bundle2);
                            startActivity(intent2);
                            break;
                        case "class":
                            //TODO 跳转到课堂界面
                            break;
                    }
                }
            });
        }
        selectDot(0);

        CustomCyclePagerAdapter adapter = new CustomCyclePagerAdapter(viewPagerList);
        viewPager.setAdapter(adapter);
    }

    private void selectDot(int position) {
        for (int i = 0; i < dots.length; i++) {
            dots[i].setEnabled(true);
        }
        dots[position].setEnabled(false);
    }

    private void initNAVList() {
        List<Nav> navList = home.getNav();

        for (int i = 0; i < navList.size(); i++) {
            final Nav nav = navList.get(i);
            if (i == 1) {
                continue;
            }

            imageLoader.get(nav.getHost_pic(), ImageLoader.getImageListener(circleImageViewNAVs[i], R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));

            textViewNAVs[i].setText(nav.getSubject());
            final String type = nav.getItemtype();
            circleImageViewNAVs[i].setOnClickListener(new View.OnClickListener() {
                Intent intent = new Intent();

                @Override
                public void onClick(View v) {
                    switch (type) {
                        case "nav_daren":
                            intent.setClass(context, DarenActivity.class);
                            break;
                        case "nav_event":
                            intent.setClass(context, CampaignActivity.class);
                            break;
                        case "nav_topic":
                            intent.setClass(context, TopicActivity.class);
                            break;
                    }
                    startActivity(intent);
                }
            });
        }

        if(!isSending) {
            isSending = true;
            mHandler.sendEmptyMessageDelayed(1, 2000);

        }
    }

    private void initADVList() {
        List<Adv> advList = home.getAdv();

        for (int i = 0; i < 2; i++) {
            Adv adv = advList.get(i);
            imageLoader.get(adv.getAd_img(), ImageLoader.getImageListener(imageViewADVs[i], R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));
            final String subject = adv.getSubject();
            imageViewADVs[i].setOnClickListener(new View.OnClickListener() {
                Intent intent = new Intent();

                @Override
                public void onClick(View v) {
                    switch (subject) {
                        case "手工市集":
                            //TODO 跳转手工市集
                            break;
                        case "互动课堂":
                            //TODO 跳转互动课堂
                            break;
                    }
                }
            });
        }
    }

    private void initDaren() {
        final User daren = home.getDaren();


        textView_uName.setText(daren.getUser_name());
        textView_region.setText(daren.getRegion());
        textView_otherInfo.setText("教程" + daren.getCourse_count() + "·粉丝" + daren.getFan_count() + "·手工圈" + daren.getOpus_count());
        switch (daren.getGuanStatus()) {
            case "0":
                textView_guanStatus.setText("关注");
                break;
            case "1":
                textView_guanStatus.setText("已关注");
                break;
        }

        textView_guanStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = AppCtx.getInstance().getUser();
                if(user != null){
                    switch (textView_guanStatus.getText().toString()) {
                        case "关注":
                            textView_guanStatus.setText("已关注");
                            break;
                        case "已关注":
                            textView_guanStatus.setText("关注");
                            break;
                    }
                }else{
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        int height = linearLayout_darenBg.getHeight();
        int width = linearLayout_darenBg.getWidth();

        imageView_darenBg.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        imageView_darenBg.setScaleType(ImageView.ScaleType.CENTER_CROP);

//        Picasso.with(context).load(daren.getBg_image()).resize(width, height).centerCrop().placeholder(R.drawable.sgk_userhome_bg).into(imageView_darenBg);
        imageLoader.get(daren.getBg_image(), ImageLoader.getImageListener(imageView_darenBg,R.drawable.sgk_userhome_bg,R.drawable.sgk_userhome_bg));
        imageLoader.get(daren.getHead_pic(), ImageLoader.getImageListener(circleImageView_daren, R.drawable.sgk_img_default_big, R.drawable.sgk_img_default_big));

        linearLayout_darenBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",daren);
                intent.setClass(context, CourseUserDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    private void initTopic() {
        final List<Topic> list = home.getTopics();
        HomeTopicAdapter adapter = new HomeTopicAdapter(list, context);
        myGridView_topic.setAdapter(adapter);

        myGridView_topic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic topic = list.get(position);
                Intent intent = new Intent();
                intent.setClass(context,TopicDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("topicId",topic.getId());
                bundle.putString("topicSubject",topic.getSubject());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initCourse() {
        final List<Course> list = home.getCourses();
        HomeCourseAdapter adapter = new HomeCourseAdapter(list, context);
        myGridView_course.setAdapter(adapter);


        myGridView_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = list.get(position);
                Intent intent = new Intent();
                intent.setClass(context, CourseAllDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", course.getHand_id());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }


}