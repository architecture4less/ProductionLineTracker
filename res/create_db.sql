/*
AUTH: Jared O'Toole
DATE: Thu, Oct 24th, 2019
PROJ: ProductionLineTracker
FILE: create_db.sql

The SQL to initialize the database.
 */

drop table if exists product;
drop table if exists prodsrecord;

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

    prodsnum  int      not null auto_increment unique,
    prodid    int      not null,
    serialnum varchar  not null,
    date      datetime not null,
);
