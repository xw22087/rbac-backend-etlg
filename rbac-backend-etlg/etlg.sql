CREATE DATABASE IF NOT EXISTS etlg;

USE etlg;

CREATE TABLE  IF NOT EXISTS  `user` (
                        `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user ID',
                        `username` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'username',
                        `user_password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user password, encoded',
                        `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `uk` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='user table';

CREATE TABLE  IF NOT EXISTS  `user_profile` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'profile ID',
                                `user_id` bigint(20) NOT NULL COMMENT 'user ID',
                                `save` json DEFAULT NULL COMMENT 'player data',
                                `avatar` int(11) DEFAULT NULL COMMENT 'avatar Id',
                                `nick_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'nick name',
                                `player_score` int(11) DEFAULT NULL COMMENT 'player score',
                                `achievement` int(11) DEFAULT NULL COMMENT 'achievement points',
                                `learning_progress` int(11) DEFAULT NULL COMMENT 'completed charpters',
                                `boss1` float DEFAULT NULL,
                                `boss2` float DEFAULT NULL,
                                `boss3` float DEFAULT NULL,
                                `boss4` float DEFAULT NULL,
                                `boss5` float DEFAULT NULL,
                                `boss6` float DEFAULT NULL,
                                `boss7` float DEFAULT NULL,
                                `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
                                `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    --addons
                                `difficulty` int(11) DEFAULT NULL COMMENT 'difficulty',
                                `initial_spaceship_idx` int(11) DEFAULT NULL COMMENT 'initial spaceship idx',
                                `player_skill_data` json DEFAULT NULL COMMENT 'player skill data',
                                `player_artifacts` json DEFAULT NULL COMMENT 'player artifacts',
                                `player_modules` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'player modules',
                                `equipped_modules` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'equipped modules',
                                `player_npcs` json DEFAULT NULL COMMENT 'player npcs',
                                `player_achievement` json DEFAULT NULL COMMENT 'player achievement',
                                `battle_victory_count` int(11) DEFAULT NULL COMMENT 'battle victory count',
                                `played_tutorial_group` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'played tutorial group',
                                `chapters_save_data` json DEFAULT NULL COMMENT 'chapters save data',
                                `courses_save_data` json DEFAULT NULL COMMENT 'courses save data',
                                `domians_save_data` json DEFAULT NULL COMMENT 'domians save data',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `role` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'role ID',
                                      `role_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'role name',
                                      `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'description',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `permission` (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'permission ID',
                                            `perm_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'permission password',
                                            `type` tinyint(4) DEFAULT NULL COMMENT '0-page, 1-operation',
                                            `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'description',
                                            `path` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `role_permission` (
                                                 `role_id` bigint(20) NOT NULL,
                                                 `perm_id` bigint(20) NOT NULL COMMENT 'permission ID',
                                                 PRIMARY KEY (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_role` (
                                           `user_id` bigint(20) NOT NULL,
                                           `role_id` bigint(20) NOT NULL,
                                           PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


TRUNCATE TABLE permission;
TRUNCATE TABLE role;
TRUNCATE TABLE role_permission;
TRUNCATE TABLE user;
TRUNCATE TABLE user_profile;
TRUNCATE TABLE user_role;

INSERT INTO `user` (`username`, `user_password`) VALUES
            ('player1', '$2a$10$NklWCXXvNnRFJbkqt9Qv5.4XwrdrGgg4hmSveKeRJR0wqUjBpuQ9y'),
            ('player2', '$2a$10$tZ8FJQj5j2PP6UIejQU21.d4TsXo8ie8u9CqfFE5.IBS9Nv6YwxU.'),
            ('player3', '$2a$10$jtLamPynjxkCYm9nGfB5NOb3C8s6Ly/TwTVU99XnK.RxucjungDRe'),
            ('player4', '$2a$10$lH8qzaEeba8IS5iBmrMvQOjF6CC5NSmKuoRRmera31SKw2xHhdvR6');

INSERT INTO `role` (`role_name`, `description`) VALUES
                                                    ('Admin', 'Administrator role with full access'),
                                                    ('User', 'Standard user role with limited access');

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
                                                   (1, 1),
                                                   (2, 2);

INSERT INTO `role_permission` (`role_id`, `perm_id`)
VALUES
    (1,1001),
    (1,1002),
    (1,1003),
    (1,1004);

INSERT INTO user_profile (user_id, save, avatar, nick_name, player_score, achievement, learning_progress, boss1, boss2, boss3, boss4, boss5, boss6, boss7)
VALUES
    (1, '{"playerScore": "1000", "BossDefeatTime": "[10.5, 20.3, 15.7, 30.2, 25.1, 40.6, 35.8]", "achievementScore": "500", "learningProgress": "75"}', 1, 'Eric', 1000, 500, 75, 10.5, 20.3, 15.7, 30.2, 25.1, 40.6, 35.8),
    (2, '{"playerScore": "1500", "BossDefeatTime": "[5.5, 15.3, 10.7, 20.2, 15.1, 30.6, 25.8]", "achievementScore": "800", "learningProgress": "90"}', 2, 'Peter', 1500, 800, 90, 5.5, 15.3, 10.7, 20.2, 15.1, 30.6, 25.8),
    (3, '{"playerScore": "1200", "BossDefeatTime": "[8.5, 18.3, 13.7, 25.2, 20.1, 35.6, 30.8]", "achievementScore": "600", "learningProgress": "80"}', 3, 'John', 1200, 600, 80, 8.5, 18.3, 13.7, 25.2, 20.1, 35.6, 30.8),
    (4, '{"playerScore": "2000", "BossDefeatTime": "[15.5, 25.3, 20.7, 35.2, 30.1, 45.6, 40.8]", "achievementScore": "1000", "learningProgress": "100"}', 4, 'Simon', 2000, 1000, 100, 15.5, 25.3, 20.7, 35.2, 30.1, 45.6, 40.8);
