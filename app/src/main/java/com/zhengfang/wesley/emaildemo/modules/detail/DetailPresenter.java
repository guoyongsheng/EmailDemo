package com.zhengfang.wesley.emaildemo.modules.detail;

import com.zhengfang.wesley.emaildemo.Constant.Constant;
import com.zhengfang.wesley.emaildemo.base.BaseApplication;
import com.zhengfang.wesley.emaildemo.entitiy.Email;
import com.zhengfang.wesley.emaildemo.entitiy.MailReceiver;
import com.zhengfang.wesley.emaildemo.utils.MailHelper;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wesley on 2016/10/9.
 * 详情界面的presenter
 */
class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View view;

    DetailPresenter(DetailContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadData(final int type, final int startPosition) {

        Observable.create(new Observable.OnSubscribe<List<MailReceiver>>() {

            @Override
            public void call(Subscriber<? super List<MailReceiver>> subscriber) {

                if (BaseApplication.login_from == 0) {
                    subscriber.onNext(MailHelper.getInstance().getAllMailBySSL(startPosition));
                } else {
                    subscriber.onNext(MailHelper.getInstance().getAllMail(startPosition));
                }
                subscriber.onCompleted();
            }
        }).map(new Func1<List<MailReceiver>, List<Email>>() {
            @Override
            public List<Email> call(List<MailReceiver> mailReceivers) {
                return MailHelper.getInstance().getListMail(mailReceivers);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Email>>() {

            @Override
            public void onCompleted() {
                view.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Email> emails) {
                view.saveMail(emails);
                view.showData(emails);
            }

            @Override
            public void onStart() {
                view.showProgressDialog();
            }
        });
    }

    @Override
    public void deleteMailById(final String messageId, final int position) {

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {

                if (BaseApplication.login_from == 0) {
                    subscriber.onNext(MailHelper.getInstance().deleteMailBySSL(messageId));
                } else {
                    subscriber.onNext(MailHelper.getInstance().deleteMessage(messageId));
                }
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                view.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    view.deleteSuccess(Constant.DELETE_SUCCESS, position);
                } else {
                    view.deleteFailure(Constant.DELETE_FAILURE);
                }
            }

            @Override
            public void onStart() {
                view.showProgressDialog();
            }
        });
    }

    @Override
    public void saveMail(final List<Email> list) {

        // Realm realm = Realm.getDefaultInstance();
        //realm.beginTransaction();
        // realm.copyToRealm(list);
        // realm.commitTransaction();
        //realm.close();
    }

    @Override
    public void getLocalData() {

        //Realm realm = Realm.getDefaultInstance();
        //view.showData(realm.where(Email.class).findAll());
        //realm.close();
    }
}
