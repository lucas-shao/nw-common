package com.normalworks.common.chat;

import com.normalworks.common.utils.ContextUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.Date;
import java.util.*;

@Service
public class ChatMessageRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private ChatConfig chatConfig;

    public List<ChatMessage> queryChatMessages(String topic, String userId1, String userId2, Date maxSendTime, long limit) {
        String sql = "select * from " + getTableName() + " where topic=? and ((sender_id=? and receiver_id=?) or (sender_id=? and receiver_id=?)) and send_time<? order by send_time desc limit ?";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<ChatMessage>>() {
            @Override
            public List<ChatMessage> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRowResult(rs);
            }
        }, topic, userId1, userId2, userId2, userId1, maxSendTime, limit);
    }

    public List<String> queryAllTopics(String userId1, String userId2) {
        String sql = "select distinct topic from " + getTableName() + " where (sender_id=? and receiver_id=?) or (sender_id=? and receiver_id=?)";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<String>>() {
            @Override
            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<String> topics = new ArrayList<>();
                while (rs.next()) {
                    topics.add(rs.getString("topic"));
                }
                return topics;
            }
        }, userId1, userId2, userId2, userId1);
    }

    public int countUnreadMessagesByReceiverIdAndTopic(String userId, String topic) {
        String sql = "select count(*) from " + getTableName() + " where receiver_id=? and topic=? and read_time is null";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, topic);
        return count == null ? 0 : count;
    }

    public Map<String, Integer> countUnreadMessagesByReceiverId(String receiverId) {
        String sql = "select topic,count(*) as count from " + getTableName() + " where receiver_id=? and read_time is null group by topic";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<String, Integer> result = new HashMap<>();
                while (rs.next()) {
                    result.put(rs.getString("topic"), rs.getInt("count"));
                }
                return result;
            }
        }, receiverId);
    }


    public List<ChatMessage> queryLatestChatMessagesByTopics(List<String> topics) {
        StringBuilder sql = new StringBuilder("SELECT n.* FROM " + getTableName() + " n INNER JOIN (SELECT TOPIC, MAX(send_time) AS send_time FROM " + getTableName() + " where topic in(");
        appendStringList(topics, sql);
        sql.append(") GROUP BY TOPIC) AS max USING (TOPIC, send_time)");

        return jdbcTemplate.query(sql.toString(), new ResultSetExtractor<List<ChatMessage>>() {
            @Override
            public List<ChatMessage> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractSingleRowResult(rs);
            }
        });
    }

    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        String sql = "insert into " + getTableName() + "(request_id,sender_id,receiver_id,text_content,topic,send_time,create_time,modify_time)" + "values(?,?,?,?,?,?,?,?)";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        /*
         * 使用这个方式来执行insert操作，主要是想要获取到自增id
         */
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, chatMessage.getRequestId());
            ps.setString(2, chatMessage.getSenderId());
            ps.setString(3, chatMessage.getReceiverId());
            ps.setString(4, chatMessage.getTextContent());
            ps.setString(5, chatMessage.getTopic());
            ps.setTimestamp(6, new Timestamp(chatMessage.getSendTime().getTime()));
            ps.setTimestamp(7, getCurrentTimestamp());
            ps.setTimestamp(8, getCurrentTimestamp());
            return ps;
        }, generatedKeyHolder);

        chatMessage.setMessageId(generatedKeyHolder.getKey().longValue());
        return chatMessage;
    }

    public int markAsRead(List<Long> messageIds) {
        StringBuilder sql = new StringBuilder("update " + getTableName() + " set read_time=?,modify_time=? where message_id in (");
        appendLongList(messageIds, sql);
        sql.append(")");
        return jdbcTemplate.update(sql.toString(), getCurrentTimestamp(), getCurrentTimestamp());
    }

    private void appendLongList(List<Long> ids, StringBuilder sql) {
        for (int i = 0; i < ids.size(); i++) {
            sql.append(ids.get(i));
            if (i < ids.size() - 1) {
                sql.append(",");
            }
        }
    }

    private static void appendStringList(List<String> ids, StringBuilder sql) {
        for (int i = 0; i < ids.size(); i++) {
            sql.append("'").append(ids.get(i)).append("'");
            if (i < ids.size() - 1) {
                sql.append(",");
            }
        }
    }

    private static Timestamp getCurrentTimestamp() {
        return new Timestamp(ContextUtil.getTime().getTime());
    }

    private List<ChatMessage> extractSingleRowResult(ResultSet rs) throws SQLException {

        List<ChatMessage> messages = new ArrayList<>();
        while (rs.next()) {

            ChatMessage record = new ChatMessage();
            record.setRequestId(rs.getString("request_id"));
            record.setMessageId(rs.getLong("message_id"));
            record.setSenderId(rs.getString("sender_id"));
            record.setReceiverId(rs.getString("receiver_id"));
            record.setTextContent(rs.getString("text_content"));
            record.setTopic(rs.getString("topic"));
            record.setSendTime(rs.getTimestamp("send_time"));
            record.setReadTime(rs.getTimestamp("read_time"));
            record.setCreateTime(rs.getTimestamp("create_time"));
            record.setModifyTime(rs.getTimestamp("modify_time"));
            messages.add(record);
        }

        return messages;
    }

    private String getTableName() {
        return chatConfig.getTablePrefix() + "chat_message";
    }

}
