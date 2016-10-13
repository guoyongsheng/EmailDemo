package com.zhengfang.wesley.emaildemo.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by wesley on 2016/10/10.
 * 工具类
 */
public class GsonUtils<T> {


    private static volatile GsonUtils instance;

    private GsonUtils(){}



    public static GsonUtils getInstance(){
        if(instance == null){
            synchronized (GsonUtils.class){
                if(instance == null){
                    instance = new GsonUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 对象转字符串
     * @param t
     * @return
     */
    public String objectToString(T t) {

        Gson gson = new Gson();
        return gson.toJson(t);
    }


    /**
     * 字符串转对象
     * @param value
     * @return
     */
    public T stringToObject(String value, Type type){
        Gson gson = new Gson();
        return gson.fromJson(value, type);
    }

}
