CREATE TABLE `retry_task`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `retry_task_code`   varchar(64)  NOT NULL,
    `business_id`       varchar(128) NOT NULL,
    `retry_times`       int(11) NOT NULL,
    `ext_info`          varchar(1024) DEFAULT NULL,
    `next_execute_time` timestamp(3) NOT NULL,
    `create_time`       timestamp(3) NOT NULL,
    `modify_time`       timestamp(3) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_business_id` (`business_id`,`retry_task_code`) USING BTREE,
    KEY                 `idx_next_execute_time` (`next_execute_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;