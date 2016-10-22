package com.zhengfang.wesley.emaildemo.modules.homepage;

/**
 * Created by wesley on 2016/10/9.
 * 主页的presenter
 */

class HomePagePresenter implements HomePageContract.Presenter {

    HomePagePresenter(HomePageContract.View view){
        view.setPresenter(this);
    }
}
