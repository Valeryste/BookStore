package com.example.bookstore.models;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cart_book")
@Slf4j
public class Cart_Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "cart_id",referencedColumnName = "id")
    private Cart cart;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "totalprice")
    private int totalPrice;

    @Override
    public String toString(){
        return book.getName() +"/quantity: "+quantity +"/idCart: "+cart.getId();
    }

}
