DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS who_user;
DROP TABLE IF EXISTS user_settings;
DROP TABLE IF EXISTS who_role;
DROP sequence IF EXISTS book_seq;
DROP sequence IF EXISTS genre_seq;
DROP sequence IF EXISTS author_seq;
DROP sequence IF EXISTS user_seq;
DROP sequence IF EXISTS user_settings_seq;
DROP sequence IF EXISTS who_role_seq;

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

create sequence user_seq;
CREATE TABLE who_user(
    id BIGINT NOT NULL DEFAULT user_seq.nextval primary key,
    username VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255));

create sequence user_settings_seq;
CREATE TABLE user_settings(
    id BIGINT NOT NULL DEFAULT user_settings_seq.nextval primary key,
    rows_per_page INTEGER DEFAULT 2 NOT NULL,
	user_id BIGINT REFERENCES who_user(id));
alter table public.user_settings
    add constraint user_settings_who_user_constraint
        foreign key (user_id)
            references who_user;

create sequence who_role_seq;
CREATE TABLE who_role(
    id BIGINT NOT NULL DEFAULT user_seq.nextval primary key,
    role_name VARCHAR(36) NOT NULL,
    who_user_id BIGINT REFERENCES who_user(id));
alter table public.who_role
    add constraint who_role_who_user
        foreign key (who_user_id)
            references who_user;
