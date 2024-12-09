package com.normalworks.common.utils;

import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * OcrUtil
 * OCR工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2024/1/16 3:34 PM
 */
public class OcrUtil {

    /**
     * PINVO支持OCR的图片类型
     */
    public static final List<HttpContentTypeEnum> SUPPORT_OCR_IMAGE_HTTP_CONTENT_TYPE_LIST = Arrays.asList(
            HttpContentTypeEnum.PNG, HttpContentTypeEnum.X_PNG,
            HttpContentTypeEnum.JPEG, HttpContentTypeEnum.JPG, HttpContentTypeEnum.X_JPG,
            HttpContentTypeEnum.TIFF,
            HttpContentTypeEnum.WEBP,
            HttpContentTypeEnum.BMP, HttpContentTypeEnum.X_BMP, HttpContentTypeEnum.X_MS_BMP,
            HttpContentTypeEnum.HEIC, HttpContentTypeEnum.HEIF
    );

    /**
     * PINVO支持OCR的文件类型
     */
    public static final List<HttpContentTypeEnum> SUPPORT_OCR_FILE_HTTP_CONTENT_TYPE_LIST = Arrays.asList(
            HttpContentTypeEnum.PDF
    );

    /**
     * 多模态大模型原生支持OCR的类型
     * 比如OPENAI原生支持OCR的图片类型 PNG/JPEG/PDF
     */
    public static final List<HttpContentTypeEnum> SUPPORT_OCR_RAW_HTTP_CONTENT_TYPE_LIST = Arrays.asList(
            HttpContentTypeEnum.PNG, HttpContentTypeEnum.X_PNG,
            HttpContentTypeEnum.JPEG, HttpContentTypeEnum.JPG, HttpContentTypeEnum.X_JPG,
            HttpContentTypeEnum.PDF
    );

    /**
     * 判断该媒体类型是否支持OCR
     * 有些图片类型可以转化为PNG再做OCR
     */
    public static boolean isSupportOcr(HttpContentTypeEnum httpContentType) {
        if (SUPPORT_OCR_IMAGE_HTTP_CONTENT_TYPE_LIST.contains(httpContentType))
            return true;
        else if (SUPPORT_OCR_FILE_HTTP_CONTENT_TYPE_LIST.contains(httpContentType))
            return true;
        else
            return false;
    }

    /**
     * 是否是多模态大模型原生支持OCR的类型
     * 比如OPENAI原生支持OCR的图片类型 PNG/JPEG/PDF
     *
     * @param httpContentType
     * @return
     */
    public static boolean isRawSupportOcr(HttpContentTypeEnum httpContentType) {
        if (SUPPORT_OCR_RAW_HTTP_CONTENT_TYPE_LIST.contains(httpContentType))
            return true;
        else
            return false;
    }

    /**
     * 判断URL是否是多模态大模型原生支持OCR的类型
     *
     * @param url
     * @return
     */
    public static boolean isRawSupportOcr(String url) {
        String fileSuffix = StringUtils.substringAfterLast(StringUtils.lowerCase(url), ".");
        if (StringUtils.isBlank(fileSuffix)) {
            return false;
        }
        for (HttpContentTypeEnum httpContentTypeEnum : SUPPORT_OCR_RAW_HTTP_CONTENT_TYPE_LIST) {
            if (StringUtils.equalsIgnoreCase(fileSuffix, httpContentTypeEnum.getExtensionNameWithoutDot())) {
                return true;
            }
        }
        return false;
    }
}
