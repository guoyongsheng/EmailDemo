package com.zhengfang.wesley.emaildemo.modules.homepage;

/**
 * Created by wesley on 2016/10/9.
 * 主页的presenter
 */

class HomePagePresenter implements HomePageContract.Presenter {

    private HomePageContract.View view;

    HomePagePresenter(HomePageContract.View view){
        this.view = view;
        view.setPresenter(this);
    }


}
