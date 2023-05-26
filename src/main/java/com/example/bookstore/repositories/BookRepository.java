package com.example.bookstore.repositories;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.CategoryBooks;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAll();

    CategoryBooks findCategoryById(int id);

    Book findBookById(int id);

    @Query("SELECT b FROM Book b JOIN b.category c WHERE c.name = ?1")
    Optional<Book> findByCategoryName(String categoryName);




}
