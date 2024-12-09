package com.normalworks.common.ocr.mindee.client;

import com.mindee.parsing.common.Document;
import com.mindee.parsing.invoice.InvoiceV4Inference;

/**
 * MindeeOcrClient
 * mindee ocr 客户端
 * README https://developers.mindee.com/docs/java-ocr-getting-started
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/3 2:44 PM
 */
public interface MindeeOcrClient {

    /**
     * 读取本地文件并识别Invoice
     *
     * @param fileName 本地文件目录
     * @return Invoice识别模型
     */
    Document<InvoiceV4Inference> parseInvoice(String fileName);
}
