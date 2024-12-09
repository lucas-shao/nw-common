create TABLE ***_chat_message (
    message_id BIGINT AUTO_INCREMENT,
    request_id VARCHAR(64) not null ,
    sender_id VARCHAR(64) not null,
    receiver_id VARCHAR(64) not null,
    text_content VARCHAR(64),
    topic VARCHAR(64) not null,
    send_time TIMESTAMP(3) not null,
    read_time TIMESTAMP(3),
    create_time TIMESTAMP(3) not null,
    modify_time TIMESTAMP(3) not null,
    PRIMARY KEY (message_id),
    unique index idx_request_id(request_id),
    index idx_sender_id(sender_id),
    index idx_receiver_id(receiver_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;