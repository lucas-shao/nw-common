package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.utils.model.EmailBaseInfo;
import com.normalworks.common.utils.model.EmailFileInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * EmailMessageUtil
 * 邮件内容处理工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/8 3:13 PM
 */
public class EmailMessageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailMessageUtil.class);


    /**
     * 从邮件Message里面获取邮件基本信息
     *
     * @param message 邮件Message
     * @return 邮件基本信息
     */
    public static EmailBaseInfo fetchEmailBaseInfoFromMessage(Message message) {

        try {
            EmailBaseInfo emailBaseInfo = new EmailBaseInfo();
            emailBaseInfo.setMessageId(StringUtils.trim(((MimeMessage) message).getMessageID()));
            emailBaseInfo.setSubject(StringUtils.trim(message.getSubject()));
            emailBaseInfo.setFromAddress(StringUtils.trim(((InternetAddress) message.getFrom()[0]).getAddress()));
            emailBaseInfo.setSendDate(message.getSentDate());
            emailBaseInfo.setReceivedDate(message.getReceivedDate());

            LOGGER.info("fetchEmailBaseInfoFromMessage: " + emailBaseInfo);

            return emailBaseInfo;

        } catch (Exception e) {

            LOGGER.error("从邮件Message里面获取邮件基本信息失败", e);
            throw new AssertionException(CommonResultCode.FETCH_EMAIL_MESSAGE_FAILED, "从邮件Message里面获取邮件基本信息失败");
        }
    }

    /**
     * 从邮件Message里面获取正文中的纯文本信息
     *
     * @param message 邮件Message
     * @return 邮件正文中的纯文本信息
     */
    public static String fetchEmailContentTextFromMessage(Message message) {

        try {
            String result = "";
            if (message.isMimeType("text/plain")) {
                result = message.getContent().toString();
            } else if (message.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                result = fetchTextFromMimeMultipart(mimeMultipart);
            }
            return result.trim();

        } catch (Exception e) {

            LOGGER.error("从邮件Message里面获取正文纯文本信息失败", e);
            throw new AssertionException(CommonResultCode.FETCH_EMAIL_MESSAGE_FAILED, "从邮件Message里面获取正文纯文本信息失败");
        }
    }

    /**
     * 从邮件Message里面获取正文中inline的文件信息
     *
     * @param message 邮件Message
     * @return inline文件列表
     */
    public static List<EmailFileInfo> fetchEmailInlineFileFromMessage(Message message) {

        return fetchFileFromMessage(message, Part.INLINE);
    }

    /**
     * 从邮件Message里面获取附件信息
     *
     * @param message 邮件Message
     * @return 附件列表
     */
    public static List<EmailFileInfo> fetchEmailAttachmentFileFromMessage(Message message) {

        return fetchFileFromMessage(message, Part.ATTACHMENT);
    }

    /**
     * 下载邮件，保存邮件格式为EML文件
     *
     * @param message
     * @return
     */
    public static String downloadEmail(Message message) {

        try {
            File file = FileUtil.generateTmpFile("eml");
            ((MimeMessage) message).writeTo(new FileOutputStream(file));
            return file.getAbsolutePath();

        } catch (Exception e) {

            LOGGER.error("downloadEmail 下载邮件失败", e);
            throw new AssertionException(CommonResultCode.FETCH_EMAIL_MESSAGE_FAILED, "下载邮件失败");
        }
    }

    /**
     * 从邮件Messgae中获取文件信息
     *
     * @param message      邮件Message
     * @param bodyPartType 邮件体类型（Part.ATTACHMENT / Part.INLINE）
     * @return
     */
    private static List<EmailFileInfo> fetchFileFromMessage(Part message, String bodyPartType) {

        try {
            List<EmailFileInfo> emailFileInfoList = new ArrayList<>();
            if (message.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) message.getContent();
                for (int i = 0; i < multipart.getCount(); i++) {
                    emailFileInfoList.addAll(fetchFileFromMessage(multipart.getBodyPart(i), bodyPartType));
                }
            } else if (message.isMimeType("message/rfc822")) {
                // EML文件
                emailFileInfoList.addAll(fetchFileFromMessage((Part) message.getContent(), bodyPartType));
            } else if (bodyPartType.equalsIgnoreCase(message.getDisposition())) {
                emailFileInfoList.addAll(fetchFileFromBodyPart((BodyPart) message));
            }
            return emailFileInfoList;
        } catch (Exception e) {

            LOGGER.error("fetchFileFromMessage 从邮件Message里面获取文件信息错误 message = " + message, e);
            throw new AssertionException(CommonResultCode.FETCH_EMAIL_MESSAGE_FAILED, "从邮件Message里面获取文件信息错误");
        }
    }

    private static List<EmailFileInfo> fetchFileFromBodyPart(BodyPart bodyPart) {

        try {
            List<EmailFileInfo> emailFileInfoList = new ArrayList<>();

            // 获取fullContentType，样式如：application/pdf; name=invoice-test001.pdf
            // 根据MIME标准，如果文件名包含特殊字符或空格，文件名会被双引号括起来。例如：
            // Invoice 57439.pdf 包含空格，因此会被表示为 "Invoice 57439.pdf"。
            // report(2023).pdf 包含括号，因此会被表示为 "report(2023).pdf"。
            String fullContentType = bodyPart.getContentType();
            String contentType = StringUtils.substringBefore(fullContentType, ";").trim();
            String originalFileName = fetchOriginalFileName(bodyPart);

            // 创建临时文件，并本地保存文件
            InputStream is = bodyPart.getInputStream();

            // 判断文件是否为空文件
            byte[] buf = new byte[4096];
            int bytesRead = is.read(buf);
            if (bytesRead == -1) {
                LOGGER.warn("fetchFileFromBodyPart 文件为空文件 originalFileName = " + originalFileName);
                return emailFileInfoList;
            }

            File file = FileUtil.generateTmpFile(StringUtils.substringAfter(originalFileName, "."));
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buf, 0, bytesRead);
            while ((bytesRead = is.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
            fos.close();

            // 设置属性信息
            EmailFileInfo emailFileInfo = new EmailFileInfo();
            emailFileInfo.setContentType(contentType);
            emailFileInfo.setOriginalFileName(originalFileName);
            emailFileInfo.setLocalSavedPath(file.getAbsolutePath());
            emailFileInfo.setBodyPartType(bodyPart.getDisposition());
            LOGGER.info("fetchFileFromMessage: " + emailFileInfo);

            // 判断附件是否是EML文件，如果是EML文件，递归解析EML文件
            HttpContentTypeEnum httpContentType = HttpContentTypeEnum.getByRawContentType(contentType);
            if (httpContentType == HttpContentTypeEnum.EML) {
                List<EmailFileInfo> emailFileInfos = fetchFileFromMessage(new MimeMessage(Session.getDefaultInstance(System.getProperties()), is), bodyPart.getDisposition());
                emailFileInfoList.addAll(emailFileInfos);
            }
            emailFileInfoList.add(emailFileInfo);

            return emailFileInfoList;

        } catch (Exception e) {

            LOGGER.error("fetchFileFromBodyPart 从邮件Message里面获取文件信息错误 bodyPart = " + bodyPart, e);
            throw new AssertionException(CommonResultCode.FETCH_EMAIL_MESSAGE_FAILED, "从邮件Message里面获取文件信息错误");
        }
    }

    /**
     * 获取邮件中的原文件名
     */
    private static String fetchOriginalFileName(BodyPart bodyPart) throws MessagingException {

        String originalFileName = StringUtils.trim(bodyPart.getFileName());
        if (StringUtils.isNotBlank(originalFileName)) {
            return originalFileName;
        }

        // 尝试从Content-Type中获取文件名
        String fullContentType = StringUtils.trim(bodyPart.getContentType());
        originalFileName = StringUtils.strip(StringUtils.trim(StringUtils.substringAfter(fullContentType, "name=")), "\"");
        if (StringUtils.isNotBlank(originalFileName)) {
            return originalFileName;
        }

        return null;
    }

    /**
     * 从多媒体中获取纯文本信息
     *
     * @param mimeMultipart 邮件多媒体信息
     * @return 纯文本信息
     * @throws MessagingException
     * @throws IOException
     */
    private static String fetchTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + fetchTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }
}
