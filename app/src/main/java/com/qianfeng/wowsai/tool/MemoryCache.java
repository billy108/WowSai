package com.qianfeng.wowsai.tool;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/3
 */
public class MemoryCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    private final String TAG = this.getClass().getSimpleName();

    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        Log.v(TAG, "Retrieved item from Mem Cache");
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        Log.v(TAG, "Added item to Mem Cache");
        put(url, bitmap);
    }
}