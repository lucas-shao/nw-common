package com.normalworks.common.ocr.mindee.client.impl;

import com.mindee.DocumentToParse;
import com.mindee.MindeeClient;
import com.mindee.MindeeClientInit;
import com.mindee.parsing.common.Document;
import com.mindee.parsing.invoice.InvoiceV4Inference;
import com.normalworks.common.ocr.mindee.client.MindeeOcrClient;
import com.normalworks.common.ocr.mindee.config.MindeeOcrConfig;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

/**
 * MindeeOcrClientImpl
 * README https://developers.mindee.com/docs/java-ocr-getting-started
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/3 2:45 PM
 */
@Service
public class MindeeOcrClientImpl implements MindeeOcrClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MindeeOcrClientImpl.class);

    @Resource
    private MindeeOcrConfig mindeeOcrConfig;

    private MindeeClient mindeeClient;

    @Override
    public Document<InvoiceV4Inference> parseInvoice(String fileName) {

        LOGGER.info("开始 Mindee Invoice ocr 识别 : fileName = " + fileName);

        try {
            DocumentToParse documentToParse = mindeeClient.loadDocument(new File(fileName));
            Document<InvoiceV4Inference> invoiceDocument = mindeeClient.parse(InvoiceV4Inference.class, documentToParse);

            LOGGER.info("Mindee Invoice ocr 识别成功，识别结果 ：" + invoiceDocument.toString());
            return invoiceDocument;
        } catch (Exception e) {

            LOGGER.error("调用Mindee Invoice ocr识别系统异常", e);
            throw new AssertionException(CommonResultCode.THIRD_PARTY_EXCEPTION, "调用Mindee Invoice ocr识别系统异常");
        }
    }

    @PostConstruct
    private void init() {
        // 初始化客户端
        mindeeClient = MindeeClientInit.create(mindeeOcrConfig.getApiKey());
    }
}
