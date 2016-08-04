package com.qianfeng.wowsai.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.qianfeng.wowsai.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/4/29.
 */
public class CustomRefreshListView extends LinearLayout {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout loadingLayout;
    private ListView listView;
    private TextView textView_time;
    private ImageView imageView_backToTop;
    private Timer timer;
    private Handler handler = new Handler();
    private OnRefreshListener listener;
    private int topHeight = -100;
    private int showHeight = 10;
    private int downSpeed = 2;
    private int upSpeed = 4;
    private int timeOffSet = 10;
    private static int FREQUENCY = 10;
    private String label;
    private int dividerColor = DIVIDERCOLOR;
    private int dividerHeight = DIVIDERHEIGHT;
    private int topPadding = TOPPADDING;
    public static final int DIVIDERCOLOR = Color.GRAY;
    public static final int DIVIDERHEIGHT = 1;
    public static final int TOPPADDING = 0;
    private boolean isComplete;
    private boolean isLoading;
    private boolean isLast;
    private OnLastItemListener onLastItemListener;


    public CustomRefreshListView(Context context) {
        this(context, null);
    }

    public CustomRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshListView(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRefreshListView, defStyle, 0);
        dividerColor = a.getColor(R.styleable.CustomRefreshListView_divider_color, DIVIDERCOLOR);
        dividerHeight = a.getDimensionPixelSize(R.styleable.CustomRefreshListView_divider_height, DIVIDERHEIGHT);
        topPadding = a.getDimensionPixelOffset(R.styleable.CustomRefreshListView_top_padding, TOPPADDING);
        a.recycle();


        View view = LayoutInflater.from(context).inflate(R.layout.custom_refresh_listview, null);
        addView(view);

        loadingLayout = (LinearLayout) view.findViewById(R.id.linearLayout_custom_refresh_loading);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_custom_refresh);
        listView = (ListView) view.findViewById(R.id.listView_custom_refresh);
        textView_time = (TextView) view.findViewById(R.id.textView_custom_refresh_time);
        imageView_backToTop = (ImageView) view.findViewById(R.id.imageView_custom_refresh_backToTop);


        listView.setDivider(new ColorDrawable(dividerColor));
        listView.setDividerHeight(dividerHeight);
//        listView.setPadding(listView.getListPaddingLeft(),topPadding,listView.getPaddingRight(),listView.getPaddingBottom());


        swipeRefreshLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        loadingLayout.setPadding(loadingLayout.getPaddingLeft(), topHeight, loadingLayout.getPaddingRight(), loadingLayout.getPaddingBottom());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (!isLoading) {

                    isLoading = true;
                    isComplete = false;

                    label = DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                            | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                    textView_time.setText(label);

                    refreshAnimation();
                }

                if (listener != null) {
                    listener.onRefresh();
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLast) {
                    if(onLastItemListener != null) {
                        onLastItemListener.onLastItem();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isLast = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });

        imageView_backToTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setSelection(0);
            }
        });


    }

    public ListView getListView() {
        return listView;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public void setOnLastItemListener(OnLastItemListener onLastItemListener) {
        this.onLastItemListener = onLastItemListener;
    }

    public void setBackToTop(boolean flag){
        if(flag){
            imageView_backToTop.setVisibility(VISIBLE);
        }else{
            imageView_backToTop.setVisibility(GONE);
        }
    }

    public void onRefreshComplete() {
        swipeRefreshLayout.setRefreshing(false);
        isComplete = true;
        if (timer != null && isLoading == false) {
            timer.cancel();
            refreshCompleteAnimation();
        }
    }

    private void refreshCompleteAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {


                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                int top = loadingLayout.getPaddingTop();

                                @Override
                                public void run() {

                                    if (top <= topHeight) {
                                        timer.cancel();

                                    }


                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingLayout.setPadding(loadingLayout.getPaddingLeft(), top = top - upSpeed, loadingLayout.getPaddingRight(), loadingLayout.getPaddingBottom());
                                        }
                                    });
                                }
                            }, timeOffSet, FREQUENCY);
                        }

                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshAnimation() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int top = topHeight;

            @Override
            public void run() {

                if (top >= showHeight) {
                    timer.cancel();
                    isLoading = false;
                    if (isComplete) {
                        refreshCompleteAnimation();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadingLayout.setPadding(loadingLayout.getPaddingLeft(), top = top + downSpeed, loadingLayout.getPaddingRight(), loadingLayout.getPaddingBottom());
                    }
                });
            }
        }, timeOffSet, FREQUENCY);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLastItemListener {
        void onLastItem();
    }


}
