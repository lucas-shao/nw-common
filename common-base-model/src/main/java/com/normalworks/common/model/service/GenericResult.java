package com.normalworks.common.model.service;

/**
 * GenericResult
 * 通用返回值类型
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年10月31日 5:33 下午
 */
public class GenericResult<T> extends BaseResult {

    private static final long serialVersionUID = 5215379326479097518L;

    /**
     * result value
     **/
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
