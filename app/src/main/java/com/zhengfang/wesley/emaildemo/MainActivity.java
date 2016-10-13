package com.zhengfang.wesley.emaildemo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.zhengfang.wesley.emaildemo.base.BaseActivity;
import com.zhengfang.wesley.emaildemo.base.BaseApplication;
import com.zhengfang.wesley.emaildemo.modules.login.LoginActivity;

/**
 * 入口界面
 */
public class MainActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews() {
        Button btn_qq_login = (Button) findViewById(R.id.qq_login);
        Button btn_wangyi_login = (Button) findViewById(R.id.wangyi_login);

        btn_qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BaseApplication.login_from = 0;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_wangyi_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.login_from = 1;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initPresenter() {

    }
}
