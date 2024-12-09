package com.normalworks.common.email.repository.impl;

import com.normalworks.common.email.config.EmailIntegrationConfig;
import com.normalworks.common.email.model.dal.EmailMsgDetailDO;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseRepositoryImpl
 * 仓储基础类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/2/15 4:33 PM
 */
public abstract class BaseRepositoryImpl {

    @Resource
    protected JdbcTemplate jdbcTemplate;

    @Resource
    protected EmailIntegrationConfig emailIntegrationConfig;

    /**
     * 邮件详情表列字段信息
     */
    protected static final String columnList = "email_msg_id, subject, text_content, from_address, to_address, sent_time, received_time, attachment_medium_list , inline_medium_list , email_file_medium , create_time, modify_time";

    protected EmailMsgDetailDO extractSingleEmailMsgDetailRowResult(ResultSet rs) throws SQLException {

        if (rs.getFetchSize() > 1) {
            throw new RuntimeException("expect 1 result, actual is " + rs.getFetchSize());
        }

        while (rs.next()) {
            return convertRowToEmailMsgDetail(rs);
        }

        return null;
    }

    protected List<EmailMsgDetailDO> extractMultiEmailMsgDetailRowResult(ResultSet rs) throws SQLException {

        List<EmailMsgDetailDO> emailMsgDetailDOS = new ArrayList<>();
        while (rs.next()) {
            EmailMsgDetailDO emailMsgDetailDO = convertRowToEmailMsgDetail(rs);
            emailMsgDetailDOS.add(emailMsgDetailDO);
        }

        return emailMsgDetailDOS;
    }

    private EmailMsgDetailDO convertRowToEmailMsgDetail(ResultSet rs) throws SQLException {

        EmailMsgDetailDO emailMsgDetailDO = new EmailMsgDetailDO();
        emailMsgDetailDO.setEmailMsgId(rs.getString("email_msg_id"));
        emailMsgDetailDO.setSubject(rs.getString("subject"));
        emailMsgDetailDO.setTextContent(rs.getString("text_content"));
        emailMsgDetailDO.setFromAddress(rs.getString("from_address"));
        emailMsgDetailDO.setToAddress(rs.getString("to_address"));
        emailMsgDetailDO.setSentTime(rs.getTimestamp("sent_time"));
        emailMsgDetailDO.setReceivedTime(rs.getTimestamp("received_time"));
        emailMsgDetailDO.setAttachmentMediumList(rs.getString("attachment_medium_list"));
        emailMsgDetailDO.setInlineMediumList(rs.getString("inline_medium_list"));
        emailMsgDetailDO.setEmailFileMedium(rs.getString("email_file_medium"));
        emailMsgDetailDO.setCreateTime(rs.getTimestamp("create_time"));
        emailMsgDetailDO.setModifyTime(rs.getTimestamp("modify_time"));
        return emailMsgDetailDO;
    }
}
