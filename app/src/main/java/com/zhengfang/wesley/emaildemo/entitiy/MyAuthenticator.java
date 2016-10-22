package com.zhengfang.wesley.emaildemo.entitiy;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by wesley on 2016/10/9.
 */
public class MyAuthenticator extends Authenticator {
    private String userName=null;
    private String password=null;

    public MyAuthenticator(){
    }
    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    /**
     * 登入校验
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
