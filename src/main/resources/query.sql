-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.11.2-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- greengram2024_v3 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `greengram2024_v3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `greengram2024_v3`;

-- 테이블 greengram2024_v3.feed 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed` (
                                      `feed_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `writer_id` bigint(20) NOT NULL,
    `contents` varchar(1000) DEFAULT NULL,
    `location` varchar(30) DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`feed_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 greengram2024_v3.feed_comment 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed_comment` (
                                              `feed_comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `feed_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `COMMENT` varchar(1000) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`feed_comment_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 greengram2024_v3.feed_favorite 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed_favorite` (
                                               `feed_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`feed_id`,`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 greengram2024_v3.feed_pics 구조 내보내기
CREATE TABLE IF NOT EXISTS `feed_pics` (
                                           `feed_pic_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `feed_id` bigint(20) NOT NULL,
    `pic` varchar(70) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`feed_pic_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 greengram2024_v3.user 구조 내보내기
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
    ) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 greengram2024_v3.user_follow 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_follow` (
                                             `from_user_id` bigint(20) NOT NULL,
    `to_user_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`from_user_id`,`to_user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
