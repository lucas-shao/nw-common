package com.normalworks.common.webencrypt.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * EncryptResponseWrapper
 * 需要加密的http响应数据包裹类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月07日 10:50 上午
 */
public class EncryptResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream output;
    private ServletOutputStream filterOutput;

    public EncryptResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (filterOutput == null) {
            filterOutput = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    output.write(b);
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                }
            };
        }

        return filterOutput;
    }

    public String getResponseData() {
        return output.toString();
    }

}
