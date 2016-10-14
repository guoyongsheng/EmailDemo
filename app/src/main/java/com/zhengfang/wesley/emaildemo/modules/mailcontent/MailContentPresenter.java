package com.zhengfang.wesley.emaildemo.modules.mailcontent;

import com.zhengfang.wesley.emaildemo.Constant.Constant;
import com.zhengfang.wesley.emaildemo.utils.IOUtil;

import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
    public void downLoadAttachment(final String stream, final String path) {

        Observable.create(new Observable.OnSubscribe<InputStream>() {
            @Override
            public void call(Subscriber<? super InputStream> subscriber) {

                subscriber.onNext(IOUtil.getStringStream(stream));
                subscriber.onCompleted();
            }
        }).map(new Func1<InputStream, Boolean>() {
            @Override
            public Boolean call(InputStream inputStream) {
                return IOUtil.writeFileFromIS(path, inputStream, false);
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
                    view.downLoadFailure(Constant.DOWN_LOAD_FAILURE + " path = " + path);
                } else {
                    view.downLoadSuccess(Constant.DOWN_LOAD_SUCCESS + " path = " + path);
                }
            }

            @Override
            public void onStart() {
                view.showProgressDialog();
            }
        });
    }
}
