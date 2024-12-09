package com.normalworks.common.webencrypt.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * StreamUtil
 * 流工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2022年04月07日 10:47 上午
 */
public class StreamUtil {

    public static final int BUFFER_SIZE = 4096;

    public static byte[] copyToByteArray(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        copy(in, out);
        return out.toByteArray();
    }

    private static int copy(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            byteCount += bytesRead;
        }
        out.flush();
        return byteCount;
    }
}
