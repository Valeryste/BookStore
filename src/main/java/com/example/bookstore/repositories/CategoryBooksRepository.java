package com.example.bookstore.repositories;


import com.example.bookstore.models.CategoryBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryBooksRepository extends JpaRepository<CategoryBooks,Integer> {
    List<CategoryBooks> findAllBy();

    CategoryBooks findById(int id);

}
