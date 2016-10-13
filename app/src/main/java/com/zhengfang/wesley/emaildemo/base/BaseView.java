package com.zhengfang.wesley.emaildemo.base;

/**
 * Created by wesley on 2016/10/9.
 * 基类的接口---所有的fragment都要实现这个接口
 */
public interface BaseView<T> {

    /**
     * 让view层持有presenter层的引用
     * @param presenter
     */
    void setPresenter(T presenter);
}
