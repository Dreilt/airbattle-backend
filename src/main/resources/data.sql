insert into roles (id, name) values (1, 'ROLE_ADMIN');
insert into roles (id, name) values (2, 'ROLE_PLAYER');

insert into users (id, user_name, email, password, blocked, user_is_online) values (1, 'Administrator', 'administrator@administrator.pl', '$2a$10$vwaTnaz2QoEusWS30Ic4Ru/RPd5OuIi83ZuIG5wiCASUNomfJDQsi', false, false);

insert into user_role (user_id, role_id) values (1, 1);