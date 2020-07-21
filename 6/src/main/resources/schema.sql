DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;
DROP sequence IF EXISTS book_seq;
DROP sequence IF EXISTS genre_seq;
DROP sequence IF EXISTS author_seq;

create sequence genre_seq;
CREATE TABLE genre(
	id BIGINT NOT NULL DEFAULT genre_seq.nextval primary key,
	title VARCHAR(255));

create sequence author_seq;
CREATE TABLE public.author(
	id BIGINT NOT NULL DEFAULT author_seq.nextval primary key,
	title VARCHAR(255));

create sequence book_seq;
CREATE TABLE public.book(
	id BIGINT NOT NULL DEFAULT book_seq.nextval primary key,
	title VARCHAR(255),
	author_id BIGINT REFERENCES author(id),
	genre_id BIGINT REFERENCES genre(id));
