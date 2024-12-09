package com.normalworks.common.service.sso.google.model;

import com.normalworks.common.utils.model.BaseModel;

/**
 * GoogleAccountProfileInfo
 * google账号信息
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/3 8:02 PM
 */
public class GoogleAccountProfileInfo extends BaseModel {

    /**
     * google账号的userID
     */
    private String userId;

    /**
     * google账号关联的email
     */
    private String email;

    /**
     * google是否核验了该邮箱是该google account用户的
     */
    private boolean emailVerified;

    /**
     * 用户的全名
     */
    private String fullName;

    /**
     * 用户的头像url
     */
    private String pictureUrl;

    /**
     * 用户设置的locale信息
     */
    private String locale;

    /**
     * 姓
     */
    private String familyName;

    /**
     * 名
     */
    private String givenName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}
