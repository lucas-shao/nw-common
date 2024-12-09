package com.normalworks.common.utils.assertion;

import java.io.Serializable;

/**
 * AssertionException 断言抛出的异常基类
 *
 * 异常需要附带的信息：
 * 1）日志信息，在校验不通过的时候打印充分的日志信息，方便排查，日志信息包含内部信息。
 * 2）返回给上游的信息：
 *   2.1 包括校验不通过的原因，这个原因应该比较具体，可以是一个code的形式，前端基于code可以配置给用户展示的错误文案。
 *   2.2 错误文案可能需要后端提供一些相关的信息。比如请求超出限额，当前允许的额度是多少，可以考虑在返回信息中附带。（或者前端通过其它接口查询获得，也是一个方案）
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年10月29日 10:54 下午
 */
public class AssertionException extends RuntimeException implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4376395243855955678L;

    private AssertionResultCode resultCode;

    /**
     * 设置错误枚举信息
     *
     * @param resultCode 错误码
     */
    public void setResultCode(AssertionResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public AssertionException(AssertionResultCode resultCode) {
        this.resultCode = resultCode;
    }


    public AssertionException(AssertionResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

    public AssertionException(AssertionResultCode resultCode, String msg, Throwable cause) {
        super(msg, cause);
        this.resultCode = resultCode;
    }

    public AssertionResultCode getResultCode() {
        return resultCode;
    }
}
