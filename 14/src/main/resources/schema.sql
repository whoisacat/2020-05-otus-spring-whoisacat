DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;
DROP sequence IF EXISTS book_seq;
DROP sequence IF EXISTS genre_seq;
DROP sequence IF EXISTS author_seq;

create sequence genre_seq;
CREATE TABLE genre(
	id BIGINT NOT NULL DEFAULT genre_seq.nextval primary key,
	title VARCHAR(255),
    mongo_id VARCHAR(255));

create sequence author_seq;
CREATE TABLE public.author(
	id BIGINT NOT NULL DEFAULT author_seq.nextval primary key,
	title VARCHAR(255),
    mongo_id VARCHAR(255));

create sequence book_seq;
CREATE TABLE public.book(
	id BIGINT NOT NULL DEFAULT book_seq.nextval primary key,
	title VARCHAR(255),
	author_id BIGINT REFERENCES author(id),
	genre_id BIGINT REFERENCES genre(id),
    mongo_id VARCHAR(255));

alter table public.book
    add constraint FKklnrv3weler2ftkweewlky958
    foreign key (author_id)
    references author;
alter table book
    add constraint FKm1t3yvw5i7olwdf32cwuul7ta
    foreign key (genre_id)
    references genre;

alter table genre
       drop constraint if exists UK27x9hd9purnqmmpl87umo1olq;
alter table genre
       add constraint UK27x9hd9purnqmmpl87umo1olq unique (title);
