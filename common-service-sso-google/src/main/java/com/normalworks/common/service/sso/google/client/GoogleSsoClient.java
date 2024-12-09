package com.normalworks.common.service.sso.google.client;

import com.google.firebase.FirebaseApp;
import com.normalworks.common.service.sso.google.model.GoogleAccountProfileInfo;

/**
 * GoogleSsoClient
 * google账号单点登录服务
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/3 8:02 PM
 */
public interface GoogleSsoClient {

    /**
     * 通过googleIdToken获取用户信息
     *
     * @param googleIdToken googleId Token
     * @return 用户信息
     */
    GoogleAccountProfileInfo obtainProfileInfo(String googleIdToken);

    /**
     * 通过FirebaseIdToken获取用户信息(主要用于APP客户端的登录)
     *
     * @param firebaseApp     FirebaseApp
     * @param firebaseIdToken FirebaseIdToken
     * @return
     */
    GoogleAccountProfileInfo obtainProfileInfoFromFirebaseIdToken(FirebaseApp firebaseApp, String firebaseIdToken);
}
