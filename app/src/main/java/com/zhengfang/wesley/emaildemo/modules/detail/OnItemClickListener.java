package com.zhengfang.wesley.emaildemo.modules.detail;

import com.zhengfang.wesley.emaildemo.entitiy.Email;

/**
 * Created by wesley on 2016/10/10.
 * Recyclerview的item点击事件
 */
interface OnItemClickListener {
    void onItemClick(Email email, int position);

    void showDialogDelete(String messageId, int position);
}
