INSERT INTO author (id,title) VALUES (1,'Сунь-Цзы');
INSERT INTO author (id,title) VALUES (2,'Кент Бек');
INSERT INTO author (id,title) VALUES (3,'Роберт Мартин');
INSERT INTO author (id,title) VALUES (4,'Л.Н. Полякова');
INSERT INTO author (id,title) VALUES (5,'Дмитрий Кетов');
ALTER SEQUENCE author_seq RESTART WITH 6;

INSERT INTO genre (id,title) VALUES (1,'Программирование');
INSERT INTO genre (id,title) VALUES (2,'Историческая художка');
ALTER SEQUENCE genre_seq RESTART WITH 3;

INSERT INTO book (id,title,author_id,genre_id) VALUES (1,'Исскуство войны',1,2);
INSERT INTO book (id,title,author_id,genre_id) VALUES (2,'Чистый код',3,1);
INSERT INTO book (id,title,author_id,genre_id) VALUES (3,'Идеальный программист',3,1);
INSERT INTO book (id,title,author_id,genre_id) VALUES (4,'Разработка через тестирование',2,1);
INSERT INTO book (id,title,author_id,genre_id) VALUES (5,'Основы SQL',4,1);
INSERT INTO book (id,title,author_id,genre_id) VALUES (6,'Внутреннее устройство LINUX',5,1);
ALTER SEQUENCE book_seq RESTART WITH 7;

INSERT INTO who_user (id,email,password,first_name,last_name)
    VALUES (1,'who@whoisacat.com','$2a$13$8c/moMd0sMVWPGKYe6660eQzy0ksOLtejtXIyiDJ89bknFvmAabAK','Антон','Цыпляшов');--'pass'
INSERT INTO who_user (id,email,password,first_name,last_name)
    VALUES (2,'another0ne@whoisacat.com','$2a$13$pxJTRCJs/LXi1E8YmMOiNu5P/2Wyz/1ohiyI6BPAJj9qODxjD1Z.m','Гогорий','Иванов');--anotherPass
ALTER SEQUENCE user_seq RESTART WITH 3;

INSERT INTO user_settings (id,user_id) VALUES (1,1);
INSERT INTO user_settings (id,user_id) VALUES (2,2);
ALTER SEQUENCE user_settings_seq RESTART WITH 3;

INSERT INTO who_role (id,role_name,who_user_id) VALUES (1,'ROLE_USER',1);
INSERT INTO who_role (id,role_name,who_user_id) VALUES (2,'ROLE_ADMIN',1);
INSERT INTO who_role (id,role_name,who_user_id) VALUES (3,'ROLE_USER',2);
ALTER SEQUENCE who_role_seq RESTART WITH 4;
