package com.zhengfang.wesley.emaildemo.base;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zhengfang.wesley.emaildemo.BuildConfig;
import com.zhengfang.wesley.emaildemo.entitiy.MailInfo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;

/**
 * Created by wesley on 2016/10/9.
 * 基类的application
 */
public class BaseApplication extends Application {

    public static Session session = null;
    public static MailInfo mailInfo = new MailInfo();
    public static int login_from;

    public static List<ArrayList<InputStream>> attachmentsInputStreams = null;


    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init(getPackageName()).logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
    }
}
