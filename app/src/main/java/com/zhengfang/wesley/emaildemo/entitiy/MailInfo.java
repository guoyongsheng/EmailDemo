package com.zhengfang.wesley.emaildemo.entitiy;

import java.util.List;
import java.util.Properties;

/**
 * Created by wesley on 2016/10/9.
 * 邮件信息实体类
 */
public class MailInfo {

    private String mailServerHost; //发送邮件的服务器的IP
    private String mailServerPort = "25"; //发送邮件的服务器的端口号
    private String userName; //登陆邮件发送服务器的用户名
    private String password; //登陆邮件发送服务器的密码
    private boolean validate = false; //是否需要身份验证
    private String fromAddress; //邮件发送者的地址
    private String subject; //邮件主题
    private String content; //邮件的文本内容
    private List<Attachment> attachmentInfos; //邮件附件的路径
    private String[] receivers; // 邮件的接收者，可以有多个

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }


    public List<Attachment> getAttachmentInfos() {
        return attachmentInfos;
    }

    public void setAttachmentInfos(List<Attachment> attachmentInfos) {
        this.attachmentInfos = attachmentInfos;
    }

    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
       // p.setProperty("mail.debug", "true");
       /* MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.socketFactory", sf);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }*/
        return p;
    }
}
