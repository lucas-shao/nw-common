package com.normalworks.common.login.test;

import com.normalworks.common.login.utils.JwtUtil;
import org.junit.Test;

public class JwtUtilTest {

    @Test
    public void test1() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDIwMjQwNDAxMDQ5ODE4LFBBUlRORVIiLCJleHAiOjE3MjEzMDk5MDV9.d1F4JY615qX-CW6BCeBHQ5jLATn6Qla84-7Mozcg9RFiPLGD2Er2rYRs98wp-msBG6_5--1zcz4xWjd4zZpM-g";
        String subjectWithoutValidate = JwtUtil.getSubjectWithoutValidate(token);
        System.out.println(subjectWithoutValidate);
    }
}
