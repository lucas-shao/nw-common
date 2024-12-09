package com.normalworks.common.cloud.aws.client;

import java.io.File;

/**
 * AwsOcrClient
 * 亚马逊OCR客户端
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/3/9 11:42 AM
 */
public interface AwsOcrClient {

    /**
     * OCR识别单张文件
     * 文件类型 PDF JPG PNG
     *
     * @param file 文件
     * @return 识别出来的文字内容
     */
    String ocrFileWithOnePage(File file);
}
