package com.qianfeng.wowsai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.my.LoginAct;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.EventMessage;
import de.greenrobot.event.EventBus;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/2
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.textView_setting_fragment_myInfo)
    private TextView textView_setting_fragment_myInfo;

    @ViewInject(R.id.textView_setting_fragment_myLevel)
    private TextView textView_setting_fragment_myLevel;

    @ViewInject(R.id.textView_setting_fragment_changePwd)
    private TextView textView_setting_fragment_changePwd;

    @ViewInject(R.id.textView_setting_fragment_goodDetail)
    private TextView textView_setting_fragment_goodDetail;

    @ViewInject(R.id.textView_setting_fragment_classDetail)
    private TextView textView_setting_fragment_classDetail;

    @ViewInject(R.id.btn_set_headline)
    private CheckBox btn_set_headline;

    @ViewInject(R.id.textView_setting_fragment_goodPing)
    private TextView textView_setting_fragment_goodPing;

    @ViewInject(R.id.textView_setting_fragment_clearCash)
    private TextView textView_setting_fragment_clearCash;

    @ViewInject(R.id.textView_setting_fragment_suggest)
    private TextView textView_setting_fragment_suggest;

    @ViewInject(R.id.textView_setting_fragment_about)
    private TextView textView_setting_fragment_about;

    @ViewInject(R.id.textView_setting_fragment_weixin)
    private TextView textView_setting_fragment_weixin;

    @ViewInject(R.id.textView_setting_fragment_version)
    private TextView textView_setting_fragment_version;

    @ViewInject(R.id.textView_setting_fragment_login)
    private TextView textView_setting_fragment_login;

    private AppCtx appCtx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appCtx = AppCtx.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_setting, container, false);
        ViewUtils.inject(this, ret);

        addListener();

        return ret;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (appCtx.getUser() != null) {
            textView_setting_fragment_login.setText("退出登录");
        } else {
            textView_setting_fragment_login.setText("登录");
        }
    }

    private void addListener() {
        textView_setting_fragment_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_setting_fragment_login:
                if (appCtx.getUser() != null) {
                    AppCtx.user = null;
                    textView_setting_fragment_login.setText("登录");

                    EventMessage msg = new EventMessage();
                    msg.setMsg("111");
                    EventBus.getDefault().postSticky(msg);

                } else {
                    startActivity(new Intent(getActivity(), LoginAct.class));
                }
                break;
        }
    }
}