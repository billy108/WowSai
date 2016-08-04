package com.qianfeng.wowsai.activity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.app.AppCtx;
import com.qianfeng.wowsai.fragment.MainFragment;
import com.qianfeng.wowsai.model.Cates;
import com.qianfeng.wowsai.tool.GetJsonInfo;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.StaticData;

import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    @ViewInject(R.id.textView_main_find)
    private TextView textView_main_find;
    @ViewInject(R.id.textView_main_course)
    private TextView textView_main_course;
    @ViewInject(R.id.textView_main_rank)
    private TextView textView_main_rank;
    @ViewInject(R.id.textView_main_mine)
    private TextView textView_main_mine;

    private TextView[] textViews;
    private boolean isConnected;

    private MainFragment findFragment;
    private MainFragment courseFragment;
    private MainFragment rankFragment;
    private MainFragment mineFragment;

    private FragmentManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ViewUtils.inject(this);

        checkNetwork();

        initData();
        initView();

        setSelected(0);
        changeColor(0);
    }

    private void initData() {
        textViews = new TextView[]{textView_main_find, textView_main_course, textView_main_rank, textView_main_mine};
        manager = getSupportFragmentManager();
        //获得分类列表
        if (isConnected){
            HttpUtil.getStringData(StaticData.CATES, this, new HttpUtil.OnSuccessListener() {
                @Override
                public void loadData(String result) {
                    if (result != null) {
                        List<Cates> catesInfo = GetJsonInfo.getCatesInfo(result);
                        if (catesInfo != null) {
                            AppCtx.getInstance().setCatesList(catesInfo);
                            Log.e("1111-cate",catesInfo.toString());
                        }
                    }
                }
            });
        }
    }

    private void initView() {
        textView_main_find.setOnClickListener(this);
        textView_main_course.setOnClickListener(this);
        textView_main_rank.setOnClickListener(this);
        textView_main_mine.setOnClickListener(this);
    }


    /**
     * 选择要显示的Fragment
     *
     * @param index
     */
    private void setSelected(int index) {
        if (isConnected) {
            Bundle bundle = new Bundle();
            bundle.putInt("tabIndex", index);
            FragmentTransaction transaction = manager.beginTransaction();
            hideFragment(transaction);
            switch (index) {
                case 0:
                    if (findFragment == null) {
                        findFragment = new MainFragment();
                        findFragment.setArguments(bundle);
                        transaction.add(R.id.layout_main_container, findFragment);
                    } else {
                        transaction.show(findFragment);
                    }
                    break;
                case 1:
                    if (courseFragment == null) {
                        courseFragment = new MainFragment();
                        courseFragment.setArguments(bundle);
                        transaction.add(R.id.layout_main_container, courseFragment);
                    } else {
                        transaction.show(courseFragment);
                    }
                    break;
                case 2:
                    if (rankFragment == null) {
                        rankFragment = new MainFragment();
                        rankFragment.setArguments(bundle);
                        transaction.add(R.id.layout_main_container, rankFragment);
                    } else {
                        transaction.show(rankFragment);
                    }
                    break;
                case 3:
                    if (mineFragment == null) {
                        mineFragment = new MainFragment();
                        mineFragment.setArguments(bundle);
                        transaction.add(R.id.layout_main_container, mineFragment);
                    } else {
                        transaction.show(mineFragment);
                    }
                    break;
            }
            transaction.commit();
        }
    }

    /**
     * 更改Tab图标颜色
     *
     * @param index
     */
    private void changeColor(int index) {
        for (int i = 0; i < 4; i++) {
            if (i == index) {
                textViews[i].setEnabled(false);
                textViews[i].setTextColor(getResources().getColor(R.color.red));
            } else {
                textViews[i].setEnabled(true);
                textViews[i].setTextColor(getResources().getColor(R.color.black));
            }
        }
    }

    /**
     * 隐藏所有Fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (courseFragment != null) {
            transaction.hide(courseFragment);
        }
        if (rankFragment != null) {
            transaction.hide(rankFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    /**
     * 检查网络
     */
    private void checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            isConnected = true;
        } else {
            Toast.makeText(this, "网络异常,请检查", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_main_find:
                setSelected(0);
                changeColor(0);
                break;
            case R.id.textView_main_course:
                setSelected(1);
                changeColor(1);
                break;
            case R.id.textView_main_rank:
                setSelected(2);
                changeColor(2);
                break;
            case R.id.textView_main_mine:
                setSelected(3);
                changeColor(3);
                break;
        }
    }

    /**
     * 再按一次退出程序
     */

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}