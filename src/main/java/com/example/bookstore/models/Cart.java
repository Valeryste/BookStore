package com.example.bookstore.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;


    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cart_Book> cart_books;

    @Column(name = "total_price_cart")
    private int totalPriceCart;


}
