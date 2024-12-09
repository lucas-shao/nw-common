package com.normalworks.common.cloud.aws.client.impl;

import com.normalworks.common.cloud.aws.client.AwsS3Client;
import com.normalworks.common.cloud.aws.config.AwsCommonConfig;
import com.normalworks.common.cloud.aws.config.AwsS3Config;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URL;
import java.time.Duration;

/**
 * AwsS3ClientImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/12/20 10:26 AM
 */
@Service
public class AwsS3ClientImpl implements AwsS3Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3ClientImpl.class);

    @Resource
    private AwsCommonConfig awsCommonConfig;

    @Resource
    private AwsS3Config awsS3Config;

    /**
     * s3访问客户端
     */
    private S3Client s3Client;

    /**
     * URL加签器
     */
    private S3Presigner s3Presigner;

    @Override
    public URL generatePreSignedUploadUrl(String fullKeyName, String contentType, long expTimeInSec) {

        try {
            LOGGER.info("开始生成临时上传URL fullKeyName = " + fullKeyName + " , contentType = " + contentType + " , expTimeInSec = " + expTimeInSec);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(awsS3Config.getBucketName()).key(fullKeyName).contentType(contentType).build();

            PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder().signatureDuration(Duration.ofSeconds(expTimeInSec)).putObjectRequest(putObjectRequest).build();

            PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);

            return presignedPutObjectRequest.url();

        } catch (Exception exception) {

            LOGGER.error("调用generatePreSignedUploadUrl服务系统异常", exception);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用generatePreSignedUploadUrl服务系统异常");
        }
    }

    @Override
    public void createFolder(String folderName) {

        try {
            LOGGER.info("开始创建文件夹 folderName = " + folderName);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsS3Config.getBucketName()).key(folderName).build();
            s3Client.putObject(putObjectRequest, RequestBody.empty());

        } catch (Exception exception) {

            LOGGER.error("调用createFolder服务系统异常", exception);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用createFolder服务系统异常");
        }
    }

    @PostConstruct
    private void init() {
        Region region = Region.of(awsCommonConfig.getRegion());
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsCommonConfig.getAccessKeyId(), awsCommonConfig.getAccessKeySecret());
        s3Client = S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();

        s3Presigner = S3Presigner.builder().region(region).build();
    }
}
