package com.zhengfang.wesley.emaildemo.modules.mailreplay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.Constant.Constant;
import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseApplication;
import com.zhengfang.wesley.emaildemo.base.BaseFragment;
import com.zhengfang.wesley.emaildemo.entitiy.Attachment;
import com.zhengfang.wesley.emaildemo.entitiy.Email;

/**
 * Created by wesley on 2016/10/11.
 * 回复界面的fragment
 */
public class MailReplayFragment extends BaseFragment<MailReplayPresenter> implements MailReplayContract.View, OnItemClickListener{

    private Email email; //邮件对象
    private TextView tv_sender; //发送者
    private EditText et_receivers; //接收者
    private EditText et_subject; //主题
    private EditText et_content; //内容
    private ProgressDialog dialog;
    private RecyclerView rv_recycler;
    private MailReplayAdapter adapter;

    public static MailReplayFragment newInstance(Email email) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("email", email);
        MailReplayFragment fragment = new MailReplayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initVariables() {
        handleBundle();

        adapter = new MailReplayAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    private void handleBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            email = (Email) bundle.getSerializable("email");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mail_replay;
    }

    @Override
    public void initViews(View view) {
        if (view == null) {
            return;
        }

        tv_sender = (TextView) view.findViewById(R.id.mail_sender);
        et_receivers = (EditText) view.findViewById(R.id.mail_receivers);
        et_subject = (EditText) view.findViewById(R.id.mail_subject);
        et_content = (EditText) view.findViewById(R.id.mail_content);
        Button btn_add_attachment = (Button) view.findViewById(R.id.mail_attachment);
        rv_recycler = (RecyclerView) view.findViewById(R.id.mail_recycler);
        final Toolbar toolbar = (Toolbar) ((MailReplayActivity) context).findViewById(R.id.head_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int key = item.getItemId();
                switch (key) {
                case R.id.menu_mail_replay_send:
                    sendMail();
                    break;

                default:
                    break;
                }
                return true;
            }
        });
        String userName = BaseApplication.mailInfo.getUserName();
        tv_sender.setText(userName);

        setUpRecyclerView();

        btn_add_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == null) {
                    return;
                }
                addAttachment();
            }
        });
    }

    @Override
    public String getReceivers() {
        return et_receivers.getText().toString();
    }

    @Override
    public String getSubject() {
        return et_subject.getText().toString();
    }

    @Override
    public String getContent() {
        return et_content.getText().toString();
    }

    @Override
    public void sendMail() {

        BaseApplication.mailInfo.setAttachmentInfos(adapter.getDataSource());
        BaseApplication.mailInfo.setFromAddress(tv_sender.getText().toString());
        BaseApplication.mailInfo.setSubject(getSubject());
        BaseApplication.mailInfo.setContent(getContent());
        BaseApplication.mailInfo.setReceivers(new String[]{getReceivers()});
        presenter.sendMail(BaseApplication.mailInfo, BaseApplication.session);
    }

    @Override
    public void showProgressDialog() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(getResources().getString(R.string.send_mail));
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void sendSuccess(String message) {
        Snackbar.make(tv_sender, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void sendFailure(String errorMessage) {
        Snackbar.make(tv_sender, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    //添加附件
    private void addAttachment() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/");
        startActivityForResult(intent, Constant.REQUEST_CODE_ADD_ATTACHMENT);
    }

    //初始化recyclerview
    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_recycler.setHasFixedSize(true);
        rv_recycler.setLayoutManager(layoutManager);
        rv_recycler.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * 添加附件
         */
        if (requestCode == Constant.REQUEST_CODE_ADD_ATTACHMENT && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
            }
            if (uri == null) {
                return;
            }
            rv_recycler.setVisibility(View.VISIBLE);
            String path = uri.getPath();
            Attachment affInfos = Attachment.GetFileInfo(path);
            adapter.addItem(affInfos);
        }
    }

    @Override
    public void onItemClick(int position) {
        adapter.removeItem(position);
    }
}
