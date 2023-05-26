package com.example.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import java.util.List;


@Data
@Entity
@Table(name="book")
public class Book {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private int price;

    @Column(name="author")
    private String author;

    @Column(name="release_date")
    private String releaseDate;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="categorybooks_id", referencedColumnName = "id")
    private CategoryBooks category;

    @Column(name="path_image")
    private String pathImage;

    @OneToMany(mappedBy="cart",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cart_Book> cart_bookList;

}
