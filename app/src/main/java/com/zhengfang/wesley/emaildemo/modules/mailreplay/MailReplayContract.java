package com.zhengfang.wesley.emaildemo.modules.mailreplay;

import com.zhengfang.wesley.emaildemo.base.BasePresenter;
import com.zhengfang.wesley.emaildemo.base.BaseView;
import com.zhengfang.wesley.emaildemo.entitiy.MailInfo;

import javax.mail.Session;

/**
 * Created by wesley on 2016/10/11.
 * 回复界面协议接口
 */
interface MailReplayContract {

    interface View extends BaseView<MailReplayPresenter> {

        /**
         * @return 收件人
         */
        String getReceivers();

        /**
         * @return 邮件主题
         */
        String getSubject();

        /**
         * @return 邮件内容
         */
        String getContent();

        /**
         * 发送邮件
         */
        void sendMail();

        /**
         * 显示ProgressDialog
         */
        void showProgressDialog();

        /**
         * 隐藏ProgressDialog
         */
        void hideProgressDialog();

        /**
         * @param message 发送成功的信息
         */
        void sendSuccess(String message);

        /**
         * @param errorMessage 发送失败的信息
         */
        void sendFailure(String errorMessage);

    }

    interface Presenter extends BasePresenter {

        /**
         * 发送邮件
         * @param mailInfo 邮件信息
         * @param session session
         */
        void sendMail(MailInfo mailInfo, Session session);
    }
}
