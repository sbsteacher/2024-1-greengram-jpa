-- 쿼리문에 개행이 없어야 함 (명령어 하나는 무조건 한줄)

INSERT INTO `main_code` (`main_code_id`, `cd_name`, `description`) VALUES (1, 'role', '권한');
INSERT INTO `sub_code` (`sub_code_id`, `main_code_id`, `val`, `description`) VALUES (1, 1, 'ROLE_USER', '일반 사용자'), (2, 1, 'ROLE_ADMIN', '관리자');

INSERT INTO `user` (`user_id`, `provider_type`, `uid`, `upw`, `nm`, `pic`, `created_at`, `updated_at`) VALUES (1, 4, 'user_1', '$2a$10$y3Tk8lmRwmarg9rNpTTUr.DfneNAQ5BX0JF1BRitVVONleEZvSNDC', '일반사용자', '047c89ba-fdcd-4974-b307-4b1a29de18d0.jpg', '2024-05-03 14:35:03', '2024-07-15 12:33:58'), (2, 4, 'user_2', '$2a$10$y3Tk8lmRwmarg9rNpTTUr.DfneNAQ5BX0JF1BRitVVONleEZvSNDC', '관리자', NULL, '2024-05-07 16:41:08', '2024-07-15 12:34:00'), (3, 4, 'user_3', '$2a$10$y3Tk8lmRwmarg9rNpTTUr.DfneNAQ5BX0JF1BRitVVONleEZvSNDC', '사용자_관리자', 'd91451bb-6e7c-419b-bc7e-2cb494f453de.jfif', '2024-05-16 17:10:34', '2024-07-15 12:36:16');
INSERT INTO `user_role` (`user_id`, `role_cd`, `role`) VALUES (1, 1, 'ROLE_USER'), (2, 2, 'ROLE_ADMIN'), (3, 1, 'ROLE_USER'), (3, 2, 'ROLE_ADMIN');
