CREATE TABLE `sequence`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `sequence_name`   varchar(64)  NOT NULL,
    `current_value`   bigint(20) NOT NULL DEFAULT '0',
    `increment_value` bigint(20) NOT NULL DEFAULT '1',
    `minimum`         bigint(20) NOT NULL DEFAULT '1',
    `maximum`         bigint(20) NOT NULL DEFAULT '9223372036854775807',
    `create_time`     timestamp(3) NOT NULL,
    `modify_time`     timestamp(3) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `sequence_name` (`sequence_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;
