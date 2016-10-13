package com.zhengfang.wesley.emaildemo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by wesley on 2016/10/9.
 * 工具类---activity添加fragment的工具类
 */
public class ActivityUtils {


    private ActivityUtils(){

    }

    /**
     * fragment添加到activity中
     * @param fragmentManager 资源管理器
     * @param fragment 要添加的fragment
     * @param contentId 资源id
     */
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int contentId){
        if(fragmentManager == null || fragment == null){
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(contentId, fragment);
        transaction.commit();
    }
}
