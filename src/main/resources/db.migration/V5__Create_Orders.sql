create table orders(
    id INteger primary key generated by default AS IDENTITY,
    customer_name varchar (100),
    customer_last_name varchar (100),
    delivery_address varchar (100),
    delivery_region varchar (100),
    cc_number varchar (50),
    cc_experation varchar (25),
    cvv int,
    order_date timestamp,
    person_id int,
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);