package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PdfUtil
 * PDF工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/3/9 2:47 PM
 */
public class PdfUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * 将PDF文件按照每一页拆分为多个文件
     *
     * @param pdfFile 待拆分的PDF
     * @return 拆分出来的每一页PDF
     */
    public static List<File> split(File pdfFile) {

        if (null == pdfFile) {
            return Collections.emptyList();
        }

        try {
            PDDocument document = PDDocument.load(pdfFile);
            List<File> splitFileList = new ArrayList<>();

            // Split the document into separate pages
            int pageCount = document.getNumberOfPages();

            for (int i = 0; i < pageCount; i++) {
                // Get the i-th page
                PDPage page = document.getPage(i);

                // Create a new document with the page
                PDDocument newDocument = new PDDocument();
                newDocument.addPage(page);

                // Save the new document as a separate PDF file
                String pdfFilePath = pdfFile.getAbsolutePath();
                File splitFile = new File(StringUtils.substringBeforeLast(pdfFilePath, ".") + "-" + (i + 1) + ".pdf");
                newDocument.save(splitFile);
                newDocument.close();

                // Add splitFile to List
                splitFileList.add(splitFile);

                LOGGER.info("拆分成功PDF page" + i + ":" + splitFile.getAbsolutePath());
            }

            return splitFileList;

        } catch (Exception exception) {
            LOGGER.error("将PDF文件按照每一页拆分为多个文件系统错误 pdfFile = " + pdfFile.getAbsolutePath(), exception);
            throw new AssertionException(CommonResultCode.SYSTEM_EXCEPTION, "将PDF文件按照每一页拆分为多个文件系统错误");
        }
    }

    /**
     * 将PNG图片转换为PDF
     */
    public static void drawPng2Pdf(String pngFilePath, String pdfFilePath) {

        try {
            // 加载PNG图片
            BufferedImage image = ImageIO.read(new File(pngFilePath));

            // 按照比例计算对应的尺寸
            int pngTotalHeight = image.getHeight();
            // 按照PDF比例计算，每一页PDF上的图片高度
            float pngPageHeight = image.getWidth() * (PDRectangle.A4.getHeight() / PDRectangle.A4.getWidth());

            // 计算所需要的PDF的页数
            int pdfPageCount = (int) Math.ceil((double) pngTotalHeight / pngPageHeight);

            // 创建一个新的PDF文档
            PDDocument document = new PDDocument();

            // 创建PDF页面
            for (int i = 0; i < pdfPageCount; i++) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
            }

            // 开始将PNG图片绘制到PDF上
            for (int i = 0; i < pdfPageCount; i++) {

                // 首先按比例裁剪每一页应该绘制的PNG图片
                String croppedImagePath = FilenameUtils.normalize(pngFilePath).replace(".png", "-" + (i + 1) + ".png");

                // 计算裁剪区域
                int x = 0;
                int y = (int) (i * pngPageHeight);
                float height = (y + pngPageHeight) > pngTotalHeight ? (pngTotalHeight - y) : pngPageHeight;

                // 裁剪PNG图片
                ImageUtil.cropPng(pngFilePath, croppedImagePath, image.getWidth(), (int) height, x, y);

                // 绘制PNG图片到PDF上
                PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(i));
                PDImageXObject imageXObject = PDImageXObject.createFromFile(croppedImagePath, document);
                contentStream.drawImage(imageXObject, 0, PDRectangle.A4.getHeight() - (height / pngPageHeight) * PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth(), (height / pngPageHeight) * PDRectangle.A4.getHeight());

                // 关闭页面内容流
                contentStream.close();

                // 删除裁剪后的PNG图片
                FileUtils.deleteQuietly(new File(croppedImagePath));
            }

            // 保存PDF文件
            document.save(pdfFilePath);

            // 关闭PDF文档
            document.close();

        } catch (Exception e) {
            LOGGER.error("图片转PDF失败！pngFilePath = " + pngFilePath + "，pdfFilePath = " + pdfFilePath, e);
            throw new AssertionException(CommonResultCode.PDF_PROCESS_FAILED, "图片转PDF失败！");
        }
    }

    /**
     * 将PDF的某一页转换为PNG图片
     *
     * @param pdfFilePath PDF文件路径
     * @param pdfPage     PDF页数，从1开始计数
     * @param pngFilePath PNG文件路径
     */
    public static void drawPdfPage2Png(String pdfFilePath, int pdfPage, String pngFilePath) {

        try {
            // 加载PDF文档
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            // 获取PDF文档的页数
            int pageCount = document.getNumberOfPages();

            // 获取PDF文档的指定页
            if (pdfPage > pageCount) {
                throw new AssertionException(CommonResultCode.PDF_PROCESS_FAILED, "PDF文档的页数不足");
            }

            // Create a PDF renderer
            PDFRenderer renderer = new PDFRenderer(document);

            // Render the second page of the PDF as an image
            BufferedImage image = renderer.renderImageWithDPI(pdfPage - 1, 300, ImageType.RGB);

            // 保存PNG图片
            ImageIO.write(image, "png", new File(pngFilePath));

            // 关闭PDF文档
            document.close();

        } catch (Exception e) {
            LOGGER.error("PDF转图片失败！pdfFilePath = " + pdfFilePath + "，pdfPage = " + pdfPage + "，pngFilePath = " + pngFilePath, e);
            throw new AssertionException(CommonResultCode.PDF_PROCESS_FAILED, "PDF转图片失败！");
        }
    }

    public static void main(String[] args) {

        String pdfFilePath = "/Users/shaoshuai.shao/Desktop/402c3c8c-81c5-426d-8ef0-79dd721c11af.pdf";
        String pngFilePath = "/Users/shaoshuai.shao/Desktop/html-1.png";
        drawPdfPage2Png(pdfFilePath, 1, pngFilePath);
    }
}
