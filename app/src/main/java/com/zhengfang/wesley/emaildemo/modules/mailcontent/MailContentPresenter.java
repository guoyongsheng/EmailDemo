package com.zhengfang.wesley.emaildemo.modules.mailcontent;

import com.zhengfang.wesley.emaildemo.Constant.Constant;
import com.zhengfang.wesley.emaildemo.utils.IOUtil;

import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wesley on 2016/10/10.
 * 邮件内容presenter
 */
class MailContentPresenter implements MailContentContract.Presenter {

    private MailContentContract.View view;

    MailContentPresenter(MailContentContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void downLoadAttachment(final InputStream is, final String path) {

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {

                subscriber.onNext(IOUtil.writeFileFromIS(path, is, false));
                subscriber.onCompleted();
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                view.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean value) {
                if (!value) {
                    view.downLoadFailure(Constant.DOWN_LOAD_FAILURE + " path = " + value);
                } else {
                    view.downLoadSuccess(Constant.DOWN_LOAD_SUCCESS + " path = " + value);
                }
            }

            @Override
            public void onStart() {
                view.showProgressDialog();
            }
        });
    }
}
