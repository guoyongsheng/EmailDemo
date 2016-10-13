package com.zhengfang.wesley.emaildemo.utils;

import com.orhanobut.logger.Logger;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.zhengfang.wesley.emaildemo.base.BaseApplication;
import com.zhengfang.wesley.emaildemo.entitiy.Attachment;
import com.zhengfang.wesley.emaildemo.entitiy.Email;
import com.zhengfang.wesley.emaildemo.entitiy.MailInfo;
import com.zhengfang.wesley.emaildemo.entitiy.MailReceiver;
import com.zhengfang.wesley.emaildemo.entitiy.MyAuthenticator;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * Created by wesley on 2016/10/10.
 * 邮件工具类
 */
public class MailHelper {
    private static volatile MailHelper instance;

    private MailHelper() {

    }

    //单例模式
    public static MailHelper getInstance() {
        if (instance == null) {
            synchronized (MailHelper.class) {
                if (instance == null) {
                    instance = new MailHelper();
                }
            }
        }
        return instance;
    }


    /**
     * 获取所有的邮件
     *
     * @return 所有邮件
     */
    public List<MailReceiver> getAllMail() {
        List<MailReceiver> list = new ArrayList<>();

        try {
            Store store = BaseApplication.session.getStore("pop3");
            String temp = BaseApplication.mailInfo.getMailServerHost();
            String host = temp.replace("smtp", "pop");
            store.connect(host, 110, BaseApplication.mailInfo.getUserName(), BaseApplication.mailInfo.getPassword());
            // 打开文件夹
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            // 总的邮件数
            int mailCount = folder.getMessageCount();
            if (mailCount == 0) {
                folder.close(true);
                store.close();
                Logger.d(" 邮件总数 mailCount = " + mailCount);
                return null;
            } else {
                // 取得所有的邮件
                Message[] messages = folder.getMessages();
                for (Message message : messages) {
                    // 自定义的邮件对象

                    MailReceiver reciveMail = new MailReceiver((MimeMessage) message);
                    list.add(reciveMail);// 添加到邮件列表中
                }
                return list;
            }
        } catch (MessagingException e) {
            Logger.d(e.getMessage());
        }

        return list;
    }

    /**
     * @param listMailReceiver 邮件对象集合
     * @return 邮件对象集合
     */
    public List<Email> getListMail(List<MailReceiver> listMailReceiver) {
        List<Email> list = new ArrayList<>();
        List<ArrayList<InputStream>> attachmentsInputStreamsList = new ArrayList<>();

        if (listMailReceiver == null) {
            return list;
        }

        for (MailReceiver mailReceiver : listMailReceiver) {
            Email email = new Email();
            try {
                email.setMessageID(mailReceiver.getMessageID());
                email.setFrom(mailReceiver.getFrom());
                email.setTo(mailReceiver.getMailAddress("TO"));
                email.setCc(mailReceiver.getMailAddress("CC"));
                email.setBcc(mailReceiver.getMailAddress("BCC"));
                email.setSubject(mailReceiver.getSubject());
                email.setSentdata(mailReceiver.getSentData());
                email.setContent(mailReceiver.getMailContent());
                email.setReplysign(mailReceiver.getReplySign());
                email.setHtml(mailReceiver.isHtml());
                email.setNews(mailReceiver.isNew());
                email.setAttachments(mailReceiver.getAttachments());
                email.setCharset(mailReceiver.getCharset());
                attachmentsInputStreamsList.add(0,
                        mailReceiver.getAttachmentsInputStreams());
                list.add(0, email);
            } catch (Exception e) {
                Logger.d(e.getMessage());
            }
        }
        BaseApplication.attachmentsInputStreams = attachmentsInputStreamsList;

        return list;
    }


