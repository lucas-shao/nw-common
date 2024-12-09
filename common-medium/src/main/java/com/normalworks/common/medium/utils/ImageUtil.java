package com.normalworks.common.medium.utils;

import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.medium.enums.MediumResultCode;
import com.normalworks.common.medium.model.Image;
import com.normalworks.common.utils.assertion.AssertionException;

import java.io.IOException;
import java.io.InputStream;

/**
 * ImageUtil
 *
 * @author: lingeng
 * @date: 09/01/2023
 */
public class ImageUtil {

    /**
     * 解析图片流
     * 解析图片的长，宽，图片的类型
     *
     * @param inputStream 图片流
     * @param image       图片信息
     */
    public static void processImageStream(InputStream inputStream, Image image) {

        try {
            int c1 = inputStream.read();
            int c2 = inputStream.read();
            int c3 = inputStream.read();

            Integer width = -1;
            Integer height = -1;
            HttpContentTypeEnum contentType = null;

            if (c1 == 'G' && c2 == 'I' && c3 == 'F') { // GIF
                inputStream.skip(3);
                width = readInt(inputStream, 2, false);
                height = readInt(inputStream, 2, false);
                contentType = HttpContentTypeEnum.GIF;
            } else if (c1 == 0xFF && c2 == 0xD8) { // JPG
                while (c3 == 255) {
                    int marker = inputStream.read();
                    int len = readInt(inputStream, 2, true);
                    if (marker == 192 || marker == 193 || marker == 194) {
                        inputStream.skip(1);
                        height = readInt(inputStream, 2, true);
                        width = readInt(inputStream, 2, true);
                        contentType = HttpContentTypeEnum.JPEG;
                        break;
                    }
                    inputStream.skip(len - 2);
                    c3 = inputStream.read();
                }
            } else if (c1 == 137 && c2 == 80 && c3 == 78) { // PNG
                inputStream.skip(15);
                width = readInt(inputStream, 2, true);
                inputStream.skip(2);
                height = readInt(inputStream, 2, true);
                contentType = HttpContentTypeEnum.PNG;
            } else if (c1 == 66 && c2 == 77) { // BMP
                inputStream.skip(15);
                width = readInt(inputStream, 2, false);
                inputStream.skip(2);
                height = readInt(inputStream, 2, false);
                contentType = HttpContentTypeEnum.BMP;
            } else if (c1 == 'R' && c2 == 'I' && c3 == 'F') { // WEBP
                byte[] bytes = new byte[27];
                inputStream.read(bytes);
                width = ((int) bytes[24] & 0xff) << 8 | ((int) bytes[23] & 0xff);
                height = ((int) bytes[26] & 0xff) << 8 | ((int) bytes[25] & 0xff);
                contentType = HttpContentTypeEnum.WEBP;
            } else {
                int c4 = inputStream.read();
                if ((c1 == 'M' && c2 == 'M' && c3 == 0 && c4 == 42) || (c1 == 'I' && c2 == 'I' && c3 == 42 && c4 == 0)) { //TIFF
                    boolean bigEndian = c1 == 'M';
                    int ifd = 0;
                    int entries;
                    ifd = readInt(inputStream, 4, bigEndian);
                    inputStream.skip(ifd - 8);
                    entries = readInt(inputStream, 2, bigEndian);
                    for (int i = 1; i <= entries; i++) {
                        int tag = readInt(inputStream, 2, bigEndian);
                        int fieldType = readInt(inputStream, 2, bigEndian);
                        int valOffset;
                        if ((fieldType == 3 || fieldType == 8)) {
                            valOffset = readInt(inputStream, 2, bigEndian);
                            inputStream.skip(2);
                        } else {
                            valOffset = readInt(inputStream, 4, bigEndian);
                        }
                        if (tag == 256) {
                            width = valOffset;
                        } else if (tag == 257) {
                            height = valOffset;
                        }
                        if (width != -1 && height != -1) {
                            contentType = HttpContentTypeEnum.TIFF;
                            break;
                        }
                    }
                }
            }

            image.setSourceImageWidth(width);
            image.setSourceImageHeight(height);
            image.setMediumContentType(contentType);
        } catch (IOException e) {
            throw new AssertionException(MediumResultCode.MEDIUM_GET_IMAGE_INFO_FAILED, "获取图片宽度、高度等信息失败", e);
        }
    }

    private static int readInt(InputStream is, int noOfBytes, boolean bigEndian) throws IOException {
        int ret = 0;
        int sv = bigEndian ? ((noOfBytes - 1) * 8) : 0;
        int cnt = bigEndian ? -8 : 8;
        for (int i = 0; i < noOfBytes; i++) {
            ret |= is.read() << sv;
            sv += cnt;
        }
        return ret;
    }
}
