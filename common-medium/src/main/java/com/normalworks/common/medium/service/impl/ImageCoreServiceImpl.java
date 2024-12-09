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
import com.normalworks.common.medium.model.Image;
import com.normalworks.common.medium.repository.ImageRepository;
import com.normalworks.common.medium.service.ImageCoreService;
import com.normalworks.common.medium.utils.ImageUtil;
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
import java.util.UUID;

/**
 * ImageCoreServiceImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 4:39 PM
 */
@Service
public class ImageCoreServiceImpl implements ImageCoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCoreServiceImpl.class);

    @Resource
    private ImageRepository imageRepository;

    @Resource
    private ObjectStorageCloudClient objectStorageCloudClient;

    @Override
    public Image register(String userId, String imageUrl, String imageName, String imageDigest, Long imageSize, Integer imageHeight, Integer imageWidth, HttpContentTypeEnum imageContentType, String standardImageUrl) {

        // 通过内容摘要查看是否存在已上传的图片
        Image image = imageRepository.queryUserIdByDigest(userId, imageDigest);

        if (image != null) {
            return image;
        }

        try {

            image = new Image();
            image.setUploadUserId(userId);
            image.setSourceImageUrl(imageUrl);
            image.setMediumName(FileUtil.filterFileExtensionSpecialCharOrFetchFromUrl(imageName, imageUrl));
            image.setMediumDigest(imageDigest);
            image.setSourceImageHeight(imageHeight);
            image.setSourceImageWidth(imageWidth);
            image.setMediumContentType(imageContentType);
            image.setStandardImageUrl(standardImageUrl);
            image.setMediumType(MediumTypeEnum.IMAGE);
            image.setMediumStatus(MediumStatusEnum.RELEASE);
            image.setMediumSize(imageSize);

            imageRepository.create(image);
            LOGGER.info("创建存储新的image = " + image);

        } catch (Exception e) {

            // 幂等操作
            if (e instanceof DuplicateKeyException) {
                LOGGER.info("Image 已存在，直接返回，imageDigest = " + imageDigest);
                image = imageRepository.queryUserIdByDigest(userId, imageDigest);
                if (image == null) {
                    // 由于上层服务的事务级别选择的是Read Committed，所以在查询的时候，可能会出现并发场景下，另一个事务未提交，导致当前事务查询不到的情况，出现幻读
                    // 继续将异常抛出
                    LOGGER.warn("数据幂等，但是查询不到，imageDigest = " + imageDigest, e);
                    throw e;
                }
                return image;
            } else {
                throw e;
            }
        }

        return image;
    }

    @Override
    public Image register(String userId, String imageUrl, String imageName, ByteArrayOutputStream baos, HttpContentTypeEnum imageContentType) {
        InputStream inputStreamCopy1 = null;
        InputStream inputStreamCopy2 = null;

        try {
            com.normalworks.common.medium.model.Image image = new com.normalworks.common.medium.model.Image();

            // 获取文件内容摘要和文件内容大小
            inputStreamCopy1 = new ByteArrayInputStream(baos.toByteArray());
            MediumUtil.generateDigestAndSize(inputStreamCopy1, image);

            // 通过图片摘要获取图片，如果存在历史图片就直接返回信息
            com.normalworks.common.medium.model.Image historyImage = imageRepository.queryUserIdByDigest(userId, image.getMediumDigest());
            if (historyImage != null) {
                LOGGER.info("已存在该图片的历史上传image = " + historyImage);
                return historyImage;
            }

            // 获取图片的长宽像素，以及图片类型
            inputStreamCopy2 = new ByteArrayInputStream(baos.toByteArray());
            ImageUtil.processImageStream(inputStreamCopy2, image);

            image.setSourceImageUrl(imageUrl);
            image.setStandardImageUrl(imageUrl);

            image.setMediumName(FileUtil.filterFileExtensionSpecialCharOrFetchFromUrl(imageName, imageUrl));
            image.setUploadUserId(userId);
            image.setMediumContentType(imageContentType);
            image.setMediumStatus(MediumStatusEnum.RELEASE);
            imageRepository.create(image);

            return image;

        } finally {

            IOUtils.closeQuietly(inputStreamCopy1);
            IOUtils.closeQuietly(inputStreamCopy2);
        }
    }

    @Override
    public Image uploadImage(String localImagePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId) {

        return uploadImage(null, localImagePath, contentTypeEnum, assetDomainUrl, uploadUserId);
    }

    @Override
    public Image uploadImage(String imageName, String localImagePath, HttpContentTypeEnum contentTypeEnum, String assetDomainUrl, String uploadUserId) {

        try {
            // 上传到OSS文件存储
            String fullKeyName = "assets/image/" + DateUtil.currYYYYMMDD() + "/" + UUID.randomUUID() + contentTypeEnum.getExtensionName();
            objectStorageCloudClient.uploadObject(new FileInputStream(localImagePath), fullKeyName);

            // 注册图片媒体
            String fullUrl = assetDomainUrl + "/" + fullKeyName;

            // 如果没有原始图片名称，则从本地图片路径中获取
            if (StringUtils.isBlank(imageName)) {
                imageName = FileUtil.fetchFileName(localImagePath);
            }
            LoggerUtil.info(LOGGER, "图片URL完整路径：", fullUrl, " , 图片名称：", imageName);
            return register(uploadUserId, fullUrl, imageName, MediumUtil.cloneInputStream(new FileInputStream(localImagePath)), contentTypeEnum);
        } catch (Exception e) {
            LOGGER.error("上传本地图片出现异常 localImagePath = " + localImagePath + " , contentType = " + HttpContentTypeEnum.getRawContentType(contentTypeEnum), e);
            throw new AssertionException(CommonResultCode.UPLOAD_MEDIUM_FAILED, "上传本地媒体文件系统异常");
        }
    }
}
