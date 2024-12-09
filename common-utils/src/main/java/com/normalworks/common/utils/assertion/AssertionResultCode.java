package com.normalworks.common.utils.assertion;

/**
 * AssertionResultCode 结果码接口
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年10月29日 11:03 下午
 */
public interface AssertionResultCode {

    /**
     * 获取结果码对应的Code
     *
     * @return 结果码
     */
    String getResultCode();

    /**
     * 获取结果码级别
     */
    String getErrorLevel();

    /**
     * 获取结果码对应的描述信息
     *
     * @return 描述信息
     */
    String getResultMsg();

    /**
     * 获取结果码对应的Http响应状态
     *
     * @return
     */
    int getHttpStatus();
}
