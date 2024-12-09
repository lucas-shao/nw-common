package com.normalworks.common.service.sso.google.client.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.normalworks.common.service.sso.google.model.GoogleAccountProfileInfo;
import com.normalworks.common.service.sso.google.client.GoogleSsoClient;
import com.normalworks.common.service.sso.google.config.GoogleSsoConfig;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * GoogleSsoClientImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/3 4:53 PM
 */
@Service
public class GoogleSsoClientImpl implements GoogleSsoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSsoClientImpl.class);

    @Resource
    private GoogleSsoConfig googleSsoConfig;

    @Override
    public GoogleAccountProfileInfo obtainProfileInfo(String googleIdToken) {

        try {

            GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier
                    .Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleSsoConfig.getClientId()))
                    .build();

            GoogleIdToken idToken = googleIdTokenVerifier.verify(googleIdToken);

            if (idToken != null) {
                GoogleAccountProfileInfo googleAccountProfileInfo = new GoogleAccountProfileInfo();
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                // Get profile information from payload
                String email = payload.getEmail();
                // 表示这个邮箱有没有被验证是这个Google Account本人的
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String fullName = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                googleAccountProfileInfo.setUserId(userId);
                googleAccountProfileInfo.setEmail(email);
                googleAccountProfileInfo.setEmailVerified(emailVerified);
                googleAccountProfileInfo.setFullName(fullName);
                googleAccountProfileInfo.setFamilyName(familyName);
                googleAccountProfileInfo.setGivenName(givenName);
                googleAccountProfileInfo.setLocale(locale);
                googleAccountProfileInfo.setPictureUrl(pictureUrl);

                LOGGER.info("google token = " + googleIdToken + " , 解析出google的用户数据 = " + googleAccountProfileInfo);

                return googleAccountProfileInfo;

            } else {
                LOGGER.error("Google SSO Service Invalid ID token. googleIdToken =" + googleIdToken);
                throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "Google SSO Service Invalid ID token. googleIdToken =" + googleIdToken);
            }

        } catch (Exception exception) {

            LOGGER.error("Google SSO Service发生异常 googleIdToken =" + googleIdToken, exception);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "google sso service发生异常 googleIdToken =" + googleIdToken);
        }
    }

    @Override
    public GoogleAccountProfileInfo obtainProfileInfoFromFirebaseIdToken(FirebaseApp firebaseApp, String firebaseIdToken) {

        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance(firebaseApp).verifyIdToken(firebaseIdToken);

            if (firebaseToken != null) {
                GoogleAccountProfileInfo googleAccountProfileInfo = new GoogleAccountProfileInfo();

                // 表示这个邮箱有没有被验证是这个Google Account本人的
                boolean emailVerified = firebaseToken.isEmailVerified();
                String userId = firebaseToken.getUid();
                String email = firebaseToken.getEmail();
                String fullName = firebaseToken.getName();
                String pictureUrl = firebaseToken.getPicture();

                googleAccountProfileInfo.setUserId(userId);
                googleAccountProfileInfo.setEmail(email);
                googleAccountProfileInfo.setEmailVerified(emailVerified);
                googleAccountProfileInfo.setFullName(fullName);
                googleAccountProfileInfo.setPictureUrl(pictureUrl);

                LOGGER.info("fire base token = " + firebaseIdToken + " , 解析出google的用户数据 = " + googleAccountProfileInfo);

                return googleAccountProfileInfo;
            } else {
                LOGGER.error("Google SSO Service Invalid ID token. firebaseIdToken =" + firebaseIdToken);
                throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "Google SSO Service Invalid ID token. firebaseIdToken =" + firebaseIdToken);
            }

        } catch (Exception e) {
            LOGGER.error("Google SSO Service发生异常 firebaseIdToken =" + firebaseIdToken, e);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "google sso service发生异常 firebaseIdToken =" + firebaseIdToken);
        }
    }
}
