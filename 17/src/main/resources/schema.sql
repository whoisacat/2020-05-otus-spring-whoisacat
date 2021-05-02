DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS user_settings;
DROP TABLE IF EXISTS who_role;
DROP TABLE IF EXISTS who_user;
DROP TABLE IF EXISTS heartbeat;
DROP sequence IF EXISTS book_seq;
DROP sequence IF EXISTS genre_seq;
DROP sequence IF EXISTS author_seq;
DROP sequence IF EXISTS user_seq;
DROP sequence IF EXISTS user_settings_seq;
DROP sequence IF EXISTS who_role_seq;
DROP sequence IF EXISTS heartbeat_seq;

create sequence genre_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE genre(
    id BIGINT NOT NULL primary key,
    title VARCHAR(255));

ALTER SEQUENCE public.genre_seq OWNED BY public.genre.id;
ALTER SEQUENCE public.genre_seq OWNED BY public.genre.id;

create sequence author_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE public.author(
    id BIGINT NOT NULL primary key,
    title VARCHAR(255));
ALTER SEQUENCE public.author_seq OWNED BY public.author.id;

create sequence book_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE public.book(
    id BIGINT NOT NULL primary key,
    title VARCHAR(255),
    author_id BIGINT REFERENCES author(id),
    genre_id BIGINT REFERENCES genre(id));

alter table public.book
    add constraint FKklnrv3weler2ftkweewlky958
        foreign key (author_id)
            references author;
ALTER SEQUENCE public.book_seq OWNED BY public.book.id;
alter table book
    add constraint FKm1t3yvw5i7olwdf32cwuul7ta
        foreign key (genre_id)
            references genre;

alter table genre
drop constraint if exists UK27x9hd9purnqmmpl87umo1olq;
alter table genre
    add constraint UK27x9hd9purnqmmpl87umo1olq unique (title);

create sequence user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE who_user(
    id BIGINT NOT NULL primary key,
    email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255));
ALTER SEQUENCE public.user_seq OWNED BY public.who_user.id;

create sequence user_settings_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE user_settings(
    id BIGINT NOT NULL primary key,
    rows_per_page INTEGER DEFAULT 2 NOT NULL,
	user_id BIGINT REFERENCES who_user(id));
ALTER SEQUENCE public.user_settings_seq OWNED BY public.user_settings.id;
alter table public.user_settings
    add constraint user_settings_who_user_constraint
        foreign key (user_id)
            references who_user;

create sequence who_role_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE who_role(
    id BIGINT NOT NULL primary key,
    role_name VARCHAR(36) NOT NULL,
    who_user_id BIGINT REFERENCES who_user(id));
ALTER SEQUENCE public.who_role_seq OWNED BY public.who_role.id;
alter table public.who_role
    add constraint who_role_who_user
        foreign key (who_user_id)
            references who_user;


create sequence heartbeat_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE TABLE heartbeat(
     id BIGINT NOT NULL primary key,
     heartbeat timestamp without time zone NOT NULL);
ALTER SEQUENCE public.heartbeat_seq OWNED BY public.heartbeat.id;
