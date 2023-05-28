package com.example.bookstore.models;

import com.example.bookstore.models.Order;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_book")
public class Order_Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order order;


    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private int total_price;

}
