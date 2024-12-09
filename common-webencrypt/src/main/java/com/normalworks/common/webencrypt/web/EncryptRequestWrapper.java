package com.normalworks.common.webencrypt.web;

import com.normalworks.common.webencrypt.util.StreamUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * EncryptRequestWrapper
 * 需要解密的http请求包裹类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月07日 10:46 上午
 */
public class EncryptRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody = new byte[0];

    public EncryptRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestBody = StreamUtil.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }

    public String getRequestData() {
        return new String(requestBody);
    }

    public void setRequestData(String requestData) {
        this.requestBody = requestData.getBytes();
    }
}
