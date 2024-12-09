package com.normalworks.common.email.repository.impl;

import com.normalworks.common.email.converter.EmailMessageDetailConverter;
import com.normalworks.common.email.model.EmailMessageDetail;
import com.normalworks.common.email.model.dal.EmailMsgDetailDO;
import com.normalworks.common.email.repository.EmailMessageDetailRepository;
import com.normalworks.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * EmailMessageDetailRepositoryImpl
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2023/12/29 4:36 PM
 */
@Service
public class EmailMessageDetailRepositoryImpl extends BaseRepositoryImpl implements EmailMessageDetailRepository {

    private static final String EMAIL_MESSAGE_DETAIL_TABLE_NAME = "email_msg_detail";

    @Override
    public void create(EmailMessageDetail emailMessageDetail) {

        // 数据组装
        emailMessageDetail.setCreateTime(DateUtil.curr());
        emailMessageDetail.setModifyTime(DateUtil.curr());
        EmailMsgDetailDO emailMsgDetailDO = EmailMessageDetailConverter.toDO(emailMessageDetail);

        String emailMessageDetailTableName = emailIntegrationConfig.getTablePrefix() + EMAIL_MESSAGE_DETAIL_TABLE_NAME;
        String insertSql = "insert into " + emailMessageDetailTableName + " (" + columnList + ") " + "values (?,?,?,?,?,?,?,?,?,?,?,?)";

        // 执行插入操作
        jdbcTemplate.update(insertSql, ps -> {
            ps.setString(1, emailMsgDetailDO.getEmailMsgId());
            ps.setString(2, emailMsgDetailDO.getSubject());
            ps.setString(3, emailMsgDetailDO.getTextContent());
            ps.setString(4, emailMsgDetailDO.getFromAddress());
            ps.setString(5, emailMsgDetailDO.getToAddress());
            ps.setTimestamp(6, new Timestamp(emailMsgDetailDO.getSentTime().getTime()));
            ps.setTimestamp(7, new Timestamp(emailMsgDetailDO.getReceivedTime().getTime()));
            ps.setString(8, emailMsgDetailDO.getAttachmentMediumList());
            ps.setString(9, emailMsgDetailDO.getInlineMediumList());
            ps.setString(10, emailMsgDetailDO.getEmailFileMedium());
            ps.setTimestamp(11, new Timestamp(emailMsgDetailDO.getCreateTime().getTime()));
            ps.setTimestamp(12, new Timestamp(emailMsgDetailDO.getModifyTime().getTime()));
        });

    }

    @Override
    public void update(EmailMessageDetail emailMessageDetail) {

        // 数据组装
        emailMessageDetail.setModifyTime(DateUtil.curr());
        EmailMsgDetailDO emailMsgDetailDO = EmailMessageDetailConverter.toDO(emailMessageDetail);

        String emailMessageDetailTableName = emailIntegrationConfig.getTablePrefix() + EMAIL_MESSAGE_DETAIL_TABLE_NAME;
        String updateSql = "update " + emailMessageDetailTableName + " set subject = ?, text_content = ?, from_address = ?, to_address = ?, sent_time = ?, received_time = ?, attachment_medium_list = ?, inline_medium_list = ?, email_file_medium = ?, modify_time = ? where email_msg_id = ?";

        // 执行更新操作
        jdbcTemplate.update(updateSql, ps -> {
            ps.setString(1, emailMsgDetailDO.getSubject());
            ps.setString(2, emailMsgDetailDO.getTextContent());
            ps.setString(3, emailMsgDetailDO.getFromAddress());
            ps.setString(4, emailMsgDetailDO.getToAddress());
            ps.setTimestamp(5, new Timestamp(emailMsgDetailDO.getSentTime().getTime()));
            ps.setTimestamp(6, new Timestamp(emailMsgDetailDO.getReceivedTime().getTime()));
            ps.setString(7, emailMsgDetailDO.getAttachmentMediumList());
            ps.setString(8, emailMsgDetailDO.getInlineMediumList());
            ps.setString(9, emailMsgDetailDO.getEmailFileMedium());
            ps.setTimestamp(10, new Timestamp(emailMsgDetailDO.getModifyTime().getTime()));
            ps.setString(11, emailMsgDetailDO.getEmailMsgId());
        });
    }

    @Override
    public EmailMessageDetail query(String emailMsgId) {

        String emailMessageDetailTableName = emailIntegrationConfig.getTablePrefix() + EMAIL_MESSAGE_DETAIL_TABLE_NAME;

        String querySql = "select " + columnList + " from " + emailMessageDetailTableName + " where email_msg_id = ?";

        EmailMsgDetailDO emailMsgDetailDO = jdbcTemplate.query(querySql, rs -> {
            return extractSingleEmailMsgDetailRowResult(rs);
        }, emailMsgId);

        return EmailMessageDetailConverter.toModel(emailMsgDetailDO);
    }

    @Override
    public List<EmailMessageDetail> queryByToAddress(String toAddress) {

        String emailMessageDetailTableName = emailIntegrationConfig.getTablePrefix() + EMAIL_MESSAGE_DETAIL_TABLE_NAME;

        String querySql = "select " + columnList + " from " + emailMessageDetailTableName + " where to_address = ?";

        List<EmailMsgDetailDO> emailMsgDetailDOList = jdbcTemplate.query(querySql, rs -> {
            return extractMultiEmailMsgDetailRowResult(rs);
        }, toAddress);

        return EmailMessageDetailConverter.batchToModel(emailMsgDetailDOList);
    }
}
