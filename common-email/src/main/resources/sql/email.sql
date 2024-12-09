CREATE TABLE `email_msg_detail`
(
    `email_msg_id`           varchar(128) NOT NULL,
    `subject`                varchar(256)  DEFAULT NULL,
    `text_content`           varchar(8192) DEFAULT NULL,
    `from_address`           varchar(64)  NOT NULL,
    `to_address`             varchar(64)  NOT NULL,
    `sent_time`              timestamp(3) NOT NULL,
    `received_time`          timestamp(3) NOT NULL,
    `attachment_medium_list` varchar(1024) DEFAULT NULL,
    `inline_medium_list`     varchar(1024) DEFAULT NULL,
    `create_time`            timestamp(3) NOT NULL,
    `modify_time`            timestamp(3) NOT NULL,
    PRIMARY KEY (`email_msg_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
;