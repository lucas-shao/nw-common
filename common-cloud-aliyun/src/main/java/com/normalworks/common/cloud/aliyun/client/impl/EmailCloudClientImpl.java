package com.normalworks.common.cloud.aliyun.client.impl;

import com.google.gson.GsonBuilder;
import com.normalworks.common.cloud.aliyun.config.AliyunEmailConfig;
import com.normalworks.common.cloud.api.EmailCloudClient;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

/**
 * EmailCloudClientImpl
 * 阿里云邮件云服务实现类
 * <p>
 * sample demo：https://help.aliyun.com/document_detail/29450.html
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/1/10 4:19 PM
 */
@Service("aliyunEmailCloudClient")
public class EmailCloudClientImpl implements EmailCloudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCloudClientImpl.class);

    private Session mailSession;

    @Resource
    private AliyunEmailConfig aliyunEmailConfig;

    @Override
    public String sendEmail(String fromAddr, String fromAlias, String toAddr, String replyToAddr, String subject, String htmlBody, String textBody) {
        return innerSendEmail(fromAddr, fromAlias, List.of(toAddr), replyToAddr, null, null, subject, htmlBody, textBody);
    }


    @Override
    public String sendEmail(String fromAddr, String fromAlias, String toAddr, String replyToAddr, List<String> ccAddrs, List<String> bccAddrs, String subject, String htmlBody, String textBody) {
        return innerSendEmail(fromAddr, fromAlias, List.of(toAddr), replyToAddr, ccAddrs, bccAddrs, subject, htmlBody, textBody);
    }

    private String innerSendEmail(String fromAddr, String fromAlias, List<String> toAddrs, String replyToAddr, List<String> ccAddrs, List<String> bccAddrs, String subject, String htmlBody, String textBody) {
        LOGGER.info("发送邮件 fromAddr = " + fromAddr + " , fromAlias = " + fromAlias + " , toAddr = " + toAddrs + " , replyToAddr = " + replyToAddr + ", ccAddrs = " + ccAddrs + " , bccAddrs = " + bccAddrs + " , subject = " + subject + " , htmlBody = " + htmlBody + " , textBody = " + textBody);

        //创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);

        try {
            // 设置发件人邮件地址和名称。填写控制台配置的发信地址。和上面的mail.user保持一致。名称用户可以自定义填写。
            InternetAddress from;
            if (StringUtils.isNotBlank(fromAlias)) {
                from = new InternetAddress(fromAddr, fromAlias);//from 参数,可实现代发，注意：代发容易被收信方拒信或进入垃圾箱。
            } else {
                from = new InternetAddress(fromAddr);
            }
            message.setFrom(from);

            // 设置收件人邮件地址
            setRecipients(message, Message.RecipientType.TO, toAddrs);

            // 设置抄送人邮件地址
            if (!CollectionUtils.isEmpty(ccAddrs)) {
                setRecipients(message, Message.RecipientType.CC, ccAddrs);
            }

            // 设置密送人邮件地址
            if (!CollectionUtils.isEmpty(bccAddrs)) {
                setRecipients(message, Message.RecipientType.BCC, bccAddrs);
            }

            // 可选。设置回信地址
            if (StringUtils.isNotBlank(replyToAddr)) {
                Address[] replyToAddressList = new Address[1];
                replyToAddressList[0] = new InternetAddress(replyToAddr);
                message.setReplyTo(replyToAddressList);
            }

            // 设置邮件发送时间
            message.setSentDate(new Date());

            // 设置邮件标题
            message.setSubject(subject);

            // 设置邮件内容
            // Create a multipart/alternative child container.
            MimeMultipart msgBody = new MimeMultipart("alternative");

            // Create a wrapper for the HTML and text parts.
            MimeBodyPart wrap = new MimeBodyPart();

            // Define the HTML part.
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html; charset=UTF-8");

            // Add the text and HTML parts to the child container.
            msgBody.addBodyPart(htmlPart);

            if (StringUtils.isNotBlank(textBody)) {
                // Define the text part.
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(textBody, "text/plain; charset=UTF-8");

                msgBody.addBodyPart(textPart);
            }

            // Add the child container to the wrapper object.
            wrap.setContent(msgBody);

            // Create a multipart/mixed parent container.
            MimeMultipart msg = new MimeMultipart("mixed");

            // Add the multipart/alternative part to the message.
            msg.addBodyPart(wrap);

            // Add the parent container to the message.
            message.setContent(msg);

            // 设置邮件跟踪
            String tagName = "EmailTag";
            HashMap<String, String> trace = new HashMap<>();
            trace.put("OpenTrace", "1");
            trace.put("LinkTrace", "0");
            trace.put("TagName", tagName);
            String jsonTrace = new GsonBuilder().setPrettyPrinting().create().toJson(trace);
            String base64Trace = new String(Base64.getEncoder().encode(jsonTrace.getBytes()));
            message.addHeader("X-AliDM-Trace", base64Trace);

            // 发送邮件
            Transport.send(message);

            LOGGER.info("发送阿里云邮件成功，messageId = " + message.getMessageID());

            return message.getMessageID();

        } catch (MessagingException messagingException) {

            LOGGER.error("调用阿里云邮件SMTP服务异常", messagingException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用阿里云邮件SMTP服务异常");

        } catch (Exception exception) {

            LOGGER.error("调用阿里云邮件SMTP服务系统异常", exception);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用阿里云邮件SMTP服务系统异常");
        }
    }

    /**
     * 设置收件人邮箱
     */
    private static void setRecipients(MimeMessage message, Message.RecipientType recipientType, List<String> toAddr) throws MessagingException {
        InternetAddress[] toAddresses = new InternetAddress[toAddr.size()];
        for (int i = 0; i < toAddr.size(); i++) {
            toAddresses[i] = new InternetAddress(toAddr.get(i));
        }
        message.addRecipients(recipientType, toAddresses);
    }

    @PostConstruct
    private void init() {

        // 配置发送邮件的环境属性
        final Properties props = new Properties();

        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", aliyunEmailConfig.getSmtpHost());
        // 设置465端口以及加密方式
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");

        props.put("mail.smtp.from", aliyunEmailConfig.getSmtpFrom());    //mailfrom 参数
        props.put("mail.user", aliyunEmailConfig.getSmtpUser());// 发件人的账号(在控制台创建的发信地址)
        props.put("mail.password", aliyunEmailConfig.getSmtpPassword());// 发信地址的smtp密码(在控制台选择发信地址进行设置)

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        //使用环境属性和授权信息，创建邮件会话
        mailSession = Session.getInstance(props, authenticator);
        LOGGER.info("阿里云发送邮件客户端已初始化完成");
    }
}
