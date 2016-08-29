--DROP TABLE  IF EXISTS `testdb`.`authorities`;

CREATE TABLE IF NOT EXISTS `testdb`.`authorities` (
  `id` BIGINT(20) NOT NULL,
  `user_role` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;



INSERT INTO `authorities`  (id, user_role)
  SELECT 1, 'ROLE_USE' FROM DUAL
WHERE NOT EXISTS
  (SELECT user_role FROM authorities WHERE user_role='ROLE_USE');


INSERT INTO `authorities`  (id, user_role)
  SELECT 2, 'ROLE_ADMIN' FROM DUAL
WHERE NOT EXISTS
  (SELECT user_role FROM authorities WHERE user_role='ROLE_ADMIN');


INSERT INTO `authorities`  (id, user_role)
  SELECT 3, 'ROLE_SUPER_ADMIN' FROM DUAL
WHERE NOT EXISTS
  (SELECT user_role FROM authorities WHERE user_role='ROLE_SUPER_ADMIN');



--INSERT INTO `authorities` (id, is_locked, user_role) VALUES (1, FALSE, 'ROLE_USE');
--INSERT INTO `authorities` (id, is_locked, user_role) VALUES (2, FALSE, 'ROLE_ADMIN');
--INSERT INTO `authorities` (id, is_locked, user_role) VALUES (3, FALSE, 'ROLE_SUPER_ADMIN');

--DROP TABLE IF EXISTS `testdb.face_mail`;
--CREATE TABLE `face_mail` (
--	`id` bigint(20) NOT NULL AUTO_INCREMENT,
--	`header` varchar(254) DEFAULT NULL,
--	`message_read` bit(1) NOT NULL,
--	`sent_date` date DEFAULT NULL,
--	`mailText` varchar(254) DEFAULT NULL,
--	PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
--
--DROP TABLE IF EXISTS `testdb.face_user`;
--CREATE TABLE `face_user` (
--	`id` bigint(20) NOT NULL AUTO_INCREMENT,
--	`accountExpired` bit(1) DEFAULT NULL,
--	`accountLocked` bit(1) DEFAULT NULL,
--	`credentials_expired` bit(1) DEFAULT NULL,
--	`email` varchar(100) NOT NULL,
--	`enabled` bit(1) DEFAULT NULL,
--	`firstName` varchar(25) NOT NULL,
--	`lastName` varchar(50) NOT NULL,
--	`password` varchar(255) NOT NULL,
--	`user_name` varchar(15) NOT NULL,
--	PRIMARY KEY (`id`),
--	UNIQUE KEY `UK_7xvd5coc000k812h4cc24hsf2` (`user_name`)
--) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
--
--DROP TABLE IF EXISTS `testdb.user_received_mail`;
--CREATE TABLE `user_received_mail` (
--	`user_id` bigint(20) NOT NULL,
--	`mail_id` bigint(20) NOT NULL,
--	`mail_read` bit(1) DEFAULT FALSE ,
--	PRIMARY KEY (`mail_id`, `user_id`),
--	KEY `FK_5jky9sdfkhxq0fxge58i5h47f` (`mail_id`, `user_id`),
--	CONSTRAINT `FK_5jky9sdfkhxq0fxge58i5h47f` FOREIGN KEY (`mail_id`) REFERENCES `face_mail` (`id`),
--	CONSTRAINT `FK_l695b73rgy9ewr4xjo6tdce2t` FOREIGN KEY (`user_id`) REFERENCES `face_user` (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--
--DROP TABLE IF EXISTS `testdb.user_sent_mail`;
--CREATE TABLE `user_sent_mail` (
--	`user_id` bigint(20) NOT NULL,
--	`mail_id` bigint(20) NOT NULL,
--	PRIMARY KEY (`mail_id`, `user_id`),
--	KEY `FK_n4fhcc43oq0a4e5tir837go65` (`mail_id`, `user_id`),
--	CONSTRAINT `FK_handvixbvfk8qgxphmbvk3v23` FOREIGN KEY (`mail_id`) REFERENCES `face_mail` (`id`),
--	CONSTRAINT `FK_n4fhcc43o7go65` FOREIGN KEY (`user_id`) REFERENCES `face_user` (`id`)
--)ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--DROP TABLE IF EXISTS `testdb.user_roles`;
--CREATE TABLE testdb.user_roles
--(
--	user_id BIGINT(20) NOT NULL,
--	role_id TINYINT(2) NOT NULL,
--	CONSTRAINT `PRIMARY` PRIMARY KEY (`user_id`, `role_id`),
--	CONSTRAINT `FK_user_id65` FOREIGN KEY (`user_id`) REFERENCES `face_user` (`id`),
--	INDEX FK_somForentkyJibrich (`user_id`)
--)ENGINE = InnoDB DEFAULT CHARSET=utf8;
--
--
--
--
--
--
--
--INSERT INTO testdb.face_user (email, password, registration_date) VALUES ('test1@kth.se', 'a', NOW());
--INSERT INTO testdb.user (email, password, registration_date) VALUES ('test2@kth.se', 'a', NOW());
--INSERT INTO testdb.user (email, password, registration_date) VALUES ('test3@kth.se', 'a', NOW());
--
--
--INSERT INTO testdb.roles (userRole) VALUES ('admin');
--INSERT INTO testdb.roles (userRole) VALUES ('user');
--
--INSERT INTO testdb.message (message_info) VALUES ('this is the wrong type of message1');
--INSERT INTO testdb.message (message_info) VALUES ('this is the wrong type of message2');
--INSERT INTO testdb.message (message_info) VALUES ('this is the wrong type of message3');
--
--
--INSERT INTO testdb.user_message (user_id, message_id) VALUES ((SELECT id FROM testdb.user
--WHERE email='test1@kth.se'), 1);
--
--INSERT INTO testdb.user_message (user_id, message_id) VALUES ((SELECT id FROM testdb.user
--WHERE email='test2@kth.se'), 2);
--
--INSERT INTO testdb.user_message (user_id, message_id) VALUES ((SELECT id FROM testdb.user
--WHERE email='test3@kth.se'), 3);
--
--
--INSERT INTO testdb.user_roles (role_id, user_id) VALUES (1, (SELECT id FROM testdb.user
--WHERE email='test1@kth.se'));
--
--INSERT INTO testdb.user_roles (role_id, user_id) VALUES (2, (SELECT id FROM testdb.user
--WHERE email='test1@kth.se'));
--
--INSERT INTO testdb.user_roles (role_id, user_id) VALUES (1, (SELECT id FROM testdb.user
--WHERE email='test2@kth.se'));
--
--INSERT INTO testdb.user_roles (role_id, user_id) VALUES (1, (SELECT id FROM testdb.user
--WHERE email='test3@kth.se'));


/*
 * MySQL script.
 * Load the database with reference data and unit test data.
 */
/*

-- password is 'password'
INSERT INTO Account (referenceId, username, password, enabled, credentialsexpired, expired, locked, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('a07bd221-3ecd-4893-a0f0-78d7c0fbf94e', 'user', '$2a$10$9/44Rne7kQqPXa0cY6NfG.3XzScMrCxFYjapoLq/wFmHz7EC9praK',
                                                TRUE, FALSE, FALSE, FALSE, 0, 'user', NOW ( ), NULL, NULL);
-- password is 'operations'
INSERT INTO Account (referenceId, username, password, enabled, credentialsexpired, expired, locked, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('7bd137c8-ab64-4a45-bf2d-d9bae3574622', 'operations',
                                                '$2a$10$CoMVfutnv1qZ.fNlHY1Na.rteiJhsDF0jB1o.76qXcfdWN6As27Zm', TRUE,
                                                FALSE, FALSE, FALSE, 0, 'user', NOW ( ), NULL, NULL);

INSERT INTO Role (id, code, label, ordinal, effectiveAt, expiresAt, createdAt)
VALUES (1, 'ROLE_USER', 'User', 0, '2015-01-01 00:00:00', NULL, NOW ( ));
INSERT INTO Role (id, code, label, ordinal, effectiveAt, expiresAt, createdAt)
VALUES (2, 'ROLE_ADMIN', 'Admin', 1, '2015-01-01 00:00:00', NULL, NOW ( ));
INSERT INTO Role (id, code, label, ordinal, effectiveAt, expiresAt, createdAt)
VALUES (3, 'ROLE_SYSADMIN', 'System Admin', 2, '2015-01-01 00:00:00', NULL, NOW ( ));

INSERT INTO AccountRole (accountId, roleId) SELECT
	                                            a.id,
	                                            r.id
                                            FROM Account a, Role r
                                            WHERE a.username = 'user' AND r.id = 1;
INSERT INTO AccountRole (accountId, roleId) SELECT
	                                            a.id,
	                                            r.id
                                            FROM Account a, Role r
                                            WHERE a.username = 'operations' AND r.id = 3;

INSERT INTO Greeting (referenceId, text, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('1e0d5287-67fd-4043-9ac4-b8d358d6d7ce', 'Ping World!', 0, 'user', NOW ( ), NULL, NULL);
INSERT INTO Greeting (referenceId, text, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('37c3178d-3b49-47b6-99d1-277b1a3e8df8', 'Hola Mundo!', 0, 'user', NOW ( ), NULL, NULL);

*/



