package com.normalworks.common.cloud.aws.client.impl;

import com.normalworks.common.cloud.api.EmailCloudClient;
import com.normalworks.common.cloud.aws.config.AwsCommonConfig;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SendRawEmailResponse;
import software.amazon.awssdk.services.ses.model.SesException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Properties;

/**
 * EmailCloudClientImpl
 * 邮件服务实现类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/1 10:31 PM
 */
@Service("awsEmailCloudClient")
public class EmailCloudClientImpl implements EmailCloudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCloudClientImpl.class);

    /**
     * 发邮件使用的配置项集合，默认命名为EMAIL_OPEN
     */
    private static final String CONFIGURATION_SET_NAME = "EMAIL_OPEN";

    @Resource
    private AwsCommonConfig awsCommonConfig;

    private SesClient sesClient;

    @Override
    public String sendEmail(String fromAddr, String fromAlias, String toAddr, String replyToAddr, String subject, String htmlBody, String textBody) {

        return innerSendEmail(fromAddr, fromAlias, List.of(toAddr), replyToAddr, null, null, subject, htmlBody, textBody);
    }


    @Override
    public String sendEmail(String fromAddr, String fromAlias, String toAddr, String replyToAddr, List<String> ccAddrs, List<String> bccAddr, String subject, String htmlBody, String textBody) {
        return innerSendEmail(fromAddr, fromAlias, List.of(toAddr), replyToAddr, ccAddrs, bccAddr, subject, htmlBody, textBody);
    }

    private String innerSendEmail(String fromAddr, String fromAlias, List<String> toAddrs, String replyToAddr, List<String> ccAddrs, List<String> bccAddrs, String subject, String htmlBody, String textBody) {
        try {
            LOGGER.info("发送邮件 fromAddr = " + fromAddr + " , fromAlias = " + fromAlias + " , toAddrs = " + toAddrs + " , replyToAddr = " + replyToAddr + " , ccAddrs = " + ccAddrs + " , bccAddrs = " + bccAddrs + " , subject = " + subject + " , htmlBody = " + htmlBody + " , textBody = " + textBody);

            Session session = Session.getDefaultInstance(new Properties());
            MimeMessage message = new MimeMessage(session);

            // Add subject, from and to lines.
            message.setSubject(subject, "UTF-8");

            InternetAddress fromAddress;
            if (StringUtils.isNotBlank(fromAlias)) {
                fromAddress = new InternetAddress(fromAddr, fromAlias);
            } else {
                fromAddress = new InternetAddress(fromAddr);
            }
            message.setFrom(fromAddress);

            if (StringUtils.isNotBlank(replyToAddr)) {
                message.setReplyTo(InternetAddress.parse(replyToAddr));
            }

            // 设置收件人
            setRecipients(message, Message.RecipientType.TO, toAddrs);

            // 设置抄送人
            if (!CollectionUtils.isNotEmpty(ccAddrs)) {
                setRecipients(message, Message.RecipientType.CC, ccAddrs);
            }

            // 设置密送人
            if (!CollectionUtils.isNotEmpty(bccAddrs)) {
                setRecipients(message, Message.RecipientType.BCC, bccAddrs);
            }


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

            // ready to send
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder().data(data).build();

            AwsRequestOverrideConfiguration myConf = AwsRequestOverrideConfiguration.builder().credentialsProvider(ProfileCredentialsProvider.create()).build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder().configurationSetName(CONFIGURATION_SET_NAME).rawMessage(rawMessage).overrideConfiguration(myConf).build();

            SendRawEmailResponse sendRawEmailResponse = sesClient.sendRawEmail(rawEmailRequest);
            LOGGER.info("发送邮件成功，messageId = " + sendRawEmailResponse.messageId());

            return sendRawEmailResponse.messageId();

        } catch (SesException sesException) {

            LOGGER.error("调用aws ses服务异常", sesException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws ses服务异常");

        } catch (Exception exception) {

            LOGGER.error("调用aws ses服务系统异常", exception);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws ses服务系统异常");
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
        Region region = Region.of(awsCommonConfig.getRegion());
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsCommonConfig.getAccessKeyId(), awsCommonConfig.getAccessKeySecret());
        sesClient = SesClient.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();
        LOGGER.info("AWS发送邮件客户端已初始化完成");
    }
}
