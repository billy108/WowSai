package com.qianfeng.wowsai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.course.CourseUserDetailActivity;
import com.qianfeng.wowsai.activity.my.LoginAct;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.EventMessage;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.view.custom.CircleImageView;
import de.greenrobot.event.EventBus;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/2
 */
public class MessageFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.linearLayout_message_fragment_login)
    private LinearLayout linearLayout_message_fragment_login;

    @ViewInject(R.id.imageView_message_fragment_head_img)
    private CircleImageView imageView_message_fragment_head_img;

    @ViewInject(R.id.textView_message_fragment_here)
    private TextView textView_message_fragment_here;

    @ViewInject(R.id.textView_message_fragment_login)
    private TextView textView_message_fragment_login;

    @ViewInject(R.id.textView_message_fragment_comment)
    private TextView textView_message_fragment_comment;

    @ViewInject(R.id.textView_message_fragment_back)
    private TextView textView_message_fragment_back;

    @ViewInject(R.id.textView_message_fragment_callMe)
    private TextView textView_message_fragment_callMe;

    @ViewInject(R.id.textView_message_fragment_private)
    private TextView textView_message_fragment_private;

    @ViewInject(R.id.textView_message_fragment_handCircle)
    private TextView textView_message_fragment_handCircle;

    private AppCtx appCtx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appCtx = AppCtx.getInstance();

        EventBus.getDefault().registerSticky(this,"logout", EventMessage.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_message, container, false);
        ViewUtils.inject(this, ret);

        addListener();

        return ret;
    }

    private void addListener() {
        linearLayout_message_fragment_login.setOnClickListener(this);
        textView_message_fragment_here.setOnClickListener(this);
        textView_message_fragment_login.setOnClickListener(this);
        textView_message_fragment_comment.setOnClickListener(this);
        textView_message_fragment_back.setOnClickListener(this);
        textView_message_fragment_callMe.setOnClickListener(this);
        textView_message_fragment_private.setOnClickListener(this);
        textView_message_fragment_handCircle.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = AppCtx.getInstance().getUser();
        if (user != null) {
            textView_message_fragment_login.setVisibility(View.GONE);
            textView_message_fragment_here.setText(user.getUser_name());
            appCtx.getImageLoader().get(user.getHead_pic(), ImageLoader.getImageListener(imageView_message_fragment_head_img, R.drawable.sgk_avater_normal, R.drawable.sgk_avater_normal));
        } else {
            textView_message_fragment_login.setVisibility(View.VISIBLE);
            textView_message_fragment_here.setText(getResources().getString(R.string.msg_here));
            imageView_message_fragment_head_img.setImageResource(R.drawable.sgk_avater_normal);
        }
    }

    public void logout(EventMessage msg){
        if (msg.getMsg().equals("111")){
            textView_message_fragment_login.setVisibility(View.VISIBLE);
            textView_message_fragment_here.setText(getResources().getString(R.string.msg_here));
            imageView_message_fragment_head_img.setImageResource(R.drawable.sgk_avater_normal);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle;
        switch (v.getId()) {
            case R.id.textView_message_fragment_login:
            case R.id.textView_message_fragment_here:
            case R.id.linearLayout_message_fragment_login:
                if (appCtx.getUser() == null) {
                    intent.setClass(getActivity(), LoginAct.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getActivity(), CourseUserDetailActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("user", appCtx.getUser());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.textView_message_fragment_comment:
                break;
            case R.id.textView_message_fragment_back:
                break;
            case R.id.textView_message_fragment_callMe:
                break;
            case R.id.textView_message_fragment_private:
                break;
            case R.id.textView_message_fragment_handCircle:
                break;
        }
    }
}