package com.normalworks.common.medium.utils;

import com.alibaba.fastjson.JSON;
import com.normalworks.common.medium.enums.MediumResultCode;
import com.normalworks.common.medium.model.File;
import com.normalworks.common.medium.model.Image;
import com.normalworks.common.medium.model.Medium;
import com.normalworks.common.utils.assertion.AssertionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * MediumUtil
 *
 * @author: lingeng
 * @date: 09/01/2023
 */
public class MediumUtil {

    /**
     * 获取媒体内容的摘要信息
     * 获取内容摘要信息 + 内容大小计算
     *
     * @param inputStream 媒体内容流
     * @param medium      媒体领域模型
     * @throws Exception
     */
    public static void generateDigestAndSize(InputStream inputStream, Medium medium) {

        try {
            // 拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            long totalSize = 0;
            int length = -1;
            while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
                totalSize += length;
            }
            inputStream.close();

            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;//解释参见最下方
                if (val < 16) {
                    /**
                     * 如果小于16，那么val值的16进制形式必然为一位，
                     * 因为十进制0,1...9,10,11,12,13,14,15 对应的 16进制为 0,1...9,a,b,c,d,e,f;
                     * 此处高位补0。
                     */
                    hexValue.append("0");
                }
                //这里借助了Integer类的方法实现16进制的转换
                hexValue.append(Integer.toHexString(val));
            }
            String md5ContentDigest = hexValue.toString();

            medium.setMediumDigest(md5ContentDigest);
            medium.setMediumSize(totalSize);
        } catch (Exception e) {
            throw new AssertionException(MediumResultCode.MEDIUM_GEN_DIGEST_SIZE_FAILED, "生成图片摘要信息异常", e);
        }
    }

    /**
     * 获取媒体内容的原始URL
     */
    public static String fetchMediumOriUrl(Medium medium) {
        if (null == medium) {
            return null;
        }

        switch (medium.getMediumType()) {
            case IMAGE:
                Image image = (Image) medium;
                return image.getSourceImageUrl();
            case FILE:
                File file = (File) medium;
                return file.getFileUrl();
            default:
                return null;
        }
    }

    /**
     * 将媒体列表转化为id列表字符串
     *
     * @param mediumList 媒体列表
     * @return id列表字符串
     */
    public static String convertToMediumListString(List<? extends Medium> mediumList) {

        if (CollectionUtils.isEmpty(mediumList)) {
            return "";
        }

        List<String> mediumIdList = new ArrayList<>();

        for (Medium medium : mediumList) {
            mediumIdList.add(medium.getMediumId());
        }

        return JSON.toJSONString(mediumIdList);
    }

    public static List<Medium> convertToMediumList(List<String> mediumIdList) {

        List<Medium> mediumList = new ArrayList<>();
        if (CollectionUtils.isEmpty(mediumIdList)) {
            return mediumList;
        }

        for (String mediumId : mediumIdList) {
            Medium medium = convertToMedium(mediumId);
            mediumList.add(medium);
        }

        return mediumList;
    }

    public static Medium convertToMedium(String mediumId) {
        Medium medium = new Medium();
        medium.setMediumId(mediumId);
        return medium;
    }

    public static List<String> covertToMediumIdList(List<? extends Medium> mediumList) {

        List<String> mediumIdList = new ArrayList<>();
        if (CollectionUtils.isEmpty(mediumList)) {
            return mediumIdList;
        }

        for (Medium medium : mediumList) {
            mediumIdList.add(medium.getMediumId());
        }

        return mediumIdList;
    }


    public static List<? extends Medium> convertToMediumList(String mediumIds) {

        List<Medium> mediumList = new ArrayList<>();

        List<String> mediumIdList = JSON.parseArray(mediumIds, String.class);
        if (CollectionUtils.isEmpty(mediumIdList)) {
            return mediumList;
        }

        for (String mediumId : mediumIdList) {
            Medium medium = new Medium();
            medium.setMediumId(StringUtils.trim(mediumId));
            mediumList.add(medium);
        }

        return mediumList;
    }

    public static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
