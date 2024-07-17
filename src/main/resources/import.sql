-- 쿼리문에 개행이 없어야 함 (명령어 하나는 무조건 한줄)

INSERT INTO `main_code` (`main_code_id`, `cd_name`, `description`) VALUES (1, 'role', '권한');
INSERT INTO `sub_code` (`sub_code_id`, `main_code_id`, `val`, `description`) VALUES (1, 1, 'ROLE_USER', '일반 사용자'), (2, 1, 'ROLE_ADMIN', '관리자');
