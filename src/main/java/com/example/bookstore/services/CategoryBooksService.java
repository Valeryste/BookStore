package com.example.bookstore.services;

import com.example.bookstore.models.CategoryBooks;
import com.example.bookstore.repositories.CategoryBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBooksService {
    private final CategoryBooksRepository categoryBooksRepository;

    @Autowired
    public CategoryBooksService(CategoryBooksRepository categoryBooksRepository) {
        this.categoryBooksRepository = categoryBooksRepository;
    }

    public List<CategoryBooks> allCategoryBooks(){
        return categoryBooksRepository.findAll();
    }

    public CategoryBooks categoryBooksById(int id){
        return categoryBooksRepository.findById(id);
    }
}
