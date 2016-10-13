package com.zhengfang.wesley.emaildemo.modules.mailreplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseActivity;
import com.zhengfang.wesley.emaildemo.entitiy.Email;
import com.zhengfang.wesley.emaildemo.utils.ActivityUtils;

/**
 * Created by wesley on 2016/10/11.
 * 邮件回复界面
 */
public class MailReplayActivity extends BaseActivity {


    private Email email;
    private MailReplayFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initVariables() {
        handleIntent();
    }

    @Override
    public void initViews() {
        Toolbar tb_head = (Toolbar) findViewById(R.id.head_toolbar);
        TextView tv_title = (TextView) findViewById(R.id.head_title);

        setUpToolbar(tb_head, "");
        setUpTitle(tv_title, R.string.mail_replay);
        setUpHomeAsUpEnabled(tb_head, true);

        FragmentManager manager = getSupportFragmentManager();
        fragment = (MailReplayFragment) manager.findFragmentById(R.id.content_frame);
        if(fragment == null){
            fragment = MailReplayFragment.newInstance(email);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.content_frame);
        }
    }

    @Override
    public void initPresenter() {
        new MailReplayPresenter(fragment);
    }

    //处理上个界面传过来的值
    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                email = (Email) bundle.getSerializable("email");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mail_replay, menu);
        return true;
    }
}
