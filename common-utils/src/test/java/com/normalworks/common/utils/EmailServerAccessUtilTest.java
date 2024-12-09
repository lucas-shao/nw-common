package com.normalworks.common.utils;

import com.normalworks.common.utils.model.EmailMessageInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * EmailServerAccessUtilTest
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/12/29 3:42 PM
 */
public class EmailServerAccessUtilTest {

    @Test
    public void testCheckUserNameAvailable() {
        Boolean result = EmailServerAccessUtil.checkUserNameAvailable("lucas", "xiaocichang.com", "123456");
        System.out.println(result);
    }

    @Test
    public void testCheckExistUnreadMessage() {
        Boolean result = EmailServerAccessUtil.checkExistUnreadMessage("lucas", "xiaocichang.com", "123456");
        System.out.println(result);
    }

    @Test
    public void testFetchUnReadEmailMessage() {
        List<EmailMessageInfo> emailMessageInfos = EmailServerAccessUtil.fetchUnReadEmailMessage("lucas", "xiaocichang.com", "123456");
        System.out.println(emailMessageInfos);
    }

    @Test
    public void testMarkEmailMessageRead() {
        List<EmailMessageInfo> emailMessageInfos = EmailServerAccessUtil.fetchUnReadEmailMessage("lucas", "xiaocichang.com", "123456");
        List<String> emailMessageIds = new ArrayList<>();
        for (EmailMessageInfo emailMessageInfo : emailMessageInfos) {
            emailMessageIds.add(emailMessageInfo.getEmailBaseInfo().getMessageId());
        }
        EmailServerAccessUtil.markEmailMessageRead("lucas", "xiaocichang.com", "123456", emailMessageIds);
        System.out.println(emailMessageInfos);
    }
}
