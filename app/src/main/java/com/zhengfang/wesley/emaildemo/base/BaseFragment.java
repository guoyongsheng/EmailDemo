package com.zhengfang.wesley.emaildemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wesley on 2016/10/9.
 * 基类的fragment所有的fragment都要继承这个fragment
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView<T>{

    protected T presenter;
    protected Context context;

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(inflater == null){
            return null;
        }

        View view = inflater.inflate(getLayoutId(), container, false);
        initViews(view);
        return view;
    }

    /**
     * 初始化变量
     */
    public abstract void initVariables();

    /**
     * 获取资源id
     * @return 视图id
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     * @param view 控件view
     */
    public abstract void initViews(View view);
}
