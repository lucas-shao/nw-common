package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * ImageUtil
 * 图像工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/12/26 3:02 PM
 */
public class ImageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 裁剪PNG图片
     *
     * @param sourcePngPath    原始图片的本地地址
     * @param croppedPngPath   裁剪后要存放的图片的本地地址
     * @param croppedPngWidth  裁剪后的图片宽度
     * @param croppedPngHeight 裁剪后的图片高度
     * @param croppedPngX      裁剪的原始图片X坐标
     * @param croppedPngY      裁剪的原始图片Y坐标
     */
    public static void cropPng(String sourcePngPath, String croppedPngPath, int croppedPngWidth, int croppedPngHeight, int croppedPngX, int croppedPngY) {

        try {
            LOGGER.info("图片裁剪开始！sourcePngPath = " + sourcePngPath + "，croppedPngPath = " + croppedPngPath + "，croppedPngWidth = " + croppedPngWidth + "，croppedPngHeight = " + croppedPngHeight + "，croppedPngX = " + croppedPngX + "，croppedPngY = " + croppedPngY);

            // 加载PNG图片
            BufferedImage image = ImageIO.read(new File(sourcePngPath));

            // 按照指定区域裁剪
            BufferedImage subImage = image.getSubimage(croppedPngX, croppedPngY, croppedPngWidth, croppedPngHeight);

            // 保存裁剪后的图片
            ImageIO.write(subImage, "png", new File(croppedPngPath));

            LOGGER.info("图片裁剪成功！sourcePngPath = " + sourcePngPath + "，croppedPngPath = " + croppedPngPath + "，croppedPngWidth = " + croppedPngWidth + "，croppedPngHeight = " + croppedPngHeight + "，croppedPngX = " + croppedPngX + "，croppedPngY = " + croppedPngY);

        } catch (Exception e) {
            LOGGER.error("图片裁剪失败！sourcePngPath = " + sourcePngPath + "，croppedPngPath = " + croppedPngPath + "，croppedPngWidth = " + croppedPngWidth + "，croppedPngHeight = " + croppedPngHeight + "，croppedPngX = " + croppedPngX + "，croppedPngY = " + croppedPngY, e);
            throw new AssertionException(CommonResultCode.IMAGE_PROCESS_FAILED, "图片裁剪失败！");
        }
    }
}
