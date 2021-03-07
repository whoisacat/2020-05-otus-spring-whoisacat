INSERT INTO author (id,title) values (1,'Сунь-Цзы');
INSERT INTO author (id,title) values (2,'Кент Бек');
INSERT INTO author (id,title) values (3,'Роберт Мартин');
INSERT INTO author (id,title) values (4,'Л.Н. Полякова');
INSERT INTO author (id,title) values (5,'Дмитрий Кетов');
INSERT INTO author (id,title) values (6,'Дмитрий Быков');
alter sequence author_seq restart with 7;

INSERT INTO genre (id,title) values (1,'Программирование');
INSERT INTO genre (id,title) values (2,'Историческая художка');
INSERT INTO genre (id,title) values (3,'Херь несусветная');
alter sequence genre_seq restart with 4;

INSERT INTO book (id,title,author_id,genre_id) values (1,'Исскуство войны',1,2);
INSERT INTO book (id,title,author_id,genre_id) values (2,'Чистый код',3,1);
INSERT INTO book (id,title,author_id,genre_id) values (3,'Идевльный программист',3,1);
INSERT INTO book (id,title,author_id,genre_id) values (4,'Разработка через тестирование',2,1);
INSERT INTO book (id,title,author_id,genre_id) values (5,'Основы SQL',4,1);
INSERT INTO book (id,title,author_id,genre_id) values (6,'Внутреннее устройство LINUX',5,1);
alter sequence book_seq restart with 7;

INSERT INTO who_user (id,username,password) VALUES (1,'who','$2a$13$8c/moMd0sMVWPGKYe6660eQzy0ksOLtejtXIyiDJ89bknFvmAabAK');--'pass'
INSERT INTO who_user (id,username,password) VALUES (2,'another0ne','$2a$13$pxJTRCJs/LXi1E8YmMOiNu5P/2Wyz/1ohiyI6BPAJj9qODxjD1Z.m');--anotherPass
ALTER SEQUENCE user_seq RESTART WITH 3;

INSERT INTO user_settings (id,user_id) VALUES (1,1);
INSERT INTO user_settings (id,user_id) VALUES (2,2);
ALTER SEQUENCE user_settings_seq RESTART WITH 3;
