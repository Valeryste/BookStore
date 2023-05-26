package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.CategoryBooks;
import com.example.bookstore.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService  {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void saveBook(Book book) {
        log.info("Saving book: {}",book);
        bookRepository.save(book);
    }

    public MultipartFile getNewFile(String fileName, MultipartFile currentFile){
        return new MultipartFile() {
            @Override
            public String getName() {
                return currentFile.getName();
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return currentFile.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return currentFile.isEmpty();
            }

            @Override
            public long getSize() {
                return currentFile.getSize();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return currentFile.getBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return currentFile.getInputStream();
            }

            @Override
            public Resource getResource() {
                return MultipartFile.super.getResource();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
            }

            @Override
            public void transferTo(Path dest) throws IOException, IllegalStateException {
                MultipartFile.super.transferTo(dest);
            }

        };
    }

    public List<Book> allBook(){
        return bookRepository.findAll();
    }

    public List<Book> bookByCategory(String category){
        Optional<Book> bookOptional = bookRepository.findByCategoryName(category);
        return bookOptional.isPresent()
                ? Collections.singletonList(bookOptional.get())
                : null;

    }


    public CategoryBooks categoryBooksById(int id){
        return bookRepository.findCategoryById(id);
    }

    public Book findBookByID(int id){
        return bookRepository.findBookById(id);
    }

    @Transactional
    public void updatedBook(Book updatedBook, int id){
        Book book = bookRepository.findBookById(id);

        if(updatedBook.getName()!=null){
            book.setName(updatedBook.getName());
        }
        if(updatedBook.getPrice()!=0){
            book.setPrice(updatedBook.getPrice());
        }
        if(updatedBook.getDescription()!=null){
            book.setDescription(updatedBook.getDescription());
        }
        if(updatedBook.getCategory()!=null){
            book.setCategory(updatedBook.getCategory());
        }
        if(updatedBook.getReleaseDate()!=null){
            book.setReleaseDate(updatedBook.getReleaseDate());
        }
        if(updatedBook.getPathImage()!=null){
            book.setPathImage(updatedBook.getPathImage());
        }
        if(updatedBook.getAuthor()!=null){
            book.setAuthor(updatedBook.getAuthor());
        }
        bookRepository.save(book);

    }

}
