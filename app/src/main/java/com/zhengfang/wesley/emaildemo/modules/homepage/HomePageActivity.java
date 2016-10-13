package com.zhengfang.wesley.emaildemo.modules.homepage;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseActivity;
import com.zhengfang.wesley.emaildemo.utils.ActivityUtils;

/**
 * Created by wesley on 2016/10/9.
 * 主页
 */
public class HomePageActivity extends BaseActivity {


    private Toolbar tb_head; //toolbar
    private TextView tv_title; //标题
    private HomePageFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        tb_head = (Toolbar) findViewById(R.id.head_toolbar);
        tv_title = (TextView) findViewById(R.id.head_title);
        setUpToolbar(tb_head, "");
        setUpTitle(tv_title, R.string.home_page);

        FragmentManager manager = getSupportFragmentManager();
        fragment = (HomePageFragment) manager.findFragmentById(R.id.content_frame);
        if(fragment == null){
            fragment = HomePageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.content_frame);
        }
    }

    @Override
    public void initPresenter() {
        new HomePagePresenter(fragment);
    }
}
