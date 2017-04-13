INSERT INTO user (id, username, password, firstname, lastname) VALUES (1, 'user', '123', 'Ahmad', 'Arif');
INSERT INTO user (id, username, password, firstname, lastname) VALUES (2, 'admin', '123', 'Administrator', '');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);