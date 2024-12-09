package com.normalworks.common.medium.model;


import com.normalworks.common.medium.enums.MediumTypeEnum;

/**
 * Image
 * 图片媒体
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月19日 6:43 下午
 */
public class Image extends Medium {

    /**
     * 原始图片的URL地址
     */
    private String sourceImageUrl;

    /**
     * 原始图片内容高度
     */
    private Integer sourceImageHeight;

    /**
     * 原始图片内容宽度
     */
    private Integer sourceImageWidth;

    /**
     * 标准图片的URL地址
     */
    private String standardImageUrl;

    /**
     * 模糊图片的URL地址
     */
    private String blurImageUrl;

    /**
     * 构造函数
     */
    public Image() {
        super();
        this.setMediumType(MediumTypeEnum.IMAGE);
    }

    /**
     * 构造函数
     *
     * @param ImageId 图片ID
     */
    public Image(String ImageId) {
        super();
        this.setMediumType(MediumTypeEnum.IMAGE);
        this.setMediumId(ImageId);
    }

    public String getSourceImageUrl() {
        return sourceImageUrl;
    }

    public void setSourceImageUrl(String sourceImageUrl) {
        this.sourceImageUrl = sourceImageUrl;
    }

    public Integer getSourceImageHeight() {
        return sourceImageHeight;
    }

    public void setSourceImageHeight(Integer sourceImageHeight) {
        this.sourceImageHeight = sourceImageHeight;
    }

    public Integer getSourceImageWidth() {
        return sourceImageWidth;
    }

    public void setSourceImageWidth(Integer sourceImageWidth) {
        this.sourceImageWidth = sourceImageWidth;
    }

    public String getStandardImageUrl() {
        return standardImageUrl;
    }

    public void setStandardImageUrl(String standardImageUrl) {
        this.standardImageUrl = standardImageUrl;
    }

    public String getBlurImageUrl() {
        return blurImageUrl;
    }

    public void setBlurImageUrl(String blurImageUrl) {
        this.blurImageUrl = blurImageUrl;
    }
}
