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
public class CustomRefreshGridView extends LinearLayout {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout loadingLayout;
    private GridView gridView;
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
    private boolean isComplete;
    private boolean isLoading;
    private boolean isLast;
    private OnLastItemListener onLastItemListener;
    private int columnNum;
    public static final int COLUMNNUM = 1;


    public CustomRefreshGridView(Context context) {
        this(context, null);
    }

    public CustomRefreshGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshGridView(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRefreshGridView, defStyle, 0);
        columnNum = a.getInteger(R.styleable.CustomRefreshGridView_column_num,COLUMNNUM);
        a.recycle();


        View view = LayoutInflater.from(context).inflate(R.layout.custom_refresh_gridview, null);
        addView(view);

        loadingLayout = (LinearLayout) view.findViewById(R.id.linearLayout_custom_refresh_loading);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_custom_refresh);
        gridView = (GridView) view.findViewById(R.id.gridView_custom_refresh);
        textView_time = (TextView) view.findViewById(R.id.textView_custom_refresh_time);
        imageView_backToTop = (ImageView) view.findViewById(R.id.imageView_custom_refresh_backToTop);


        gridView.setNumColumns(columnNum);


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

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                gridView.setSelection(0);
            }
        });


    }

    public GridView getGridView() {
        return gridView;
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
