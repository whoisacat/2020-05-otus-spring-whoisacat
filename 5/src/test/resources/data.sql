INSERT INTO author (id,"name") values (1,'Сунь-Цзы');
INSERT INTO author (id,"name") values (2,'Кент Бек');
INSERT INTO author (id,"name") values (3,'Роберт Мартин');
INSERT INTO author (id,"name") values (4,'Л.Н. Полякова');
INSERT INTO author (id,"name") values (5,'Дмитрий Кетов');
INSERT INTO author (id,"name") values (6,'Дмитрий Быков');

INSERT INTO genre (id,"name") values (1,'Программирование');
INSERT INTO genre (id,"name") values (2,'Историческая художка');
INSERT INTO genre (id,"name") values (3,'Херь несусветная');

INSERT INTO book (id,"name",author_id,genre_id) values (1,'Исскуство войны',1,2);
INSERT INTO book (id,"name",author_id,genre_id) values (2,'Чистый код',3,1);
INSERT INTO book (id,"name",author_id,genre_id) values (3,'Идевльный программист',3,1);
INSERT INTO book (id,"name",author_id,genre_id) values (4,'Разработка через тестирование',2,1);
INSERT INTO book (id,"name",author_id,genre_id) values (5,'Основы SQL',4,1);
INSERT INTO book (id,"name",author_id,genre_id) values (6,'Внутреннее устройство LINUX',5,1);
