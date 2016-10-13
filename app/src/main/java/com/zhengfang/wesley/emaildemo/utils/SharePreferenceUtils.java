package com.zhengfang.wesley.emaildemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wesley on 2016/10/10.
 * 工具类
 */
public class SharePreferenceUtils {

    private SharePreferenceUtils(){

    }


    /**
     * 保存数据到SharedPreferences中
     * @param value 要保存的数据
     * @param name 数据的key
     * @param context 上下文对象
     */
    public static void saveValue(String value, String name, Context context){
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }


    /**
     * 获取数据
     * @param name 数据的key
     * @param context 上下文对象
     * @return 对象
     */
    public static String getValue(String name, Context context){
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }

}
