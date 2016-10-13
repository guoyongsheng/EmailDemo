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
         * @param type 类型
         */
        void loadData(int type);

        /**
         * 显示数据
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
         * @param message 删除成功的信息
         * @param position 列表中的位置
         */
        void deleteSuccess(String message, int position);

        /**
         * 删除失败
         * @param errorMessage 删除失败的信息
         */
        void deleteFailure(String errorMessage);
    }

    interface Presenter extends BasePresenter {

        /**
         * 加载数据
         *
         * @param type 类型
         */
        void loadData(int type);


        /**
         * 根据消息id删除邮件
         * @param messageId 邮件id
         * @param position 列表中的位置
         */
        void deleteMailById(String messageId, int position);
    }
}
