package com.normalworks.common.medium.service.impl;

import com.normalworks.common.cloud.api.ObjectStorageCloudClient;
import com.normalworks.common.utils.DateUtil;
import com.normalworks.common.utils.FileUtil;
import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.medium.enums.MediumStatusEnum;
import com.normalworks.common.medium.enums.MediumTypeEnum;
import com.normalworks.common.medium.model.File;
import com.normalworks.common.medium.repository.FileRepository;
import com.normalworks.common.medium.service.FileCoreService;
import com.normalworks.common.medium.utils.MediumUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

/**
 * FileCoreServiceImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 6:13 PM
 */
@Service
public class FileCoreServiceImpl implements FileCoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCoreServiceImpl.class);

    @Resource
    private FileRepository fileRepository;

    @Resource
    private ObjectStorageCloudClient objectStorageCloudClient;

    @Override
    public File register(String userId, String fileUrl, String fileName, String fileDigest, Long fileSize, HttpContentTypeEnum fileContentType, Map<String, String> extInfo) {

        // 通过内容摘要查看是否存在已上传的文件
        File file = fileRepository.queryByUserIdAndDigest(userId, fileDigest);

        if (file != null) {
            return file;
        }

        try {
            file = new File();
            file.setUploadUserId(userId);
            file.setFileUrl(fileUrl);
            file.setMediumName(FileUtil.filterFileExtensionSpecialCharOrFetchFromUrl(fileName, fileUrl));
            file.setMediumDigest(fileDigest);
            file.setMediumContentType(fileContentType);
            file.setMediumType(MediumTypeEnum.FILE);
            file.setMediumStatus(MediumStatusEnum.RELEASE);
            file.setMediumSize(fileSize);
            file.setExtInfo(extInfo);

            fileRepository.create(file);
            LOGGER.info("创建存储新的file = " + file);

        } catch (Exception e) {

            // 幂等操作
            if (e instanceof DuplicateKeyException) {
                LOGGER.info("File 已存在，直接返回，fileDigest = " + fileDigest);
                file = fileRepository.queryByUserIdAndDigest(userId, fileDigest);
                if (file == null) {
                    // 由于上层服务的事务级别选择的是Read Committed，所以在查询的时候，可能会出现并发场景下，另一个事务未提交，导致当前事务查询不到的情况，出现幻读
                    // 继续将异常抛出
                    LOGGER.warn("数据幂等，但是查询不到，fileDigest = " + fileDigest, e);
                    throw e;
                }
                return file;
            } else {
                throw e;
            }
        }

        return file;
    }

    @Override
    public File register(String userId, String fileUrl, String fileName, ByteArrayOutputStream baos, HttpContentTypeEnum fileContentType, Map<String, String> extInfo) {

        InputStream inputStreamCopy1 = null;

        try {
            File file = new File();

            // 获取文件内容摘要和文件内容大小
            inputStreamCopy1 = new ByteArrayInputStream(baos.toByteArray());
            MediumUtil.generateDigestAndSize(inputStreamCopy1, file);

            // 通过文件摘要获取文件，如果存在历史文件就直接返回信息
            File historyFile = fileRepository.queryUserIdByDigest(userId, file.getMediumDigest());
            if (historyFile != null) {
                LOGGER.info("已存在该文件的历史上传file = " + historyFile);
                return historyFile;
            }

            file.setFileUrl(fileUrl);
            file.setMediumName(FileUtil.filterFileExtensionSpecialCharOrFetchFromUrl(fileName, fileUrl));
            file.setUploadUserId(userId);
            file.setMediumContentType(fileContentType);
            file.setMediumStatus(MediumStatusEnum.RELEASE);
            file.setExtInfo(extInfo);
            fileRepository.create(file);

            return file;

        } finally {

            IOUtils.closeQuietly(inputStreamCopy1);
        }
    }

    @Override
    public File uploadFile(String localFilePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId, Map<String, String> mediumExtInfo) {

        return uploadFile(null, localFilePath, contentTypeEnum, assetDomainUrl, uploadUserId, mediumExtInfo);
    }

    @Override
    public File uploadFile(String fileName, String localFilePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId, Map<String, String> mediumExtInfo) {

        try {
            // 上传到OSS文件存储
            String fullKeyName = "assets/file/" + DateUtil.currYYYYMMDD() + "/" + UUID.randomUUID() + contentTypeEnum.getExtensionName();
            objectStorageCloudClient.uploadObject(new FileInputStream(localFilePath), fullKeyName);

            // 注册文件媒体
            String fullUrl = assetDomainUrl + "/" + fullKeyName;
            if (StringUtils.isBlank(fileName)) {
                fileName = FileUtil.fetchFileName(fullUrl);
            }
            LoggerUtil.info(LOGGER, "文件URL完整路径：", fullUrl, " , 文件名称：", fileName);
            return register(uploadUserId, fullUrl, fileName, MediumUtil.cloneInputStream(new FileInputStream(localFilePath)), contentTypeEnum, mediumExtInfo);
        } catch (Exception e) {
            LOGGER.error("上传本地文件出现异常 localFilePath = " + localFilePath + " , contentType = " + HttpContentTypeEnum.getRawContentType(contentTypeEnum), e);
            throw new AssertionException(CommonResultCode.UPLOAD_MEDIUM_FAILED, "上传本地媒体文件系统异常");
        }
    }
}
