package com.normalworks.common.email.service;

import com.normalworks.common.email.model.EmailMessageDetail;
import com.normalworks.common.utils.enums.HttpContentTypeEnum;

import java.util.List;

/**
 * EmailMessageDetailCoreService
 * 邮件内容详情核心领域服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/12/29 4:25 PM
 */
public interface EmailMessageDetailCoreService {

    /**
     * 读取并存储的未读邮件
     *
     * @param emailUserName                           邮件用户名
     * @param emailDomain                             邮件域名
     * @param emailPwd                                邮件密码
     * @param assetDomainUrl                          如果有附件需要存储，对应的媒体域名
     * @param uploadUserId                            如果有附件需要存储，对应的上传用户ID
     * @param supportedImageAttachmentContentTypeList 支持的邮件附件图片类型
     * @param supportedFileAttachmentContentTypeList  支持的邮件附件文件类型
     * @return 未读邮件的邮件内容详情
     */
    List<EmailMessageDetail> fetchUnReadEmailMessageAndStore(String emailUserName,
                                                             String emailDomain,
                                                             String emailPwd,
                                                             String assetDomainUrl,
                                                             String uploadUserId,
                                                             List<HttpContentTypeEnum> supportedImageAttachmentContentTypeList,
                                                             List<HttpContentTypeEnum> supportedFileAttachmentContentTypeList);
}
