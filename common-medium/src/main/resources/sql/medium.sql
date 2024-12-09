CREATE TABLE `medium`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `medium_type`          varchar(64)  NOT NULL,
    `medium_digest`        varchar(128) NOT NULL,
    `medium_size`          bigint(20) NOT NULL,
    `medium_status`        varchar(64)  NOT NULL,
    `medium_content_type`  varchar(256)  NOT NULL,
    `upload_user_id`       varchar(64)  NOT NULL,
    `medium_url`           varchar(1024) DEFAULT NULL,
    `image_height`         int(11) DEFAULT NULL,
    `image_width`          int(11) DEFAULT NULL,
    `standard_medium_url`  varchar(1024) DEFAULT NULL,
    `blur_medium_url`      varchar(1024) DEFAULT NULL,
    `cover_image_url`      varchar(1024) DEFAULT NULL,
    `create_time`          datetime     NOT NULL,
    `modify_time`          datetime     NOT NULL,
    `audit_failed_details` varchar(4096) DEFAULT NULL,
    `medium_duration`      bigint(20) DEFAULT NULL,
    `text_content`         mediumtext,
    `ext_info`             varchar(1024) DEFAULT NULL,
    `medium_name`          varchar(256)  DEFAULT NULL COMMENT '文件名',
    PRIMARY KEY (`id`),
    UNIQUE KEY `medium_digest` (`medium_digest`,`medium_type`,`upload_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;