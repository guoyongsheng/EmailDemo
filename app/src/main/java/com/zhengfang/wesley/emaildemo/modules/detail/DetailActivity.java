package com.zhengfang.wesley.emaildemo.modules.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseActivity;
import com.zhengfang.wesley.emaildemo.utils.ActivityUtils;

/**
 * Created by wesley on 2016/10/9.
 * 详情界面
 */
public class DetailActivity extends BaseActivity {


    private DetailFragment fragment;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initVariables() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                type = bundle.getInt("type");
            }
        }
    }

    @Override
    public void initViews() {

        Toolbar tb_head = (Toolbar) findViewById(R.id.head_toolbar);
        TextView tv_title = (TextView) findViewById(R.id.head_title);
        setUpToolbar(tb_head, "");
        setUpTitle(tv_title, R.string.detail);

        FragmentManager manager = getSupportFragmentManager();
        fragment = (DetailFragment) manager.findFragmentById(R.id.content_frame);
        if(fragment == null){
            fragment = DetailFragment.newInstance(type);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.content_frame);
        }
    }

    @Override
    public void initPresenter() {
        new DetailPresenter(fragment);
    }
}
