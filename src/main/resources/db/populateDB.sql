DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User1', 'use1@yandex.ru', 'password'),
('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
('USER1', 100002);



INSERT INTO meals ( user_id,date_time ,description,calories)
VALUES (100001,'2022-10-19 10:23:54','obed',1488),
       (100001,'2022-10-19 11:00:00','zavtrack',228),
          (100003,'2020-10-19 11:00:00','Uzin',1444);