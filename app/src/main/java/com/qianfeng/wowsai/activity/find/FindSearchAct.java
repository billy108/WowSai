package com.qianfeng.wowsai.activity.find;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qianfeng.wowsai.R;
import com.qianfeng.wowsai.adapter.SectionAdapter;
import com.qianfeng.wowsai.tool.GetJsonInfo;
import com.qianfeng.wowsai.tool.HttpUtil;
import com.qianfeng.wowsai.tool.StaticData;
import com.qianfeng.wowsai.view.custom.PinnedHeaderListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class FindSearchAct extends Activity {

    @ViewInject(R.id.editText_search_keyword)
    private EditText editText_search_keyword;

    @ViewInject(R.id.textView_search_search)
    private TextView textView_search_search;

    @ViewInject(R.id.listView_search_show)
    private PinnedHeaderListView pinnedHeaderListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_search);

        ViewUtils.inject(this);

        addListener();
    }

    private void addListener() {
        textView_search_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editText_search_keyword.getText().toString();
                if (!"".equals(keyword)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("keyword", keyword);
                    HttpUtil.getStringData(StaticData.SEARCH, FindSearchAct.this, map, new HttpUtil.OnSuccessListener() {
                        @Override
                        public void loadData(String result) {
                            if (result != null) {
                                List<Map<String, Object>> searchInfo = GetJsonInfo.getSearchInfo(result);
                                checkInfo(searchInfo);
                            }
                        }
                    });
                } else {
                    Toast.makeText(FindSearchAct.this, "请输入关键字", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkInfo(List<Map<String, Object>> searchInfo) {
        if (searchInfo != null) {
            String info = searchInfo.get(0).get("info").toString();
            if (info.contains("成功")) {
                searchInfo.remove(0);
                SectionAdapter adapter = new SectionAdapter(searchInfo,this);
                pinnedHeaderListView.setAdapter(adapter);
            } else {
                Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
            }
        }
    }
}