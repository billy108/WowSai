package com.qianfeng.wowsai.tool;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/3
 */
public class LoginInfoUtil {
    public static String getDevice_token(Context context){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static String getPush_token(Context context){
        return null;
    }
}
