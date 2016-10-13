package com.zhengfang.wesley.emaildemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wesley on 2016/10/9.
 * 基类的activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initVariables();
        initViews();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 获取视图
     * @return 视图的布局
     */
    public abstract int getLayoutId();

    /**
     * 初始化变量---一般是上个界面传过来的变量
     */
    public abstract void initVariables();

    /**
     * 初始化view---视图中的控件
     */
    public abstract void initViews();

    /**
     * 初始化presenter
     */
    public abstract void initPresenter();

    /**
     * 初始化toolbar
     * @param toolbar Toolbar控件
     */
    protected void setUpToolbar(Toolbar toolbar, String toolBarTitle){
        toolbar.setTitle(toolBarTitle);
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化标题
     * @param tv_title textview控件
     * @param titleId 标题资源id
     */
    protected void setUpTitle(TextView tv_title, int titleId){
        tv_title.setText(titleId);
    }


    /**
     * 设置Toolbar的返回按钮点击事件
     * @param toolbar 控件
     * @param isShow 返回按钮是否显示
     */
    protected void setUpHomeAsUpEnabled(Toolbar toolbar, boolean isShow){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(isShow);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
