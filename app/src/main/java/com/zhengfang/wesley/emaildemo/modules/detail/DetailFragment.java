package com.zhengfang.wesley.emaildemo.modules.detail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseFragment;
import com.zhengfang.wesley.emaildemo.entitiy.Email;
import com.zhengfang.wesley.emaildemo.modules.mailcontent.MailContentActivity;

import java.util.List;

/**
 * Created by wesley on 2016/10/9.
 * 详情界面的fragment
 */
public class DetailFragment extends BaseFragment<DetailPresenter> implements DetailContract.View, OnItemClickListener {

    private int type;
    private DetailAdapter adapter;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private RecyclerView recyclerView;

    public static DetailFragment newInstance(int type) {

        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void initVariables() {
        Bundle bundle = getArguments();
        type = bundle.getInt("type");

        adapter = new DetailAdapter(context);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        loadData(type);
    }

    @Override
    public void loadData(int type) {
        presenter.loadData(type);
    }

    @Override
    public void showData(List<Email> list) {
        adapter.addAll(list);
    }

    @Override
    public void showProgressDialog() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void deleteSuccess(String message, int position) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
        adapter.removeItem(position);
    }

    @Override
    public void deleteFailure(String errorMessage) {
        Snackbar.make(recyclerView, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Email email, int position) {
        Intent intent = new Intent(context, MailContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("email", email);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //显示删除对话框
    @Override
    public void showDialogDelete(final String messageId, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.sure_delete);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteMailById(messageId, position);
                alertDialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }
}
