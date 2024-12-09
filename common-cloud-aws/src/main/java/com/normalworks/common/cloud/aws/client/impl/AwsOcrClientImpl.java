package com.normalworks.common.cloud.aws.client.impl;

import com.normalworks.common.cloud.aws.client.AwsOcrClient;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * AwsOcrClientImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/3/9 11:43 AM
 */
@Service
public class AwsOcrClientImpl implements AwsOcrClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsOcrClientImpl.class);

    @Override
    public String ocrFileWithOnePage(File file) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

            byte[] fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);

            Document myDoc = Document.builder().bytes(SdkBytes.fromByteArray(fileBytes)).build();
            // 默认使用欧洲的OCR能力
            TextractClient textractClient = TextractClient.builder().region(Region.EU_CENTRAL_1).build();
            DetectDocumentTextRequest detectDocumentTextRequest = DetectDocumentTextRequest.builder().document(myDoc)
                    .build();
            DetectDocumentTextResponse textResponse = textractClient.detectDocumentText(detectDocumentTextRequest);

            StringBuilder text = new StringBuilder();
            for (Block block : textResponse.blocks()) {
                System.out.println("The block type is " + block.blockType().toString());
                System.out.println("The block is " + block.text());
                if (StringUtils.isBlank(block.text())) {
                    continue;
                }
                text.append(block.text() + " ");
            }
            return text.toString();

        } catch (Exception exception) {

            LOGGER.error("调用ocrFileWithOnePage服务系统异常", exception);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用ocrFileWithOnePage服务系统异常");

        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception exception) {
                    LOGGER.error("调用ocrFileWithOnePage服务关闭文件流系统异常", exception);
                    throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用ocrFileWithOnePage服务关闭文件流系统异常");

                }
            }
        }
    }
}
