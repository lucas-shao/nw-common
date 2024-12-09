package com.normalworks.common.email.repository;


import com.normalworks.common.email.model.EmailMessageDetail;

import java.util.List;

/**
 * EmailMessageDetailRepository
 * 邮件内容详情仓储类服务接口
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/20 8:57 PM
 */
public interface EmailMessageDetailRepository {

    /**
     * 创建邮件内容详情
     *
     * @param emailMessageDetail 邮件内容详情
     */
    void create(EmailMessageDetail emailMessageDetail);

    /**
     * 更新邮件内容详情
     *
     * @param emailMessageDetail 邮件内容详情
     */
    void update(EmailMessageDetail emailMessageDetail);

    /**
     * 通过邮件唯一ID查询邮件内容详情
     *
     * @param emailMsgId 邮件内容唯一ID
     * @return 邮件内容详情
     */
    EmailMessageDetail query(String emailMsgId);

    /**
     * 通过收件人地址查询邮件内容详情
     *
     * @param toAddress 收件人地址
     * @return 邮件内容详情列表
     */
    List<EmailMessageDetail> queryByToAddress(String toAddress);
}
