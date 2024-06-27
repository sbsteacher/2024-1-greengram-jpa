
CREATE TABLE IF NOT EXISTS `user` (
    `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `uid` varchar(20) NOT NULL,
    `upw` varchar(100) NOT NULL,
    `nm` varchar(20) NOT NULL,
    `pic` varchar(70) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uid` (`uid`)
);
TRUNCATE TABLE user;

CREATE TABLE IF NOT EXISTS `user_follow` (
    `from_user_id` bigint(20) NOT NULL,
    `to_user_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`from_user_id`,`to_user_id`)
);
TRUNCATE TABLE user_follow;

CREATE TABLE IF NOT EXISTS `feed` (
    `feed_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `writer_id` bigint(20) NOT NULL,
    `contents` varchar(1000) DEFAULT NULL,
    `location` varchar(30) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`feed_id`)
);
TRUNCATE TABLE feed;


CREATE TABLE IF NOT EXISTS `feed_comment` (
    `feed_comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `feed_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `COMMENT` varchar(1000) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`feed_comment_id`)
);
TRUNCATE TABLE feed_comment;

CREATE TABLE IF NOT EXISTS `feed_pics` (
    `feed_pic_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `feed_id` bigint(20) NOT NULL,
    `pic` varchar(70) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`feed_pic_id`)
);
TRUNCATE TABLE feed_pics;

CREATE TABLE IF NOT EXISTS `feed_favorite` (
    `feed_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`feed_id`,`user_id`)
    );
TRUNCATE TABLE feed_favorite;
