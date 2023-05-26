create table book (
    id serial not null,
    author varchar(255),
    description varchar,
    name varchar(255),
    path_image varchar(255),
    price int,
    release_date varchar(255),
    categorybooks_id int,
    primary key (id)
);

create table cart (
    id  serial not null,
    person_id int,
    primary key (id)
);

create table cart_book (
    id  serial not null,
    quantity int,
    totalprice int,
    book_id int,
    cart_id int,
    primary key (id)
);
create table categorybooks (
    id  serial not null,
    name varchar(255),
    type varchar(255),
    primary key (id)
);
create table person (
    id  serial not null,
    email varchar(255),
    is_activ boolean,
    password varchar(255),
    role varchar(20),
    username varchar(100),
    year_of_birth int check (year_of_birth>=1960),
    primary key (id)
);

alter table book
    add constraint book_categorybooks_fk
    foreign key (categorybooks_id) references categorybooks;

alter table cart
    add constraint cart_person_fk
    foreign key (person_id) references person on delete cascade;

alter table cart_book
    add constraint cart_book_book_fk
    foreign key (book_id) references book;

alter table cart_book
    add constraint cart_book_cart_fk
    foreign key (cart_id) references cart on delete cascade;