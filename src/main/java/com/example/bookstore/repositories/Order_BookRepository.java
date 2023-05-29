package com.example.bookstore.repositories;


import com.example.bookstore.models.Order_Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_BookRepository extends JpaRepository<Order_Book,Integer> {


}
