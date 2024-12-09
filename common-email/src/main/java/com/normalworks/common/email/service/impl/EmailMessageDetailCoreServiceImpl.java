package com.normalworks.common.email.service.impl;

import com.normalworks.common.email.model.EmailMessageDetail;
import com.normalworks.common.email.repository.EmailMessageDetailRepository;
import com.normalworks.common.email.service.EmailMessageDetailCoreService;
import com.normalworks.common.medium.enums.MediumExtInfoKeyEnum;
import com.normalworks.common.medium.model.File;
import com.normalworks.common.medium.model.Image;
import com.normalworks.common.medium.service.FileCoreService;
import com.normalworks.common.medium.service.ImageCoreService;
import com.normalworks.common.utils.EmailServerAccessUtil;
import com.normalworks.common.utils.FileUtil;
import com.normalworks.common.utils.PdfUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.utils.model.EmailFileInfo;
import com.normalworks.common.utils.model.EmailMessageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * EmailMessageDetailCoreServiceImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/12/29 4:37 PM
 */
@Service
public class EmailMessageDetailCoreServiceImpl implements EmailMessageDetailCoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailMessageDetailCoreServiceImpl.class);

    /**
     * 邮件内容最大长度
     */
    private static final int CONTEXT_TEXT_MAX_LENGTH = 8192;

    @Resource
    private EmailMessageDetailRepository emailMessageDetailRepository;

    @Resource
    private ImageCoreService imageCoreService;

    @Resource
    private FileCoreService fileCoreService;

    @Override
    public List<EmailMessageDetail> fetchUnReadEmailMessageAndStore(String emailUserName,
                                                                    String emailDomain,
                                                                    String emailPwd,
                                                                    String assetDomainUrl,
                                                                    String uploadUserId,
                                                                    List<HttpContentTypeEnum> supportedImageAttachmentContentTypeList,
                                                                    List<HttpContentTypeEnum> supportedFileAttachmentContentTypeList) {

        List<EmailMessageInfo> emailMessageInfoList = EmailServerAccessUtil.fetchUnReadEmailMessage(emailUserName, emailDomain, emailPwd);
        if (CollectionUtils.isEmpty(emailMessageInfoList)) {
            LOGGER.info("该邮箱暂时没有未读邮件，emailUserName = " + emailUserName + ", emailDomain = " + emailDomain + ", emailPwd = " + emailPwd);
            return new ArrayList<>();
        }

        String emailToAddr = emailUserName + "@" + emailDomain;

        try {
            // 存储邮件内容
            List<EmailMessageDetail> emailMessageDetailList = new ArrayList<>();
            for (EmailMessageInfo emailMessageInfo : emailMessageInfoList) {
                EmailMessageDetail emailMessageDetail = toEmailMessageDetail(emailMessageInfo, emailToAddr, assetDomainUrl, uploadUserId, supportedImageAttachmentContentTypeList, supportedFileAttachmentContentTypeList);
                try {
                    emailMessageDetailRepository.create(emailMessageDetail);
                    emailMessageDetailList.add(emailMessageDetail);
                } catch (Exception exception) {
                    // 幂等操作
                    if (exception instanceof DuplicateKeyException) {
                        LOGGER.info("请求MSG ID幂等，继续读取邮件内容，emailMessageDetail = " + emailMessageDetail);
                        EmailServerAccessUtil.markEmailMessageRead(emailUserName,
                                emailDomain,
                                emailPwd,
                                Arrays.asList(emailMessageDetail.getEmailMsgId()));
                        LOGGER.info("标记邮件已读，emailMessageDetail = " + emailMessageDetail);
                    } else {
                        LOGGER.error("存储邮件内容失败，emailMessageDetail = " + emailMessageDetail, exception);
                        throw exception;
                    }
                }
            }
            return emailMessageDetailList;

        } finally {

            deleteLocalSavedFile(emailMessageInfoList);
        }
    }

    /**
     * 删除本地保存的文件
     *
     * @param emailMessageInfoList
     */
    private void deleteLocalSavedFile(List<EmailMessageInfo> emailMessageInfoList) {

        for (EmailMessageInfo emailMessageInfo : emailMessageInfoList) {
            if (StringUtils.isNotBlank(emailMessageInfo.getEmailFileLocalSavedPath())) {
                FileUtil.delete(emailMessageInfo.getEmailFileLocalSavedPath());
            }
            if (!CollectionUtils.isEmpty(emailMessageInfo.getAttachmentFileInfoList())) {
                for (EmailFileInfo emailFileInfo : emailMessageInfo.getAttachmentFileInfoList()) {
                    FileUtil.delete(emailFileInfo.getLocalSavedPath());
                    FileUtil.delete(emailFileInfo.getThumbnailLocalSavedPath());
                }
            }
            if (!CollectionUtils.isEmpty(emailMessageInfo.getInlineFileInfoList())) {
                for (EmailFileInfo emailFileInfo : emailMessageInfo.getInlineFileInfoList()) {
                    FileUtil.delete(emailFileInfo.getLocalSavedPath());
                    FileUtil.delete(emailFileInfo.getThumbnailLocalSavedPath());
                }
            }
        }
    }

    private EmailMessageDetail toEmailMessageDetail(EmailMessageInfo emailMessageInfo,
                                                    String emailToAddr,
                                                    String assetDomainUrl,
                                                    String uploadUserId,
                                                    List<HttpContentTypeEnum> supportedImageAttachmentContentTypeList,
                                                    List<HttpContentTypeEnum> supportedFileAttachmentContentTypeList) {

        EmailMessageDetail emailMessageDetail = new EmailMessageDetail();
        emailMessageDetail.setEmailMsgId(emailMessageInfo.getEmailBaseInfo().getMessageId());
        emailMessageDetail.setSubject(emailMessageInfo.getEmailBaseInfo().getSubject());
        // 截取纯文本内容
        emailMessageDetail.setContentText(StringUtils.substring(emailMessageInfo.getContextText(), 0, CONTEXT_TEXT_MAX_LENGTH));
        emailMessageDetail.setFromAddress(emailMessageInfo.getEmailBaseInfo().getFromAddress());
        emailMessageDetail.setToAddress(emailToAddr);
        emailMessageDetail.setSentTime(emailMessageInfo.getEmailBaseInfo().getSendDate());
        emailMessageDetail.setReceivedTime(emailMessageInfo.getEmailBaseInfo().getReceivedDate());
        emailMessageDetail.setAttachmentMediumList(toAttachmentMediumList(emailMessageInfo.getAttachmentFileInfoList(), assetDomainUrl, uploadUserId, supportedImageAttachmentContentTypeList, supportedFileAttachmentContentTypeList));
        emailMessageDetail.setInlineMediumList(toAttachmentMediumList(emailMessageInfo.getInlineFileInfoList(), assetDomainUrl, uploadUserId, supportedImageAttachmentContentTypeList, supportedFileAttachmentContentTypeList));
        emailMessageDetail.setEmailFileMedium(toEmailFileMedium(emailMessageInfo, assetDomainUrl, uploadUserId));

        return emailMessageDetail;
    }

    private String toEmailFileMedium(EmailMessageInfo emailMessageInfo, String assetDomainUrl, String uploadUserId) {
        String fileName = StringUtils.isBlank(emailMessageInfo.getEmailBaseInfo().getSubject()) ? "ANONYMOUS" : emailMessageInfo.getEmailBaseInfo().getSubject() + ".eml";
        File file = fileCoreService.uploadFile(fileName, emailMessageInfo.getEmailFileLocalSavedPath(), HttpContentTypeEnum.EML, assetDomainUrl, uploadUserId, null);
        return file.getMediumId();
    }

    private List<String> toAttachmentMediumList(List<EmailFileInfo> attachmentFileInfoList,
                                                String assetDomainUrl,
                                                String uploadUserId,
                                                List<HttpContentTypeEnum> supportedImageAttachmentContentTypeList,
                                                List<HttpContentTypeEnum> supportedFileAttachmentContentTypeList) {

        if (CollectionUtils.isEmpty(attachmentFileInfoList)) {
            return new ArrayList<>();
        }

        List<String> mediumIdList = new ArrayList<>();
        for (EmailFileInfo emailFileInfo : attachmentFileInfoList) {

            HttpContentTypeEnum httpContentTypeEnum = HttpContentTypeEnum.getByRawContentType(emailFileInfo.getContentType());
            LOGGER.info("httpContentTypeEnum = " + httpContentTypeEnum);

            if (httpContentTypeEnum == null) {
                LOGGER.error("不支持的HttpContentType类型，contentType = " + emailFileInfo.getContentType() + " , fileInfo = " + emailFileInfo);
                throw new AssertionException(CommonResultCode.PARAM_ILLEGAL, "不支持的HttpContentType类型");
            }

            if (httpContentTypeEnum == HttpContentTypeEnum.OCTET_STREAM) {
                // 将二进制流文件，通过对应文件的后缀，转化为对应的文件类型
                String fileExtension = "." + FileUtil.fetchFileExtension(emailFileInfo.getOriginalFileName());
                HttpContentTypeEnum priorityMatchedHttpContentType = HttpContentTypeEnum.fetchPriorityMatchedHttpContentTypeByExtensionName(fileExtension);
                if (priorityMatchedHttpContentType == null) {
                    LOGGER.error("不支持的二进制流文件类型，emailFileInfo = " + emailFileInfo);
                    continue;
                }
                httpContentTypeEnum = priorityMatchedHttpContentType;
                emailFileInfo.setContentType(HttpContentTypeEnum.getRawContentType(httpContentTypeEnum));
                LOGGER.info("通过二进制流文件类型，转化为对应的文件类型，emailFileInfo = " + emailFileInfo + " , 更新后的httpContentTypeEnum = " + httpContentTypeEnum);
            }

            if (supportedImageAttachmentContentTypeList.contains(httpContentTypeEnum)) {
                LOGGER.info("支持的图片类型，开始上传图片，emailFileInfo = " + emailFileInfo);
                Image image = imageCoreService.uploadImage(emailFileInfo.getOriginalFileName(), emailFileInfo.getLocalSavedPath(), HttpContentTypeEnum.getByRawContentType(emailFileInfo.getContentType()), assetDomainUrl, uploadUserId);
                mediumIdList.add(image.getMediumId());
                continue;
            }

            if (supportedFileAttachmentContentTypeList.contains(httpContentTypeEnum)) {

                LOGGER.info("支持的文件类型，开始上传文件，emailFileInfo = " + emailFileInfo);
                Map<String, String> extInfo = null;
                if (httpContentTypeEnum == HttpContentTypeEnum.PDF) {
                    java.io.File thumbnailFile = FileUtil.generateTmpFile("png");
                    emailFileInfo.setThumbnailLocalSavedPath(thumbnailFile.getAbsolutePath());

                    // 将PDF的第一页提取出来，转化为PNG图片，用于PDF的缩略图
                    PdfUtil.drawPdfPage2Png(emailFileInfo.getLocalSavedPath(), 1, emailFileInfo.getThumbnailLocalSavedPath());

                    // 将缩略图上传到文件存储上
                    Image coverImage = imageCoreService.uploadImage(emailFileInfo.getThumbnailLocalSavedPath(), HttpContentTypeEnum.PNG, assetDomainUrl, uploadUserId);

                    // 设置PDF的缩略图信息
                    extInfo = new HashMap<>();
                    extInfo.put(MediumExtInfoKeyEnum.COVER_IMAGE_URL.getValue(), coverImage.getSourceImageUrl());

                }

                File file = fileCoreService.uploadFile(emailFileInfo.getOriginalFileName(), emailFileInfo.getLocalSavedPath(), HttpContentTypeEnum.getByRawContentType(emailFileInfo.getContentType()), assetDomainUrl, uploadUserId, extInfo);
                mediumIdList.add(file.getMediumId());
                continue;
            }

            LOGGER.warn("不支持的附件类型，忽略处理，emailFileInfo = " + emailFileInfo);
        }

        return mediumIdList;
    }
}
