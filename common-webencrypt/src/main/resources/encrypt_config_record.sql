CREATE TABLE `encrypt_config_record`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `client_ip`        varchar(64)   NOT NULL,
    `rsa_key_id`       varchar(64)   NOT NULL,
    `rsa_public_key`   varchar(256)  NOT NULL,
    `rsa_private_key`  varchar(1024) NOT NULL,
    `rsa_invalid_time` timestamp(3)  NOT NULL,
    `aes_key_id`       varchar(64)  DEFAULT NULL,
    `aes_key`          varchar(64)  DEFAULT NULL,
    `aes_invalid_time` timestamp(3) DEFAULT NULL,
    `create_time`      timestamp(3)  NOT NULL,
    `modify_time`      timestamp(3)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `rsa_key_id` (`rsa_key_id`),
    UNIQUE KEY `aes_key_id` (`aes_key_id`),
    KEY                `index_client_ip` (`client_ip`) USING BTREE
);