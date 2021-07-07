insert into person (id, name) values (1, 'Admin');

insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'AUTH_USER');

insert into auth_user (id, username, password, person_id) values (1, 'admin', '$2a$10$/BokLf7JuxdOZZK5hEUzauPOUnfyiWZ.P3SgnsBaE14FD.kjKd/U2', 1);
insert into auth_user (id, username, password) values (2, 'user', '$2a$10$vIQpONjeg31v..Dat4L6BO.u.hliQ5e0NrJQU.JMWhA/3R.ZRmZl2');

insert into auth_user_roles (auth_user_id, roles_id) values (1, 1);
insert into auth_user_roles (auth_user_id, roles_id) values (2, 2);