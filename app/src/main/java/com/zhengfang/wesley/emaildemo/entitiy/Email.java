package com.zhengfang.wesley.emaildemo.entitiy;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wesley on 2016/10/10.
 * 邮件实体类
 */
public class Email implements Serializable {

    private String messageID;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String sentdata;
    private String content;
    private boolean replysign;
    private boolean html;
    private boolean news;
    //private ArrayList<String> attachments;
    private String charset;
    private ArrayList<Attachment> attachments;
    private ArrayList<String> attachmentsInputStreams; //附件流

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSentdata() {
        return sentdata;
    }

    public void setSentdata(String sentdata) {
        this.sentdata = sentdata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReplysign() {
        return replysign;
    }

    public void setReplysign(boolean replysign) {
        this.replysign = replysign;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setAttachmentsInputStreams(ArrayList<String> attachmentsInputStreams) {
        this.attachmentsInputStreams = attachmentsInputStreams;
    }


    public ArrayList<String> getAttachmentsInputStreams(){
        return attachmentsInputStreams;
    }

}
