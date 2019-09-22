/* from course website */
create table Product
(
    id int auto_increment,
    name varchar,
    type varchar,
    manufacturer varchar
);

create unique index Product_id_uindex
    on Product (id);

alter table Product
    add constraint Product_pk
        primary key (id);

create table ProductionRecord
(
    production_num int auto_increment,
    product_id int,
    serial_num varchar,
    date_produced datetime
);
