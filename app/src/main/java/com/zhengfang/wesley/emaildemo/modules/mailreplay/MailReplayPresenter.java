package com.zhengfang.wesley.emaildemo.modules.mailreplay;

import com.zhengfang.wesley.emaildemo.Constant.Constant;
import com.zhengfang.wesley.emaildemo.entitiy.MailInfo;
import com.zhengfang.wesley.emaildemo.utils.MailHelper;

import javax.mail.Session;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wesley on 2016/10/11.
 * 回复界面的Presenter
 */
class MailReplayPresenter implements MailReplayContract.Presenter {

    private MailReplayContract.View view;

    public MailReplayPresenter(MailReplayContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void sendMail(final MailInfo mailInfo, final Session session) {

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(MailHelper.getInstance().sendTextMail(mailInfo, session));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                view.hideProgressDialog();
                if (aBoolean) {
                    view.sendSuccess(Constant.MAIL_SEND_SUCCESS);
                } else {
                    view.sendFailure(Constant.MAIL_SEND_FAILURE);
                }
            }

            @Override
            public void onStart() {
                view.showProgressDialog();
            }
        });

    }
}
