package com.qianfeng.wowsai.activity.my;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.tool.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/4
 */
public class RegisterAct extends Activity implements View.OnClickListener {

    @ViewInject(R.id.editText_register_phoneNum)
    private EditText editText_register_phoneNum;

    @ViewInject(R.id.textView_register_getCode)
    private TextView textView_register_getCode;

    @ViewInject(R.id.editText_register_code)
    private EditText editText_register_code;

    @ViewInject(R.id.editText_register_pwd)
    private EditText editText_register_pwd;

    @ViewInject(R.id.btn_register_register)
    private Button btn_register_register;

    @ViewInject(R.id.textView_register_login)
    private TextView textView_register_login;

    private boolean phoneNumFinished;
    private boolean codeFinished;
    private boolean pwdFinished;

    private int count = 59;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                setTimeText(msg);
            }
        }
    };

    private void setTimeText(Message msg) {
        if (count <= 0) {
            textView_register_getCode.setEnabled(true);
            textView_register_getCode.setText("点击获取验证码");
            return;
        }
        textView_register_getCode.setEnabled(false);
        textView_register_getCode.setTextColor(Color.GRAY);
        textView_register_getCode.setText((count--) + "秒后可重新获取");
        Message localMsg = new Message();
        localMsg.what = 1;
        this.handler.sendMessageDelayed(localMsg, 1000);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewUtils.inject(this);

        addListener();
    }

    private void addListener() {
        textView_register_login.setOnClickListener(this);

        editText_register_phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = editText_register_phoneNum.getText().length();
                if (length == 11) {
                    phoneNumFinished = true;
                    textView_register_getCode.setClickable(true);
                    textView_register_getCode.setTextColor(Color.RED);
                    textView_register_getCode.setBackgroundResource(R.drawable.code1);
                    textView_register_getCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, String> map = new HashMap<>();
                            map.put("app_version", "3.4.1");
                            map.put("device_token", LoginInfoUtil.getDevice_token(RegisterAct.this));
                            map.put("mobile", editText_register_phoneNum.getText().toString());
                            map.put("pushToken", "");
                            HttpUtil.getStringData(StaticData.GET_CODE, RegisterAct.this, map, new HttpUtil.OnSuccessListener() {
                                @Override
                                public void loadData(String result) {
                                    if (result != null) {
                                        try {
                                            JSONObject obj = new JSONObject(result);
                                            String info = obj.getString("info");
                                            if (info.equals("短信下发成功")) {
                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Message msg = Message.obtain();
                                                        msg.what = 1;
                                                        handler.sendMessage(msg);
                                                    }
                                                });
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    });
                } else {
                    phoneNumFinished = false;
                    textView_register_getCode.setClickable(false);
                    textView_register_getCode.setTextColor(Color.GRAY);
                    textView_register_getCode.setBackgroundResource(R.drawable.code2);
                }
            }
        });

        editText_register_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = editText_register_code.getText().length();
                if (length == 6) {
                    codeFinished = true;
                } else {
                    codeFinished = false;
                }
            }
        });

        editText_register_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = editText_register_pwd.getText().length();
                if (length >= 6) {
                    pwdFinished = true;
                } else {
                    pwdFinished = false;
                }
                if (phoneNumFinished && codeFinished && pwdFinished) {
                    btn_register_register.setEnabled(true);
                    btn_register_register.setOnClickListener(RegisterAct.this);
                } else {
                    btn_register_register.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_register:
                //Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                String key = RandomUtil.getRawSeed4();
                Map<String,String> map = new HashMap<>();
                map.put("password", AESUtils4.encryptDef(editText_register_pwd.getText().toString(),key));
                map.put("code",editText_register_code.getText().toString());
                map.put("mobile",editText_register_phoneNum.getText().toString());
                map.put("key",key);

                HttpUtil.getStringData(StaticData.REGISTER, this, map, new HttpUtil.OnSuccessListener() {
                    @Override
                    public void loadData(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            String info = obj.getString("info");
                            Log.e("RegisterAct...",info);
                            Toast.makeText(RegisterAct.this, info, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.textView_register_login:
                startActivity(new Intent(this, LoginAct.class));
                finish();
                break;
        }
    }
}