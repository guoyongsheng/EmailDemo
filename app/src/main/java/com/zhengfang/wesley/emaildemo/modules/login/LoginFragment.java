package com.zhengfang.wesley.emaildemo.modules.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhengfang.wesley.emaildemo.R;
import com.zhengfang.wesley.emaildemo.base.BaseFragment;
import com.zhengfang.wesley.emaildemo.modules.homepage.HomePageActivity;

/**
 * Created by wesley on 2016/10/9.
 * 登陆界面的fragment
 */
public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View, View.OnClickListener {


    private EditText et_Mail; //邮箱账号
    private EditText et_password; //邮箱密码
    private Button btn_login; //登陆
    private ProgressDialog dialog;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public void initVariables() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initViews(View view) {
        et_Mail = (EditText) view.findViewById(R.id.login_user);
        et_password = (EditText) view.findViewById(R.id.login_pass);
        btn_login = (Button) view.findViewById(R.id.login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public String getMail() {
        return et_Mail.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void login(String mail, String password) {
        presenter.login(mail, password);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(btn_login, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(context, HomePageActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailure(String loginError) {
        Snackbar.make(btn_login, loginError, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();
    }

    @Override
    public void dissmissProgressDialog() {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        int key = view.getId();
        switch (key) {
        /**
         * 登陆
         */
        case R.id.login:
            login(getMail(), getPassword());
            break;


        default:
            break;
        }
    }
}
