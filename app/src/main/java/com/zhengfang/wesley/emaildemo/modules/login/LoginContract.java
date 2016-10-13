package com.zhengfang.wesley.emaildemo.modules.login;

import com.zhengfang.wesley.emaildemo.base.BasePresenter;
import com.zhengfang.wesley.emaildemo.base.BaseView;

/**
 * Created by wesley on 2016/10/9.
 * 登陆界面的契约接口
 */
public interface LoginContract {


    interface View extends BaseView<LoginPresenter>{

        /**
         * 获取邮箱
         * @return 邮箱账号
         */
        String getMail();

        /**
         * 获取密码
         * @return 邮箱密码
         */
        String getPassword();

        /**
         * 登陆
         * @param mail 邮箱账号
         * @param password 密码
         */
        void login(String mail, String password);

        /**
         * 显示错误信息
         * @param errorMessage 错误信息
         */
        void showErrorMessage(String errorMessage);

        /**
         * 登陆成功
         */
        void loginSuccess();

        /**
         * 登陆失败
         */
        void loginFailure(String loginError);

        /**
         * 显示dialog
         */
        void showProgressDialog();

        /**
         * 对话框消失
         */
        void dissmissProgressDialog();
    }


    interface Presenter extends BasePresenter{

        /**
         * 登陆
         * @param mail 邮箱账号
         * @param password 密码
         */
        void login(String mail, String password);

        /**
         * 检查邮箱账号是否为空
         * @param mail 邮箱账号
         * @return 邮箱账号是否为空
         */
        boolean checkMailIsEmpty(String mail);

        /**
         * 检查邮箱账号是否合法
         * @param mail 邮箱账号
         * @return 邮箱账号是否合法
         */
        boolean checkMailIsOk(String mail);

        /**
         * 检查邮箱密码是否为空
         * @param password 邮箱密码
         * @return 邮箱密码是否为空
         */
        boolean checkPassIsEmpty(String password);

    }
}
