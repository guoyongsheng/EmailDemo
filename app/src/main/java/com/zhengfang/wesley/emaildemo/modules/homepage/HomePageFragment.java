package com.zhengfang.wesley.emaildemo.modules.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseFragment;
import com.zhengfang.wesley.emaildemo.modules.detail.DetailActivity;

/**
 * Created by wesley on 2016/10/9.
 * 主页的fragment
 */

public class HomePageFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View, View.OnClickListener {


    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public void initVariables() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initViews(View view) {
        if (view == null) {
            return;
        }
        TextView tv_all_email = (TextView) view.findViewById(R.id.home_page_all_email);
        TextView tv_read_email = (TextView) view.findViewById(R.id.home_page_read_email);
        TextView tv_not_read_email = (TextView) view.findViewById(R.id.home_page_not_read_email);
        tv_all_email.setOnClickListener(this);
        tv_read_email.setOnClickListener(this);
        tv_not_read_email.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        int key = view.getId();
        switch (key) {
        /**
         * 全部邮件
         */
        case R.id.home_page_all_email:
            bundle.putInt("type", 0);
            break;

        /**
         * 已读邮件
         */
        case R.id.home_page_read_email:
            bundle.putInt("type", 1);
            break;


        /**
         * 未读邮件
         */
        case R.id.home_page_not_read_email:
            bundle.putInt("type", 2);
            break;

        default:
            break;
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
