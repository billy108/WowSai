package com.qianfeng.wowsai.app;

import android.app.Application;
import android.content.res.Resources;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.qianfeng.wowsai.model.Cates;
import com.qianfeng.wowsai.model.User;
import com.qianfeng.wowsai.tool.MemoryCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sky on 2015/4/29.
 */
public class AppCtx extends Application{
    private static AppCtx appCtx;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private Resources resources;
    private List<Cates> catesList;

    public static User user;

    @Override
    public void onCreate() {
        super.onCreate();

        firstCreateInstance();
    }

    protected void firstCreateInstance() {
        if (appCtx == null)
            appCtx = this;
        catesList = new ArrayList<>();
        getRequestQueue();
    }

    public static AppCtx getInstance(){
        return appCtx;
    }

    public Resources getRes(){
        if (resources == null) {
            resources = getResources();
        }
        return resources;
    }

    public ImageLoader getImageLoader(){
        if (imageLoader == null) {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);
            imageLoader = new ImageLoader(requestQueue, new MemoryCache(maxMemory));
        }
        return imageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(this);
        return requestQueue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        AppCtx.user = user;
    }

    public List<Cates> getCatesList() {
        return catesList;
    }

    public void setCatesList(List<Cates> catesList) {
        this.catesList = catesList;
    }
}