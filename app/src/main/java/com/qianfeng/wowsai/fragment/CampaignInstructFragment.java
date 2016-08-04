package com.qianfeng.wowsai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;

/**
 * Created by Administrator on 2015/5/2.
 */
public class CampaignInstructFragment extends Fragment {

    @ViewInject(R.id.webView_campaignInstructFragment)
    private WebView webView;

    @ViewInject(R.id.linearLayout_campaignInstructFragment)
    private LinearLayout linearLayout;

    @ViewInject(R.id.imageView_campaignStatus)
    private ImageView imageView_status;

    private String status;

    private boolean isShowing = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_campaigninstruct, container, false);

        ViewUtils.inject(this, view);

        initData();

        addListener();

        return view;
    }

    public void initData() {
        String url = getArguments().getString("url");
        status = getArguments().getString("status");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        switch (status) {
            case "0":
                imageView_status.setImageResource(R.drawable.event_prepare_bt);
                break;
            case "1":
                imageView_status.setImageResource(R.drawable.event_join_bt);
                break;
            case "2":
                imageView_status.setImageResource(R.drawable.event_over_bt);
                break;
        }
    }

    public void addListener() {
        webView.setOnTouchListener(new View.OnTouchListener() {
            int y = webView.getScrollY();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        y = webView.getScrollY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int currentY = webView.getScrollY();
                        if (y < currentY && isShowing) {
                            imgDismiss();
                            isShowing = false;
                        } else if (y > currentY && !isShowing) {
                            imgShow();
                            isShowing = true;
                        }
                        break;
                }
                return false;
            }
        });

    }

    private void imgDismiss() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 300);
        animation.setDuration(400);
        animation.setFillAfter(true);
        imageView_status.setAnimation(animation);
    }

    private void imgShow() {
        int y = imageView_status.getTop();
        int x = imageView_status.getLeft();
        TranslateAnimation animation = new TranslateAnimation(0, 0, 300, 0);
        animation.setDuration(400);
        animation.setFillAfter(true);
        imageView_status.setAnimation(animation);

    }
}