package com.example.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="categorybooks")
public class CategoryBooks {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Book> bookList;

    @Override
    public String toString(){
        return name;
    }
}
