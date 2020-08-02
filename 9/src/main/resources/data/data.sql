INSERT INTO author (id,title) values (1,'Сунь-Цзы');
INSERT INTO author (id,title) values (2,'Кент Бек');
INSERT INTO author (id,title) values (3,'Роберт Мартин');
INSERT INTO author (id,title) values (4,'Л.Н. Полякова');
INSERT INTO author (id,title) values (5,'Дмитрий Кетов');
alter sequence author_seq restart with 6;

INSERT INTO genre (id,title) values (1,'Программирование');
INSERT INTO genre (id,title) values (2,'Историческая художка');
alter sequence genre_seq restart with 3;

INSERT INTO book (id,title,author_id,genre_id) values (1,'Исскуство войны',1,2);
INSERT INTO book (id,title,author_id,genre_id) values (2,'Чистый код',3,1);
INSERT INTO book (id,title,author_id,genre_id) values (3,'Идеальный программист',3,1);
INSERT INTO book (id,title,author_id,genre_id) values (4,'Разработка через тестирование',2,1);
INSERT INTO book (id,title,author_id,genre_id) values (5,'Основы SQL',4,1);
INSERT INTO book (id,title,author_id,genre_id) values (6,'Внутреннее устройство LINUX',5,1);
alter sequence book_seq restart with 7;
