package com.normalworks.common.chat;

import com.normalworks.common.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Resource
    private ChatMessageRepository chatMessageRepository;

    /**
     * 查询聊天记录
     */
    public List<ChatMessage> queryChatMessage(String topic, String userId1, String userId2, Date maxSendTime, long limit) {
        AssertUtil.notBlank(topic, ChatResultCode.TOPIC_IS_BLANK);
        AssertUtil.notBlank(userId1, ChatResultCode.USER_ID_1_IS_BLANK);
        AssertUtil.notBlank(userId2, ChatResultCode.USER_ID_2_IS_BLANK);
        AssertUtil.notNull(maxSendTime, ChatResultCode.MAX_SEND_TIME_IS_NULL);
        AssertUtil.isTrue(limit > 0, ChatResultCode.LIMIT_IS_ILLEGAL);
        return chatMessageRepository.queryChatMessages(topic, userId1, userId2, maxSendTime, limit);
    }

    /**
     * 查询两个用户之间的所有话题
     */
    public List<String> queryAllTopics(String userId1, String userId2) {
        AssertUtil.notBlank(userId1, ChatResultCode.USER_ID_1_IS_BLANK);
        AssertUtil.notBlank(userId2, ChatResultCode.USER_ID_2_IS_BLANK);
        return chatMessageRepository.queryAllTopics(userId1, userId2);
    }

    /**
     * 发送聊天消息
     */
    public ChatMessage sendChatMessage(ChatMessage chatMessage) {
        AssertUtil.notNull(chatMessage, ChatResultCode.MESSAGE_IS_NULL);
        AssertUtil.isNull(chatMessage.getMessageId(), ChatResultCode.MESSAGE_ID_IS_NOT_NULL);
        AssertUtil.notBlank(chatMessage.getRequestId(), ChatResultCode.REQUEST_ID_IS_BLANK);
        AssertUtil.notBlank(chatMessage.getSenderId(), ChatResultCode.SENDER_ID_IS_BLANK);
        AssertUtil.notBlank(chatMessage.getReceiverId(), ChatResultCode.RECEIVER_ID_IS_BLANK);
        AssertUtil.notBlank(chatMessage.getTopic(), ChatResultCode.TOPIC_IS_BLANK);
        AssertUtil.isNull(chatMessage.getReadTime(), ChatResultCode.READ_TIME_IS_NOT_NULL);
        return chatMessageRepository.createChatMessage(chatMessage);
    }

    public int markAsRead(List<ChatMessage> messages) {
        AssertUtil.notEmpty(messages, ChatResultCode.MESSAGES_IS_EMPTY);
        List<Long> messageIds = getUnreadMessageIds(messages);
        if (messageIds.isEmpty()) {
            return 0;
        } else {
            return chatMessageRepository.markAsRead(messageIds);
        }
    }

    public int countUnreadMessages(String receiverId, String topic) {
        return chatMessageRepository.countUnreadMessagesByReceiverIdAndTopic(receiverId, topic);
    }

    public Map<String, Integer> countUnreadMessages(String receiverId) {
        return chatMessageRepository.countUnreadMessagesByReceiverId(receiverId);
    }

    public List<ChatMessage> queryLatestChatMessagesByTopics(List<String> topics) {
        return chatMessageRepository.queryLatestChatMessagesByTopics(topics);
    }

    private static List<Long> getUnreadMessageIds(List<ChatMessage> messages) {
        List<Long> messageIds = new ArrayList<>();
        for (ChatMessage message : messages) {
            if (message.getReadTime() == null) {
                messageIds.add(message.getMessageId());
            }
        }
        return messageIds;
    }

}
