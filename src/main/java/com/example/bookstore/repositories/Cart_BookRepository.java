package com.example.bookstore.repositories;


import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Cart_Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cart_BookRepository extends JpaRepository<Cart_Book,Integer> {

    Optional<Cart_Book> findCart_BookByBookAndCart(Book book, Cart Cart);


    List<Cart_Book> findAllByCart(Cart cart);


    Cart_Book findByCart(Cart cart);

    Cart_Book findById(int id);

    void deleteCart_BookById(int id);

    void deleteAllByCart(Cart cart);
}
