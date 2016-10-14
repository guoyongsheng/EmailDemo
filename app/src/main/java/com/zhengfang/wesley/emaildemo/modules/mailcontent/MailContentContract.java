package com.zhengfang.wesley.emaildemo.modules.mailcontent;

import com.zhengfang.wesley.emaildemo.base.BasePresenter;
import com.zhengfang.wesley.emaildemo.base.BaseView;

/**
 * Created by wesley on 2016/10/10.
 * 邮件内容协议接口
 */
interface MailContentContract {

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
         * @param stream 输入流转的字符串
         * @param path 下载路径
         */
        void downLoadAttachment(String stream, String path);
    }
}
