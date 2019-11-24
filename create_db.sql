/*
AUTH: Jared O'Toole
DATE: Thu, Oct 24th, 2019
PROJ: ProductionLineTracker
FILE: create_db.sql

The SQL to initialize the database.
 */

drop table if exists product;
drop table if exists prodsrecord;
drop table if exists employee;

create table product (
    primary key (id),

    id    int     not null auto_increment unique,
    name  varchar not null,
    type  varchar not null,
    manuf varchar not null,
);

create table prodsrecord (
    primary key (prodsnum),
    foreign key (prodid) references product (id)
        on delete restrict on update cascade,

    prodsnum  int       not null auto_increment unique,
    prodid    int       not null,
    serialnum varchar   not null,
    date      timestamp not null,
);

create table employee (
    primary key (id),

    id    int     not null auto_increment unique,
    name  varchar not null unique,
    first varchar as substr(trim(name), 0, position(' ', trim(name)) - 1),
    last  varchar as substr(trim(name), position(' ', trim(name)) + 1),
    user  varchar as concat(lower(substr(first, 0, 1)), lower(last)),
    email varchar as concat_ws('.', lower(first), lower(last)),
    pswd  varchar not null
);
