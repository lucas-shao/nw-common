CREATE TABLE `task_config`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `task_type`   varchar(64)  NOT NULL,
    `status`      varchar(64)  NOT NULL,
    `create_time` timestamp(3) NOT NULL,
    `modify_time` timestamp(3) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `task_type` (`task_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;