    /**
     * @param mailInfo        邮件信息
     * @param sendMailSession session
     * @return 是否发送成功
     */
    public boolean sendTextMail(MailInfo mailInfo, Session sendMailSession) {
        // 判断是否需要身份认证
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address address = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(address);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address[] tos = null;
            String[] receivers = mailInfo.getReceivers();
            if (receivers != null) {
                // 为每个邮件接收者创建一个地址
                tos = new InternetAddress[receivers.length];
                for (int i = 0; i < receivers.length; i++) {
                    tos[i] = new InternetAddress(receivers[i]);
                }
            } else {
                return false;
            }
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipients(Message.RecipientType.TO, tos);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();

            Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放多个BodyPart对象
            // 设置信件文本内容
            BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
            mdp.setContent(mailContent, "text/html;charset=utf-8");// 给BodyPart对象设置内容和格式/编码方式
            mm.addBodyPart(mdp);// 将含有信件内容的BodyPart加入到MimeMultipart对象中

            Attachment affInfos;
            FileDataSource fds1;
            List<Attachment> list = mailInfo.getAttachmentInfos();
            for (int i = 0; i < list.size(); i++) {
                affInfos = list.get(i);
                fds1 = new FileDataSource(affInfos.getFilePath());
                mdp = new MimeBodyPart();
                mdp.setDataHandler(new DataHandler(fds1));
                try {
                    mdp.setFileName(MimeUtility.encodeText(fds1.getName()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                mm.addBodyPart(mdp);
            }
            mailMessage.setContent(mm);
            mailMessage.saveChanges();

            // 设置邮件支持多种格式
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);

            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            Logger.d(ex.getMessage());
        }
        return false;
    }


    /**
     * 根据消息id删除邮件
     *
     * @param messageId 消息id
     * @return 是否删除成功
     */
    public boolean deleteMessage(String messageId) {
        try {

            String temp = BaseApplication.mailInfo.getMailServerHost();
            String host = temp.replace("smtp", "imap");

            Properties prop = System.getProperties();
            prop.put("mail.imap.host", host);
            prop.put("mail.imap.auth.plain.disable", "true");
            Session mailsession = Session.getInstance(prop, null);
            IMAPStore store = (IMAPStore) mailsession.getStore("imap");

            store.connect(host, BaseApplication.mailInfo.getUserName(), BaseApplication.mailInfo.getPassword());
            // 打开文件夹
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            // 总的邮件数
            int mailCount = folder.getMessageCount();
            if (mailCount == 0) {
                folder.close(true);
                store.close();
                return false;
            } else {
                // 取得所有的邮件
                Message[] messages = folder.getMessages();

                for (Message message : messages) {
                    String id = ((MimeMessage) message).getMessageID();
                    if (id != null && id.equals(messageId)) {
                        message.setFlag(Flags.Flag.DELETED, true);
                        folder.close(true);
                        store.close();
                        return true;
                    }
                }
                Logger.d(" 删除失败 邮件id = " + messageId);
                return false;
            }
        } catch (MessagingException e) {
            Logger.d(e.getMessage());
        }
        return false;
    }


    /**
     * 登陆 smtp负责登陆和发送邮件
     *
     * @param mail     邮箱账号
     * @param password 密码
     * @return 登陆是否成功
     */
    public Boolean login(String mail, String password) {

        MailInfo mailInfo = new MailInfo();
        String host = "smtp." + mail.substring(mail.lastIndexOf("@") + 1);
        mailInfo.setMailServerHost(host);
        if (BaseApplication.login_from == 0) {
            mailInfo.setMailServerPort("587"); //qq邮箱的端口好是587
        } else {
            mailInfo.setMailServerPort("25"); //qq邮箱的端口好是587
        }
        mailInfo.setUserName(mail);
        mailInfo.setPassword(password);
        mailInfo.setValidate(true);
        //判断是否要登入验证
        MyAuthenticator authenticator = null;
        if (mailInfo.isValidate()) {
            //创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getInstance(mailInfo.getProperties(), authenticator);
        try {
            Transport transport = sendMailSession.getTransport("smtp");
            transport.connect(mailInfo.getMailServerHost(), mailInfo.getUserName(), mailInfo.getPassword());
        } catch (MessagingException e) {
            Logger.d(e.getMessage());
            return false;
        }
        BaseApplication.mailInfo = mailInfo;
        BaseApplication.session = sendMailSession;
        return true;
    }


    /**
     * 获取所有的邮件
     *
     * @return 所有邮件
     */
    public List<MailReceiver> getAllMailBySSL() {
        List<MailReceiver> list = new ArrayList<>();

        try {
            String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.put("mail.pop3.host", "pop.qq.com");
            props.put("mail.pop3.auth.plain.disable", "true");
            props.put("mail.pop3.ssl.enable", "true");
            String temp = BaseApplication.mailInfo.getMailServerHost();
            String host = temp.replace("smtp", "pop");
            Session mailsession = Session.getInstance(props, null);
            Store store = mailsession.getStore("pop3");

            store.connect(host, 995, BaseApplication.mailInfo.getUserName(), BaseApplication.mailInfo.getPassword());
            // 打开文件夹
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            // 总的邮件数
            int mailCount = folder.getMessageCount();
            if (mailCount == 0) {
                folder.close(true);
                store.close();
                Logger.d(" 邮件总数 mailCount = " + mailCount);
                return null;
            } else {
                // 取得所有的邮件
                Message[] messages = folder.getMessages();
                for (Message message : messages) {
                    // 自定义的邮件对象

                    MailReceiver reciveMail = new MailReceiver((MimeMessage) message);
                    list.add(reciveMail);// 添加到邮件列表中
                }
                return list;
            }
        } catch (MessagingException e) {
            Logger.d(e.getMessage());
        }

        return list;
    }


    /**
     * 根据邮件id来删除邮件
     *
     * @param messageId 邮件id
     * @return 是否删除成功
     */
    public boolean deleteMailBySSL(String messageId) {

        try {
            String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.put("mail.pop3.host", "pop.qq.com");
            props.put("mail.pop3.auth.plain.disable", "true");
            props.put("mail.pop3.ssl.enable", "true");
            String temp = BaseApplication.mailInfo.getMailServerHost();
            String host = temp.replace("smtp", "pop");
            Session mailsession = Session.getInstance(props, null);
            Store store = mailsession.getStore("pop3");

            store.connect(host, 995, BaseApplication.mailInfo.getUserName(), BaseApplication.mailInfo.getPassword());
            // 打开文件夹
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            // 总的邮件数
            int mailCount = folder.getMessageCount();
            if (mailCount == 0) {
                folder.close(true);
                store.close();
                Logger.d(" 邮件总数 mailCount = " + mailCount);
                return false;
            } else {
                // 取得所有的邮件
                Message[] messages = folder.getMessages();
                for (Message message : messages) {
                    String id = ((MimeMessage) message).getMessageID();
                    if (id != null && id.equals(messageId)) {
                        message.setFlag(Flags.Flag.DELETED, true);
                        folder.close(true);
                        store.close();
                        return true;
                    }
                }
                Logger.d(" 删除失败 邮件id = " + messageId);
                return false;
            }
        } catch (MessagingException e) {
            Logger.d(e.getMessage());
        }
        return false;
    }

}
