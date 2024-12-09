CREATE TABLE `retry_task_config`
(
    `id`                           bigint(20) NOT NULL AUTO_INCREMENT,
    `retry_task_code`              varchar(64)   NOT NULL,
    `retry_task_name`              varchar(128)  NOT NULL,
    `task_execute_interval_config` varchar(1024) NOT NULL,
    `task_status`                  varchar(64)   NOT NULL,
    `task_priority`                varchar(8)    NOT NULL,
    `task_max_times_per_load`      int(8) NOT NULL,
    `create_time`                  timestamp(3)  NOT NULL,
    `modify_time`                  timestamp(3)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `retry_task_code` (`retry_task_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;