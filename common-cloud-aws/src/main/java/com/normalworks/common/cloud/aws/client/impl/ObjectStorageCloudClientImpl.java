package com.normalworks.common.cloud.aws.client.impl;

import com.normalworks.common.cloud.api.ObjectStorageCloudClient;
import com.normalworks.common.cloud.aws.config.AwsCommonConfig;
import com.normalworks.common.cloud.aws.config.AwsS3Config;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ObjectStorageCloudClientImpl
 * 对象存储实现类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/1 5:31 PM
 */
@Service
public class ObjectStorageCloudClientImpl implements ObjectStorageCloudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectStorageCloudClientImpl.class);

    @Resource
    private AwsCommonConfig awsCommonConfig;

    @Resource
    private AwsS3Config awsS3Config;

    /**
     * s3访问客户端
     */
    private S3Client s3Client;

    @Override
    public void uploadObject(InputStream inputStream, String fullKeyName) {

        try {
            LOGGER.info("上传文件对象 fullKeyName = " + fullKeyName);

            s3Client.putObject(PutObjectRequest.builder().bucket(awsS3Config.getBucketName()).key(fullKeyName).build(), RequestBody.fromInputStream(inputStream, inputStream.available()));

        } catch (SdkServiceException sdkServiceException) {

            // 服务端异常
            LOGGER.error("调用aws s3服务端异常", sdkServiceException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 uploadObject 服务端异常 fullKeyName = " + fullKeyName);

        } catch (SdkClientException sdkClientException) {

            // 客户端异常
            LOGGER.error("调用aws s3客户端异常", sdkClientException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 uploadObject 客户端异常 fullKeyName = " + fullKeyName);

        } catch (Throwable throwable) {

            // 系统异常
            LOGGER.error("调用aws s3系统异常", throwable);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 uploadObject 系统异常 fullKeyName = " + fullKeyName);
        }

    }

    @Override
    public BufferedReader getObject(String fullKeyName) {

        try {
            LOGGER.info("获取文件对象 fullKeyName = " + fullKeyName);

            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(awsS3Config.getBucketName()).key(fullKeyName).build();
            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseInputStream));
            return reader;

        } catch (SdkServiceException sdkServiceException) {

            // 服务端异常
            LOGGER.error("调用aws s3服务端异常", sdkServiceException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 getObject 服务端异常 fullKeyName = " + fullKeyName);

        } catch (SdkClientException sdkClientException) {

            // 客户端异常
            LOGGER.error("调用aws s3客户端异常", sdkClientException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 getObject 客户端异常 fullKeyName = " + fullKeyName);

        } catch (Throwable throwable) {

            // 系统异常
            LOGGER.error("调用aws s3系统异常", throwable);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 getObject 系统异常 fullKeyName = " + fullKeyName);
        }
    }

    @Override
    public void deleteObject(String fullKeyName) {

        try {
            LOGGER.info("删除文件对象 fullKeyName = " + fullKeyName);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(awsS3Config.getBucketName()).key(fullKeyName).build();
            s3Client.deleteObject(deleteObjectRequest);

        } catch (SdkServiceException sdkServiceException) {

            // 服务端异常
            LOGGER.error("调用aws s3服务端异常", sdkServiceException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 deleteObject 服务端异常 fullKeyName = " + fullKeyName);

        } catch (SdkClientException sdkClientException) {

            // 客户端异常
            LOGGER.error("调用aws s3客户端异常", sdkClientException);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 deleteObject 客户端异常 fullKeyName = " + fullKeyName);

        } catch (Throwable throwable) {

            // 系统异常
            LOGGER.error("调用aws s3系统异常", throwable);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用aws s3 deleteObject 系统异常 fullKeyName = " + fullKeyName);
        }
    }

    @PostConstruct
    private void init() {
        Region region = Region.of(awsCommonConfig.getRegion());
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsCommonConfig.getAccessKeyId(), awsCommonConfig.getAccessKeySecret());
        s3Client = S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();
    }
}
