package com.qianfeng.wowsai.activity.my;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.activity.MainActivity;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.tool.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/2
 */
public class LoginAct extends Activity implements View.OnClickListener {
    @ViewInject(R.id.editText_login_userName)
    private EditText editText_login_userName;

    @ViewInject(R.id.editText_login_pwd)
    private EditText editText_login_pwd;

    @ViewInject(R.id.btn_login_login)
    private Button btn_login_login;

    @ViewInject(R.id.textView_login_register)
    private TextView textView_login_register;

    @ViewInject(R.id.textView_login_forget)
    private TextView textView_login_forget;

    @ViewInject(R.id.textView_login_weibo)
    private TextView textView_login_weibo;

    @ViewInject(R.id.textView_login_qq)
    private TextView textView_login_qq;

    @ViewInject(R.id.textView_login_weixin)
    private TextView textView_login_weixin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
        initView();
        addListener();
    }

    private void initView() {
        ViewUtils.inject(this);
        textView_login_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    private void addListener() {
        btn_login_login.setOnClickListener(this);
        textView_login_register.setOnClickListener(this);
        textView_login_forget.setOnClickListener(this);
        textView_login_weibo.setOnClickListener(this);
        textView_login_qq.setOnClickListener(this);
        textView_login_weixin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                String name = editText_login_userName.getText().toString().trim();
                String pwd = editText_login_pwd.getText().toString().trim();
                if (!"".equals(name) && !"".equals(pwd)) {
                    Map<String, String> map = new HashMap<>();
                    String key = RandomUtil.getRawSeed4();
                    String account = AESUtils4.encrypt(name, "saiwai" + key + "saiwai");
                    String password = AESUtils4.encrypt(pwd, "saiwai" + key + "saiwai");

                    map.put("password", password);
                    map.put("system", "android");
                    map.put("app_version", "3.4.1");
                    map.put("device_token", LoginInfoUtil.getDevice_token(this));
                    map.put("opentype", "0");
                    map.put("key", key);
                    map.put("account", account);
                    map.put("push_token", "4d9df420148feb1c41d59c00a01b5e19c955c1cf");

                    HttpUtil.getStringData(StaticData.LOGIN, this, map, new HttpUtil.OnSuccessListener() {
                        @Override
                        public void loadData(String result) {
                            if (result != null) {
                                Map<String, Object> loginInfo = GetJsonInfo.getLoginInfo(result);
                                checkInfo(loginInfo);
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textView_login_register:
                startActivity(new Intent(this, RegisterAct.class));
                finish();
                break;
            case R.id.textView_login_forget:

                break;
            case R.id.textView_login_weibo:
                loginByWeibo();
                break;
            case R.id.textView_login_qq:
                loginByQQ();
                break;
            case R.id.textView_login_weixin:
                loginByWeixin();
                break;
        }
    }

    private void checkInfo(Map<String, Object> loginInfo) {
        if (loginInfo != null) {
            String info = loginInfo.get("info").toString();
            if (info.equals("登录成功")) {
                AppCtx.getInstance().setUser((User) loginInfo.get("user"));
                startActivity(new Intent(this, MainActivity.class));
            }
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }

    private void loginByWeibo() {
        final Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String token;
                if (i == Platform.ACTION_USER_INFOR) {
                    PlatformDb platDB = platform.getDb();//获取数平台数据DB
                    //通过DB获取各种数据
                    token = platDB.getToken();
                    String userId = platDB.getUserId();
                    User user = GetJsonInfo.getWeiboInfo(hashMap);
                    Map<String, String> map = new HashMap<>();
                    map.put("nick", user.getUser_name());
                    map.put("openid", userId);
                    map.put("avatar", user.getHead_pic());
                    map.put("gender", user.getGender());
                    map.put("token", token);
                    map.put("opentype", "1");
                    HttpUtil.getStringData(StaticData.LOGIN, LoginAct.this, map, new HttpUtil.OnSuccessListener() {
                        @Override
                        public void loadData(String result) {
                            if (result != null) {
                                Map<String, Object> loginInfo = GetJsonInfo.getLoginInfo(result);
                                checkInfo(loginInfo);
                                weibo.removeAccount();
                            }
                        }
                    });
                }
            }
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                weibo.removeAccount();
            }
            @Override
            public void onCancel(Platform platform, int i) {
                weibo.removeAccount();
            }
        });
        weibo.showUser(null);
    }

    private void loginByQQ() {
        Platform qq = ShareSDK.getPlatform(this, QZone.NAME);
        qq.SSOSetting(false);  //设置false表示使用SSO授权方式
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("1111-qq", hashMap.toString());
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });
        qq.showUser(null);
    }

    private void loginByWeixin() {
        Platform weixin = ShareSDK.getPlatform(this, QZone.NAME);
        weixin.SSOSetting(false);  //设置false表示使用SSO授权方式
        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("1111-qq", hashMap.toString());

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        weixin.showUser(null);
    }
}