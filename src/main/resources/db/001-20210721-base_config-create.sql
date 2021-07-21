CREATE TABLE `userdb`.`base_config` (id INT(11) auto_increment primary key,
  `name` VARCHAR(64) NOT NULL unique,
  `value` VARCHAR(256) DEFAULT NULL,
  `enable_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'enable flag');