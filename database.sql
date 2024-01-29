create table products(
    id int not null primary key auto_increment,
    name varchar(100) not null ,
    description varchar(500) not null ,
    price bigint not null
)engine InnoDB;

select * from products;

drop table products;