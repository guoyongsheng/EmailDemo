package com.zhengfang.wesley.emaildemo.modules.login;

import android.text.TextUtils;

import com.zhengfang.wesley.emaildemo.Constant.Constant;
import com.zhengfang.wesley.emaildemo.utils.EmailFormatUtils;
import com.zhengfang.wesley.emaildemo.utils.MailHelper;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wesley on 2016/10/9.
 * 登陆界面的presenter层
 */
class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    LoginPresenter(LoginContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void login(final String mail, final String password) {
        if (checkMailIsEmpty(mail)) {
            view.showErrorMessage(Constant.MAIL_IS_EMPTY);
            return;
        }

        if (checkMailIsOk(mail)) {
            view.showErrorMessage(Constant.MAIL_FORMAT_ERROR);
            return;
        }

        if (checkPassIsEmpty(password)) {
            view.showErrorMessage(Constant.PASS_IS_EMPTY);
            return;
        }

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(MailHelper.getInstance().login(mail, password));
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                view.dissmissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (!aBoolean) {
                    view.loginFailure(Constant.LOGIN_FAILURE);
                } else {
                    view.loginSuccess();
                }
            }

            @Override
            public void onStart() {
                view.showProgressDialog();
            }
        });
    }

    @Override
    public boolean checkMailIsEmpty(String mail) {
        return TextUtils.isEmpty(mail);
    }

    @Override
    public boolean checkMailIsOk(String mail) {

        return !EmailFormatUtils.emailFormat(mail);
    }

    @Override
    public boolean checkPassIsEmpty(String password) {
        return TextUtils.isEmpty(password);
    }
}
