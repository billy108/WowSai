package com.qianfeng.wowsai.tool;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.qianfeng.wowsai.app.AppCtx;

import java.util.Map;

/**
 * Created by Administrator on 2015/4/30.
 */
public class HttpUtil {

    public static void getStringData(String url, final Context context, final OnSuccessListener listener) {
        RequestQueue requestQueue = AppCtx.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.loadData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    public interface OnSuccessListener {
        void loadData(String result);
    }


    public static void getStringData(String url, final Context context, final Map<String, String> map, final OnSuccessListener listener) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.loadData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        AppCtx.getInstance().getRequestQueue().add(request);
    }
}
