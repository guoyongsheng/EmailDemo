package com.zhengfang.wesley.emaildemo.modules.mailcontent;

import com.zhengfang.wesley.emaildemo.base.BasePresenter;
import com.zhengfang.wesley.emaildemo.base.BaseView;

import java.io.InputStream;

/**
 * Created by wesley on 2016/10/10.
 */

public interface MailContentContract {

    interface View extends BaseView<MailContentPresenter> {

        /**
         * 下载成功
         */
        void downLoadSuccess(String message);

        /**
         * 下载失败
         */
        void downLoadFailure(String errorMessage);

        /**
         * 显示progressdialog
         */
        void showProgressDialog();

        /**
         * 隐藏progressdialog
         */
        void hideProgressDialog();
    }

    interface Presenter extends BasePresenter {

        /**
         * 下载附件
         * @param is 输入流
         * @param path 下载路径
         */
        void downLoadAttachment(InputStream is, String path);
    }
}
