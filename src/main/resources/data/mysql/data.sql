CREATE TABLE testdb.user_messages
(
	user_id BIGINT(20) NOT NULL,
	message_id INT(11) NOT NULL,
	CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, message_id)
);
CREATE TABLE testdb.messages
(
	id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	message VARCHAR(255)
);
CREATE TABLE testdb.user
(
	id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	registration_date DATETIME,
	email VARCHAR(255),
	password VARCHAR(255)
);

CREATE TABLE testdb.roles
(
	id TINYINT(2) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	role VARCHAR(10)
);

CREATE TABLE testdb.user_roles
(
	user_id BIGINT(20) NOT NULL,
	role_id TINYINT(2) NOT NULL,
	CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, role_id)
);



ALTER TABLE testdb.user_messages ADD FOREIGN KEY (user_id) REFERENCES testdb.user (id);
ALTER TABLE testdb.user_messages ADD FOREIGN KEY (message_id) REFERENCES testdb.messages (id);
CREATE INDEX FK_8wfgyxoweube8lhtt2xlcyctm ON testdb.user_messages (message_id);

ALTER TABLE testdb.user_roles ADD FOREIGN KEY (user_id) REFERENCES testdb.user (id);
ALTER TABLE testdb.user_roles ADD FOREIGN KEY (role_id) REFERENCES testdb.roles (id);
CREATE INDEX FK_somForentkyJibrich ON testdb.user_roles (role_id);

INSERT INTO testdb.user (email, password, registration_date) VALUES ('test1@kth.se', 'a', NOW());
INSERT INTO testdb.user (email, password, registration_date) VALUES ('test2@kth.se', 'a', NOW());
INSERT INTO testdb.user (email, password, registration_date) VALUES ('test3@kth.se', 'a', NOW());


INSERT INTO testdb.roles (role) VALUES ('admin');
INSERT INTO testdb.roles (role) VALUES ('user');

INSERT INTO testdb.messages (message) VALUES ('this is the wrong type of message1');
INSERT INTO testdb.messages (message) VALUES ('this is the wrong type of message2');
INSERT INTO testdb.messages (message) VALUES ('this is the wrong type of message3');


INSERT INTO testdb.user_messages (user_id, message_id) VALUES ((SELECT id FROM testdb.user
WHERE email='test1@kth.se'), 1);

INSERT INTO testdb.user_messages (user_id, message_id) VALUES ((SELECT id FROM testdb.user
WHERE email='test2@kth.se'), 2);

INSERT INTO testdb.user_messages (user_id, message_id) VALUES ((SELECT id FROM testdb.user
WHERE email='test3@kth.se'), 3);


INSERT INTO testdb.user_roles (role_id, user_id) VALUES (1, (SELECT id FROM testdb.user
WHERE email='test1@kth.se'));

INSERT INTO testdb.user_roles (role_id, user_id) VALUES (2, (SELECT id FROM testdb.user
WHERE email='test1@kth.se'));

INSERT INTO testdb.user_roles (role_id, user_id) VALUES (1, (SELECT id FROM testdb.user
WHERE email='test2@kth.se'));

INSERT INTO testdb.user_roles (role_id, user_id) VALUES (1, (SELECT id FROM testdb.user
WHERE email='test3@kth.se'));


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
VALUES ('1e0d5287-67fd-4043-9ac4-b8d358d6d7ce', 'Hello World!', 0, 'user', NOW ( ), NULL, NULL);
INSERT INTO Greeting (referenceId, text, version, createdBy, createdAt, updatedBy, updatedAt)
VALUES ('37c3178d-3b49-47b6-99d1-277b1a3e8df8', 'Hola Mundo!', 0, 'user', NOW ( ), NULL, NULL);

*/



