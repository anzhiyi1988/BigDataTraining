CREATE DATABASE IF NOT EXISTS `aura`;

USE `aura`;

SET NAMES utf8;

CREATE TABLE IF NOT EXISTS `sparkcore_dimension_data` (
  `dimeid` INT(11)             NOT NULL,
  `day`    DATE                NOT NULL,
  `pv`     INT(11) UNSIGNED    NOT NULL,
  `uv`     INT(11) UNSIGNED    NOT NULL,
  `ip`     INT(11) UNSIGNED    NOT NULL,
  `time`   BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`dimeid`, `day`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS `sparkcore_content_data` (
  `contentid` BIGINT(20)       NOT NULL,
  `day`       DATE             NOT NULL,
  `pv`        INT(11) UNSIGNED NOT NULL,
  `uv`        INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`contentid`, `day`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS `sparkcore_content_detail` (
  `contentid` BIGINT(20)   NOT NULL,
  `url`       VARCHAR(512) NOT NULL,
  `title`     VARCHAR(512) NOT NULL,
  PRIMARY KEY (`contentid`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `streaming_dimension_data` (
  `dimeid` INT(11)          NOT NULL,
  `second` INT(11)          NOT NULL,
  `pv`     INT(11) UNSIGNED NOT NULL,
  `uv`     INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`dimeid`, `second`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS `streaming_content_data` (
  `contentid` BIGINT(20)       NOT NULL,
  `second`    INT(11)          NOT NULL,
  `pv`        INT(11) UNSIGNED NOT NULL,
  `uv`        INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`contentid`, `second`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS `streaming_content_detail` (
  `contentid` BIGINT(20)   NOT NULL,
  `url`       VARCHAR(512) NOT NULL,
  `title`     VARCHAR(512) NOT NULL,
  PRIMARY KEY (`contentid`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `common_gender` (
  `id`    INT(11)     NOT NULL,
  `value` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `common_channel` (
  `id`    INT(11)     NOT NULL,
  `value` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS `common_dimension` (
  `id`    INT(11)     NOT NULL,
  `type`  VARCHAR(64) NOT NULL,
  `value` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS `mllib_gender_data` (
  `genderid` INT(11)          NOT NULL,
  `day`      DATE             NOT NULL,
  `pv`       INT(11) UNSIGNED NOT NULL,
  `uv`       INT(11) UNSIGNED NOT NULL,
  `ip`       INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`genderid`, `day`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS `mllib_channel_data` (
  `channelid` INT(11)          NOT NULL,
  `day`       DATE             NOT NULL,
  `pv`        INT(11) UNSIGNED NOT NULL,
  `uv`        INT(11) UNSIGNED NOT NULL,
  `ip`        INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`channelid`, `day`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = latin1;