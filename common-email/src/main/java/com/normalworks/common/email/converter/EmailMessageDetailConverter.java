package com.normalworks.common.email.converter;

import com.alibaba.fastjson.JSON;
import com.normalworks.common.email.model.EmailMessageDetail;
import com.normalworks.common.email.model.dal.EmailMsgDetailDO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * EmailMessageDetailConverter
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/21 8:03 PM
 */
public class EmailMessageDetailConverter {

    public static List<EmailMessageDetail> batchToModel(List<EmailMsgDetailDO> emailMsgDetailDOList) {

        List<EmailMessageDetail> emailMessageDetailList = new ArrayList<>();
        if (CollectionUtils.isEmpty(emailMsgDetailDOList)) {
            return emailMessageDetailList;
        }

        for (EmailMsgDetailDO emailMsgDetailDO : emailMsgDetailDOList) {
            emailMessageDetailList.add(toModel(emailMsgDetailDO));
        }
        return emailMessageDetailList;
    }

    public static EmailMessageDetail toModel(EmailMsgDetailDO emailMsgDetailDO) {

        if (null == emailMsgDetailDO) {
            return null;
        }

        EmailMessageDetail emailMessageDetail = new EmailMessageDetail();
        emailMessageDetail.setEmailMsgId(emailMsgDetailDO.getEmailMsgId());
        emailMessageDetail.setSubject(emailMsgDetailDO.getSubject());
        emailMessageDetail.setContentText(emailMsgDetailDO.getTextContent());
        emailMessageDetail.setFromAddress(emailMsgDetailDO.getFromAddress());
        emailMessageDetail.setToAddress(emailMsgDetailDO.getToAddress());
        emailMessageDetail.setSentTime(emailMsgDetailDO.getSentTime());
        emailMessageDetail.setReceivedTime(emailMsgDetailDO.getReceivedTime());
        emailMessageDetail.setAttachmentMediumList(JSON.parseArray(emailMsgDetailDO.getAttachmentMediumList(), String.class));
        emailMessageDetail.setInlineMediumList(JSON.parseArray(emailMsgDetailDO.getInlineMediumList(), String.class));
        emailMessageDetail.setEmailFileMedium(emailMsgDetailDO.getEmailFileMedium());
        emailMessageDetail.setCreateTime(emailMsgDetailDO.getCreateTime());
        emailMessageDetail.setModifyTime(emailMsgDetailDO.getModifyTime());

        return emailMessageDetail;
    }

    public static EmailMsgDetailDO toDO(EmailMessageDetail emailMessageDetail) {

        if (null == emailMessageDetail) {
            return null;
        }

        EmailMsgDetailDO emailMsgDetailDO = new EmailMsgDetailDO();
        emailMsgDetailDO.setEmailMsgId(emailMessageDetail.getEmailMsgId());
        emailMsgDetailDO.setSubject(emailMessageDetail.getSubject());
        emailMsgDetailDO.setTextContent(emailMessageDetail.getContentText());
        emailMsgDetailDO.setFromAddress(emailMessageDetail.getFromAddress());
        emailMsgDetailDO.setToAddress(emailMessageDetail.getToAddress());
        emailMsgDetailDO.setSentTime(emailMessageDetail.getSentTime());
        emailMsgDetailDO.setReceivedTime(emailMessageDetail.getReceivedTime());
        emailMsgDetailDO.setAttachmentMediumList(JSON.toJSONString(emailMessageDetail.getAttachmentMediumList()));
        emailMsgDetailDO.setInlineMediumList(JSON.toJSONString(emailMessageDetail.getInlineMediumList()));
        emailMsgDetailDO.setEmailFileMedium(emailMessageDetail.getEmailFileMedium());
        emailMsgDetailDO.setCreateTime(emailMessageDetail.getCreateTime());
        emailMsgDetailDO.setModifyTime(emailMessageDetail.getModifyTime());

        return emailMsgDetailDO;
    }
}
