package com.zhengfang.wesley.emaildemo.modules.detail;

import com.zhengfang.wesley.emaildemo.base.BasePresenter;
import com.zhengfang.wesley.emaildemo.base.BaseView;
import com.zhengfang.wesley.emaildemo.entitiy.Email;

import java.util.List;

/**
 * Created by wesley on 2016/10/9.
 * 详情界面的契约类
 */
interface DetailContract {

    interface View extends BaseView<DetailPresenter> {

        /**
         * 加载数据
         *
         * @param type          类型
         * @param startPosition 开始加载的位置
         * @param isRefreshing  true:下拉刷新 false:滚动加载
         */
        void loadData(int type, int startPosition, boolean isRefreshing);

        /**
         * 显示数据
         *
         * @param list 数据集合
         */
        void showData(List<Email> list);

        /**
         * 显示ProgressDialog
         */
        void showProgressDialog();

        /**
         * 隐藏ProgressDialog
         */
        void hideProgressDialog();

        /**
         * 删除成功
         *
         * @param message  删除成功的信息
         * @param position 列表中的位置
         */
        void deleteSuccess(String message, int position);

        /**
         * 删除失败
         *
         * @param errorMessage 删除失败的信息
         */
        void deleteFailure(String errorMessage);


        /**
         * 保存邮件到本地
         *
         * @param list 邮件列表
         */
        void saveMail(List<Email> list);

        /**
         * 获取本地数据
         */
        void getLocalData();
    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         *
         * @param type          类型
         * @param startPosition 开始加载的位置
         */
        void loadData(int type, int startPosition);


        /**
         * 根据消息id删除邮件
         *
         * @param messageId 邮件id
         * @param position  列表中的位置
         */
        void deleteMailById(String messageId, int position);

        /**
         * 保存邮件到本地
         *
         * @param list 邮件列表
         */
        void saveMail(List<Email> list);

        /**
         * 获取本地数据
         */
        void getLocalData();
    }
}
