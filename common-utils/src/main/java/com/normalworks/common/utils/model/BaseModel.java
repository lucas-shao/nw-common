package com.normalworks.common.utils.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * BaseModel
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月08日 9:53 上午
 */
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -5543895900327244559L;

    @Override
    public String toString() {
        //ToStringBuilder序列化生成的JSON格式的日期无法直接用Gson反序列化，所以改用Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
