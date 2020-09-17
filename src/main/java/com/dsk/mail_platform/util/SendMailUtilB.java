package com.dsk.mail_platform.util;

import com.dsk.mail_platform.query.CommonData;
import com.dsk.mail_platform.query.CommonResult;
import com.dsk.mail_platform.query.DataContent;
import com.dsk.mail_platform.query.HistoryResult;
import com.sun.mail.util.MailSSLSocketFactory;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

public class SendMailUtilB {
    private static String sendUser = "2467755289@qq.com";
    private static String password = "ixtaabbrdtszecab";
    private static String receiveUser = "2901491598@qq.com";


    public  void sendMailText(CommonResult commonResult, HistoryResult historyResult1) throws GeneralSecurityException, MessagingException {

        //创建一个配置文件并保存
        Properties properties = new Properties();
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");

        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendUser,password);
            }
        });


        //获取连接对象
        Transport transport = session.getTransport();

        //连接服务器
        transport.connect("smtp.qq.com",sendUser,password);

        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(sendUser));

        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(receiveUser));

        //邮件标题
        mimeMessage.setSubject(DateUtil.getCurrentTime()+"笑话");

        //邮件内容

        String content = "<h3 style='color:blue'>笑话篇</h3>";
        List<DataContent> list = commonResult.getResult();
        for (int i=0;i<list.size();i++){
            content=content+"【第"+(i+1)+"个笑话】<br>";
            content+=("&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).getContent()+"<br>");
        }

        content+="<br><br><h3 style='color:blue'>历史篇</h3><br><h4>历史上的今天</h4>";
        List<CommonData> result = historyResult1.getResult();
        int size = 0;
        if (result.size()>8){
            size = 8;

        }else {
            size  = result.size();
        }
        for(int i=0;i<size;i++){

            content=content+"<br>";
            content+=("【"+result.get(i).getTitle()+"】"+"<br>");
            content+=("&nbsp;&nbsp;&nbsp;&nbsp;"+result.get(i).getDes()+"<br>");

        }
        mimeMessage.setContent(content,"text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();

    }
/*
    public static void sendMail() throws GeneralSecurityException, MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com"); // 设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

        // QQ邮箱设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //1、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //传入发件人的姓名和授权码
                return new PasswordAuthentication(sendUser,password);
            }
        });

        //2、通过session获取transport对象
        Transport transport = session.getTransport();

        //3、通过transport对象邮箱用户名和授权码连接邮箱服务器
        transport.connect("smtp.qq.com",sendUser,password);

        //4、创建邮件,传入session对象
        MimeMessage mimeMessage = complexEmail(session);

        //5、发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

        //6、关闭连接
        transport.close();


    }

    public static MimeMessage complexEmail(Session session) throws MessagingException {
        //消息的固定信息
        MimeMessage mimeMessage = new MimeMessage(session);

        //发件人
        mimeMessage.setFrom(new InternetAddress(sendUser));
        //收件人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(receiveUser));
        //邮件标题
        mimeMessage.setSubject("带图片和附件的邮件");

        //邮件内容
        //准备图片数据
        MimeBodyPart image = new MimeBodyPart();
        DataHandler handler = new DataHandler(new FileDataSource("D:\\1.png"));
        image.setDataHandler(handler);
        image.setContentID("1.png"); //设置图片id

        //准备文本
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这是一段文本<img src='cid:1.png'>","text/html;charset=utf-8");

        //附件
        MimeBodyPart appendix = new MimeBodyPart();
        appendix.setDataHandler(new DataHandler(new FileDataSource("D:\\1.txt")));
        appendix.setFileName("1.txt");

        //拼装邮件正文
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(image);
        mimeMultipart.addBodyPart(text);
        mimeMultipart.setSubType("related");//文本和图片内嵌成功

        //将拼装好的正文内容设置为主体
        MimeBodyPart contentText = new MimeBodyPart();
        contentText.setContent(mimeMultipart);

        //拼接附件
        MimeMultipart allFile = new MimeMultipart();
        allFile.addBodyPart(appendix);//附件
        allFile.addBodyPart(contentText);//正文
        allFile.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed


        //放到Message消息中
        mimeMessage.setContent(allFile);
        mimeMessage.saveChanges();//保存修改

        return mimeMessage;
    }

 */
}
