package com.zhengfang.wesley.emaildemo.modules.login;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseActivity;
import com.zhengfang.wesley.emaildemo.utils.ActivityUtils;

/**
 * Created by wesley on 2016/10/9.
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {


    private LoginFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        Toolbar tb_head = (Toolbar) findViewById(R.id.head_toolbar);
        TextView tv_title = (TextView) findViewById(R.id.head_title);

        setUpToolbar(tb_head, "");
        setUpTitle(tv_title, R.string.login_title);

        FragmentManager manager = getSupportFragmentManager();
        fragment = (LoginFragment) manager.findFragmentById(R.id.content_frame);
        if(fragment == null){
            fragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.content_frame);
        }
    }

    @Override
    public void initPresenter() {
        new LoginPresenter(fragment);
    }
}
