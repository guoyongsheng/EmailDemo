package com.zhengfang.wesley.emaildemo.modules.mailcontent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseActivity;
import com.zhengfang.wesley.emaildemo.entitiy.Email;
import com.zhengfang.wesley.emaildemo.modules.mailreplay.MailReplayActivity;
import com.zhengfang.wesley.emaildemo.utils.ActivityUtils;

/**
 * Created by wesley on 2016/10/10.
 * 邮件内容界面
 */
public class MailContentActivity extends BaseActivity {

    private int position;
    private Email email;
    private MailContentFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initVariables() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                email = (Email) bundle.getSerializable("email");
                position = bundle.getInt("position");
            }
        }
    }

    @Override
    public void initViews() {
        Toolbar tb_head = (Toolbar) findViewById(R.id.head_toolbar);
        TextView tv_title = (TextView) findViewById(R.id.head_title);

        setUpToolbar(tb_head, "");
        setUpTitle(tv_title, R.string.mail_content);
        setUpHomeAsUpEnabled(tb_head, true);

        FragmentManager manager = getSupportFragmentManager();
        fragment = (MailContentFragment) manager.findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = MailContentFragment.newInstance(email, position);
            ActivityUtils.addFragmentToActivity(manager, fragment, R.id.content_frame);
        }
    }

    @Override
    public void initPresenter() {
        new MailContentPresenter(fragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mail_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int key = item.getItemId();
        switch (key) {
        /**
         * 回复
         */
        case R.id.menu_mail_content_replay:
            Intent intent = new Intent(this, MailReplayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("email", email);
            intent.putExtras(bundle);
            startActivity(intent);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
