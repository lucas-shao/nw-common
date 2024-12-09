package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.model.EmailMessageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * EmailServerAccessUtil
 * 访问邮件服务器工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/12/29 2:09 PM
 */
public class EmailServerAccessUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServerAccessUtil.class);

    /**
     * 校验邮箱用户名是否可用
     *
     * @param userName    邮箱用户名
     * @param emailDomain 邮箱域名
     * @param emailPwd    邮箱密码
     * @return 是否可用
     */
    public static Boolean checkUserNameAvailable(String userName, String emailDomain, String emailPwd) {

        Store store = null;
        try {

            store = connectToEmailServer(emailDomain, userName, emailPwd);
            LOGGER.info("用户确认存在 userName = " + userName + "，emailDomain = " + emailDomain + "，emailPwd = " + emailPwd);
            return Boolean.TRUE;

        } catch (AssertionException assertionException) {

            if (assertionException.getResultCode() == CommonResultCode.ACCESS_EMAIL_SERVER_AUTH_FAILED) {
                LOGGER.warn("用户不存在 userName = " + userName + "，emailDomain = " + emailDomain + "，emailPwd = " + emailPwd, assertionException);
                return Boolean.FALSE;
            } else {
                LOGGER.error("checkUserNameAvailable业务异常", assertionException);
                throw assertionException;
            }
        } catch (Throwable e) {
            LOGGER.error("checkUserNameAvailable系统异常", e);
            throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "checkUserNameAvailable系统异常");
        } finally {
            closeEmailStore(store);
        }
    }


    /**
     * 检查邮箱是否存在未读邮件
     */
    public static Boolean checkExistUnreadMessage(String userName, String emailDomain, String emailPwd) {

        Store store = null;
        Folder inbox = null;
        try {
            store = connectToEmailServer(emailDomain, userName, emailPwd);
            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            if (null != messages && messages.length > 0) {
                LOGGER.info("该用户邮箱存在未读邮件 userName = " + userName + " ， 未读邮件数 ：" + messages.length);
                return Boolean.TRUE;
            } else {
                LOGGER.info("该用户邮箱没有未读邮件 userName = " + userName + "，emailDomain = " + emailDomain + "，emailPwd = " + emailPwd);
                return Boolean.FALSE;
            }
        } catch (Throwable e) {
            LOGGER.error("checkExistUnreadMessage系统异常", e);
            throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "checkExistUnreadMessage系统异常");
        } finally {
            closeEmailInboxWithoutSave(inbox);
            closeEmailStore(store);
        }
    }

    /**
     * 读取用户未读邮件内容
     */
    public static List<EmailMessageInfo> fetchUnReadEmailMessage(String userName, String emailDomain, String emailPwd) {

        return fetchEmailMessage(userName, emailDomain, emailPwd, false);
    }

    /**
     * 读取用户已读邮件内容
     */
    public static List<EmailMessageInfo> fetchReadEmailMessage(String userName, String emailDomain, String emailPwd) {

        return fetchEmailMessage(userName, emailDomain, emailPwd, true);
    }

    /**
     * 读取用户邮件内容
     */
    private static List<EmailMessageInfo> fetchEmailMessage(String userName, String emailDomain, String emailPwd, Boolean isRead) {

        Store store = null;
        Folder inbox = null;
        try {
            store = connectToEmailServer(emailDomain, userName, emailPwd);
            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), isRead));
            List<EmailMessageInfo> emailMessageInfoList = new ArrayList<>();
            if (messages == null || messages.length == 0) {
                LOGGER.info("读取邮件内容完成，没有任何邮件，userName = " + userName + "，emailDomain = " + emailDomain + "，emailPwd = " + emailPwd + "，isRead = " + isRead);
                return emailMessageInfoList;
            }
            for (Message message : messages) {
                EmailMessageInfo emailMessageInfo = new EmailMessageInfo();
                emailMessageInfo.setEmailBaseInfo(EmailMessageUtil.fetchEmailBaseInfoFromMessage(message));
                emailMessageInfo.setContextText(EmailMessageUtil.fetchEmailContentTextFromMessage(message));
                emailMessageInfo.setAttachmentFileInfoList(EmailMessageUtil.fetchEmailAttachmentFileFromMessage(message));
                emailMessageInfo.setInlineFileInfoList(EmailMessageUtil.fetchEmailInlineFileFromMessage(message));
                emailMessageInfo.setEmailFileLocalSavedPath(EmailMessageUtil.downloadEmail(message));
                emailMessageInfoList.add(emailMessageInfo);
            }
            LOGGER.info("读取用户邮件内容完成，userName = " + userName + "，emailDomain = " + emailDomain + "，emailPwd = " + emailPwd + "，isRead = " + isRead);
            return emailMessageInfoList;
        } catch (Throwable e) {
            LOGGER.error("fetchEmailMessage系统异常", e);
            throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "fetchEmailMessage系统异常");
        } finally {
            closeEmailInboxWithoutSave(inbox);
            closeEmailStore(store);
        }
    }

    /**
     * 标记邮件已读
     */
    public static void markEmailMessageRead(String userName, String emailDomain, String
            emailPwd, List<String> emailMsgIdList) {

        if (CollectionUtils.isEmpty(emailMsgIdList)) {
            return;
        }

        Store store = null;
        Folder inbox = null;
        try {
            store = connectToEmailServer(emailDomain, userName, emailPwd);
            inbox = store.getFolder("inbox");

            // 邮箱通过可以读写的方式打开
            inbox.open(Folder.READ_WRITE);
            // 未读邮件列表
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                LOGGER.info("Unread messageId = " + ((MimeMessage) message).getMessageID());
                for (String emailMsgId : emailMsgIdList) {
                    if (StringUtils.equalsIgnoreCase(StringUtils.trim(emailMsgId), StringUtils.trim(((MimeMessage) message).getMessageID()))) {
                        // 设置为已读
                        LOGGER.info("标记邮件已读，messageId = " + emailMsgId);
                        message.setFlag(Flags.Flag.SEEN, true);
                        break;
                    }
                }
            }

            LOGGER.info("标记邮件已读完成，userName = " + userName + "，emailDomain = " + emailDomain + "，emailPwd = " + emailPwd + " , emailMsgIdList = " + emailMsgIdList);
        } catch (Throwable e) {
            LOGGER.error("markEmailMessageRead系统异常", e);
            throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "markEmailMessageRead系统异常");
        } finally {
            closeEmailInboxAndSave(inbox);
            closeEmailStore(store);
        }
    }

    /**
     * 关闭邮箱inbox，不保存对收件箱的任何更改
     */
    private static void closeEmailInboxWithoutSave(Folder inbox) {

        if (null != inbox) {
            try {
                inbox.close(false);
            } catch (Throwable e) {
                LOGGER.error("关闭邮箱inbox，不保存对收件箱的任何更改异常", e);
                throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "关闭邮箱inbox，不保存对收件箱的任何更改异常");
            }
        }
    }

    /**
     * 关闭邮箱inbox，并且保存对收件箱的更改
     */
    private static void closeEmailInboxAndSave(Folder inbox) {

        if (null != inbox) {
            try {
                inbox.close(true);
            } catch (Throwable e) {
                LOGGER.error("关闭邮箱inbox，并且保存对收件箱的更改", e);
                throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "关闭邮箱inbox，并且保存对收件箱的更改");
            }
        }
    }

    /**
     * 关闭邮箱连接
     */
    private static void closeEmailStore(Store store) {

        if (null != store) {
            try {
                // 关闭邮箱连接
                store.close();
            } catch (Throwable e) {
                LOGGER.error("关闭邮箱连接失败", e);
                throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "关闭邮箱连接失败");
            }
        }
    }

    /**
     * 连接到邮箱服务器
     */
    private static Store connectToEmailServer(String emailDomain, String userName, String emailPwd) {

        try {
            Properties properties = initEmailProperties();
            Session session = Session.getInstance(properties, null);
            Store store = session.getStore();
            store.connect("imap." + emailDomain, userName, emailPwd);
            return store;
        } catch (AuthenticationFailedException authenticationFailedException) {
            LOGGER.warn("用户不存在 userName = " + userName + "，emailDomain = " + emailDomain + "，emailPwd = " + emailPwd, authenticationFailedException);
            throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_AUTH_FAILED, "connectToEmailServer授权失败");
        } catch (Throwable e) {
            LOGGER.error("checkUserNameAvailable系统异常", e);
            throw new AssertionException(CommonResultCode.ACCESS_EMAIL_SERVER_FAILED, "connectToEmailServer系统异常");
        }
    }

    private static Properties initEmailProperties() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.ssl.enable", "true");

        return props;
    }
}
