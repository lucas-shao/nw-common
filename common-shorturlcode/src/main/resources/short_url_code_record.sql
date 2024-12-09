drop table if exists ${prefix}short_url_code_record;

create table ${prefix}short_url_code_record
(
    id          bigint(20) auto_increment not null primary key,
    long_url    varchar(512)              not null,
    short_code  varchar(16)               not null,
    create_time timestamp(3)              not null,
    modify_time timestamp(3)              not null,
    unique index uni_long_url (long_url),
    unique index short_code (short_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
;