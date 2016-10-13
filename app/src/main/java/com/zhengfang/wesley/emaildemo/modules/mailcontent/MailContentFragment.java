package com.zhengfang.wesley.emaildemo.modules.mailcontent;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseApplication;
import com.zhengfang.wesley.emaildemo.base.BaseFragment;
import com.zhengfang.wesley.emaildemo.entitiy.Email;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2016/10/10.
 * 邮件内容fragment
 */
public class MailContentFragment extends BaseFragment<MailContentPresenter> implements MailContentContract.View, OnItemClickListener {

    private int position;
    private Email email;
    private TextView tv_from; //邮件来源
    private TextView tv_subject; //邮件主题
    private TextView tv_content; //邮件内容
    private WebView wv_content; //网页版邮件内容
    private RecyclerView rv_recycler;
    private MailContentAdapter adapter;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;

    public static MailContentFragment newInstance(Email email, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("email", email);
        bundle.putInt("position", position);
        MailContentFragment fragment = new MailContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initVariables() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            email = (Email) bundle.getSerializable("email");
            position = bundle.getInt("position");
            adapter = new MailContentAdapter(context);
            adapter.setOnItemClickListener(this);
            adapter.setDataSource(email.getAttachments());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mail_content;
    }

    @Override
    public void initViews(View view) {
        if (view == null) {
            return;
        }

        tv_from = (TextView) view.findViewById(R.id.mail_content_from);
        tv_subject = (TextView) view.findViewById(R.id.mail_content_subject);
        tv_content = (TextView) view.findViewById(R.id.mail_content);
        wv_content = (WebView) view.findViewById(R.id.mail_content_web_view);
        rv_recycler = (RecyclerView) view.findViewById(R.id.mail_recycler_receive);

        tv_from.setText(email.getFrom());
        tv_subject.setText(email.getSubject());

        if (email.isHtml()) {
            tv_content.setVisibility(View.GONE);
            wv_content.setVisibility(View.VISIBLE);
            wv_content.loadDataWithBaseURL(null, email.getContent(), "text/html", "utf-8", null);
            wv_content.getSettings().setBuiltInZoomControls(false);
            wv_content.setWebChromeClient(new WebChromeClient());
        } else {
            tv_content.setVisibility(View.VISIBLE);
            wv_content.setVisibility(View.GONE);
            tv_content.setText(email.getContent());
        }

        initAttachment();
    }

    //初始化附件
    private void initAttachment() {
        ArrayList<String> list = email.getAttachments();
        if (list != null && list.size() > 0) {
            setUpRecyclerView();
        }
    }

    //初始化recyclerview
    private void setUpRecyclerView() {
        rv_recycler.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_recycler.setHasFixedSize(true);
        rv_recycler.setLayoutManager(layoutManager);
        rv_recycler.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        showDialog(position);
    }

    //显示对话框
    private void showDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.down_attachment);
        builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<ArrayList<InputStream>> list = BaseApplication.attachmentsInputStreams;
                if (list != null && list.size() > position) {

                    ArrayList<InputStream> listInput= list.get(position);
                    if(listInput != null && listInput.size() > pos){
                        String path = Environment.getExternalStorageDirectory().toString() + "/temp/" + email.getAttachments().get(pos);
                        presenter.downLoadAttachment(listInput.get(pos), path);
                    }
                }
            }
        });

        builder.setNeutralButton(R.string.no_down, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void downLoadSuccess(String message) {
        Snackbar.make(tv_from, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downLoadFailure(String errorMessage) {
        Snackbar.make(tv_from, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
