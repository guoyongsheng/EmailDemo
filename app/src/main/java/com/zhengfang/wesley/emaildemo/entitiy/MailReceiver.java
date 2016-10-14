package com.zhengfang.wesley.emaildemo.entitiy;

import com.zhengfang.wesley.emaildemo.utils.TranCharsetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by wesley on 2016/10/10.
 * 邮件接收者实体类
 */
public class MailReceiver {
    private MimeMessage mimeMessage = null;
    private String charset;
    private String dataFormat = "yyyy-MM-dd hh:mm:ss";
    private StringBuffer mailContent = new StringBuffer();// 邮件内容
    private boolean html;
    private boolean flag = true;
    private ArrayList<Attachment> attachments = new ArrayList<>();
    private ArrayList<String> attachmentsInputStreams = new ArrayList<>();

    public MailReceiver(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
        try {
            charset = parseCharset(mimeMessage.getContentType());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析字符集编码
     *
     * @param contentType 编码方式
     * @return 编码方式
     */
    private String parseCharset(String contentType) {
        if (contentType == null || !contentType.contains("charset")) {
            return null;
        }
        if (contentType.contains("gbk")) {
            return "GBK";
        } else if (contentType.contains("GB2312") || contentType.contains("gb18030")) {
            return "gb2312";
        } else {
            String sub = contentType.substring(contentType.indexOf("charset") + 8).replace("\"", "");
            if (sub.contains(";")) {
                return sub.substring(0, sub.indexOf(";"));
            } else {
                return sub;
            }
        }
    }

    /**
     * 取得「message-ID」
     *
     * @throws MessagingException
     */
    public String getMessageID() throws MessagingException {
        return mimeMessage.getMessageID();
    }


    /**
     * 获得送信人的姓名和邮件地址
     *
     * @throws Exception
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String addr = address[0].getAddress();
        String name = address[0].getPersonal();
        if (addr == null) {
            addr = "";
        }
        if (name == null) {
            name = "";
        } else if (charset == null) {
            name = TranCharsetUtil.TranEncodeTOGB(name);
        }
        return name + "<" + addr + ">";
    }


    /**
     * 根据类型，获取邮件地址 "TO"--收件人地址 "CC"--抄送人地址 "BCC"--密送人地址
     *
     * @throws Exception
     */
    public String getMailAddress(String type) throws Exception {
        String mailAddr = "";
        String addType = type.toUpperCase(Locale.CHINA);
        InternetAddress[] address;
        switch (addType) {
        case "TO":
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
            break;
        case "CC":
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
            break;
        case "BCC":
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
            break;
        default:
            System.out.println("error type!");
            throw new Exception("Error emailaddr type!");
        }
        if (address != null) {
            for (InternetAddress addres : address) {
                String mailaddress = addres.getAddress();
                if (mailaddress != null) {
                    mailaddress = MimeUtility.decodeText(mailaddress);
                } else {
                    mailaddress = "";
                }
                String name = addres.getPersonal();
                if (name != null) {
                    name = MimeUtility.decodeText(name);
                } else {
                    name = "";
                }
                mailAddr = name + "<" + mailaddress + ">";
            }
        }
        return mailAddr;
    }


    /**
     * 取得邮件标题
     *
     * @return String
     */
    public String getSubject() {
        String subject = "";
        try {
            subject = mimeMessage.getSubject();
            if (subject.contains("=?gb18030?")) {
                subject = subject.replace("gb18030", "gb2312");
            }
            subject = MimeUtility.decodeText(subject);
            if (charset == null) {
                subject = TranCharsetUtil.TranEncodeTOGB(subject);
            }
        } catch (Exception ignored) {
        }
        return subject;
    }


    /**
     * 取得邮件日期
     *
     * @throws MessagingException
     */
    public String getSentData() throws MessagingException {
        Date sentdata = mimeMessage.getSentDate();
        if (sentdata != null) {
            SimpleDateFormat format = new SimpleDateFormat(dataFormat, Locale.getDefault());
            return format.format(sentdata);
        } else {
            return "未知";
        }
    }


    /**
     * 取得邮件内容
     *
     * @throws Exception
     */
    public String getMailContent() throws Exception {
        compileMailContent(mimeMessage);
        String content = mailContent.toString();
        if (content.contains("<html>")) {
            html = true;
        }
        mailContent.setLength(0);
        return content;
    }


    /**
     * 解析邮件内容
     *
     * @param part part
     * @throws Exception
     */
    private void compileMailContent(Part part) throws Exception {
        // mailContent = new StringBuffer();
        String contentType = part.getContentType();
        boolean connName = false;
        if (contentType != null && contentType.contains("name")) {
            connName = true;
        }
        String charset = parseCharset(contentType);
        if (part.isMimeType("text/plain") && !connName && flag) {
            String content = parseInputStream((InputStream) part.getContent(), charset);
            mailContent.append(content);
            flag = false;
        } else if (part.isMimeType("text/html") && !connName && flag) {
            html = true;
            Object object = part.getContent();
            String content = parseInputStream((InputStream) object, charset);
            mailContent.append(content);
        } else if (part.isMimeType("multipart/*") || part.isMimeType("message/rfc822")) {
            if (part.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) part.getContent();
                int counts = multipart.getCount();
                for (int i = 0; i < counts; i++) {
                    compileMailContent(multipart.getBodyPart(i));
                }
            } else {
                Multipart multipart = new MimeMultipart(new ByteArrayDataSource(part.getInputStream(), "multipart/*"));
                int counts = multipart.getCount();
                for (int i = 0; i < counts; i++) {
                    compileMailContent(multipart.getBodyPart(i));
                }
                //compileMailContent(multipart.getBodyPart(counts - 1));
            }
        } else if (part.getDisposition() != null && part.getDisposition().equals(Part.ATTACHMENT)) {
            // 获取附件
            String filename = part.getFileName();
            if (filename != null) {
                if (filename.contains("=?gb18030?")) {
                    filename = filename.replace("gb18030", "gb2312");
                }
                filename = MimeUtility.decodeText(filename);
                Attachment attachment = new Attachment();
                attachment.setFileName(filename);
                attachments.add(attachment);
                //attachmentsInputStreams.add(IOUtil.getStreamString(part.getInputStream()));
            }
            // Log.e("content", "附件：" + filename);
        }
    }


    /**
     * 解析流格式
     *
     * @param is 流
     * @throws IOException
     * @throws MessagingException
     */
    private String parseInputStream(InputStream is, String charset) throws IOException, MessagingException {
        StringBuilder str = new StringBuilder();
        byte[] readByte = new byte[1024];
        int count;
        try {
            while ((count = is.read(readByte)) != -1) {
                if (charset == null) {
                    str.append(new String(readByte, 0, count, "utf-8"));
                } else {
                    str.append(new String(readByte, 0, count, charset));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     * 是否有回执
     *
     * @throws MessagingException
     */
    public boolean getReplySign() throws MessagingException {
        boolean replySign = false;
        String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
        if (needreply != null) {
            replySign = true;
        }
        return replySign;
    }

    public boolean isHtml() {
        return html;
    }

    /**
     * 是否新邮件
     *
     * @throws MessagingException
     */
    public boolean isNew() throws MessagingException {
        boolean isnew = false;
        Flags flags = mimeMessage.getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for (Flags.Flag aFlag : flag) {
            if (aFlag == Flags.Flag.SEEN) {
                isnew = true;
                break;
            }
        }
        return isnew;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public ArrayList<String> getAttachmentsInputStreams() {
        return attachmentsInputStreams;
    }

    public String getCharset() {
        return charset;
    }
}
