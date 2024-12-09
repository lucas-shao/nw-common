package com.normalworks.common.medium.model;


import com.normalworks.common.medium.enums.MediumTypeEnum;

/**
 * File
 * 文件类型
 *
 * @author: lingeng
 * @date: 7/21/22
 */
public class File extends Medium {

    /**
     * 原始文件的URL地址
     */
    private String fileUrl;

    public File() {
        super();
        this.setMediumType(MediumTypeEnum.FILE);
    }

    public File(String mediumId) {
        super();
        this.setMediumId(mediumId);
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
