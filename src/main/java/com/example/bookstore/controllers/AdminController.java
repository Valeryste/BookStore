package com.example.bookstore.controllers;


import com.example.bookstore.models.Book;
import com.example.bookstore.models.CategoryBooks;
import com.example.bookstore.models.Person;
import com.example.bookstore.security.PersonDetails;
import com.example.bookstore.services.AdminService;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.CategoryBooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private CategoryBooksService categoryBooksService;

    private BookService bookService;

    @Value("${upload.path.imagesbooks}")
    private String uploadPath;

    @Autowired
    public AdminController(AdminService adminService, CategoryBooksService categoryBooksService, BookService bookService) {
        this.adminService = adminService;
        this.categoryBooksService = categoryBooksService;
        this.bookService = bookService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        List<Person> people = adminService.allPerson();
        model.addAttribute("people", people);
        return "admin/users";
    }

    @PatchMapping("/users/{id}")
    public String ban(@PathVariable("id") int id) {
        adminService.banPerson(id);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        adminService.deletePersonById(id);
        return "redirect:/admin/users";
    }


    @GetMapping("/books")
    public String allBooks(Model model) {
        List<Book> bookList = bookService.allBook();
        model.addAttribute("books", bookList);
        return "admin/books";
    }


    @GetMapping("/books/{id}/edit")
    public String oneBook(@PathVariable("id") int id, Model model) {
        List<CategoryBooks> categoryBooks = categoryBooksService.allCategoryBooks();
        model.addAttribute("book", bookService.findBookByID(id));
        model.addAttribute("categorybooks", categoryBooks);
        return "admin/editBook";
    }

    @PatchMapping("/books/{id}")
    public String patchBook(@ModelAttribute("book") Book book,
                            @PathVariable("id") int id) {
        bookService.updatedBook(book, id);
        return "redirect:/admin/books/{id}/edit";
    }

    @GetMapping("/books/create")
    public String newBook(Model model) {
        List<CategoryBooks> categoryBooks = categoryBooksService.allCategoryBooks();
        model.addAttribute("book", new Book());
        model.addAttribute("categorybooks", categoryBooks);
        return "admin/createBook";
    }

    @PostMapping("/books/create")
    public String createBook(@RequestParam("image") MultipartFile image,
                             @ModelAttribute("book") Book book) {

        if (!image.isEmpty()) {
            String[] typeFile = image.getOriginalFilename().split("\\.");

            File file = new File(uploadPath +"/" + book.getCategory().getName());

            if(!file.mkdir()){
                file.mkdir();
            }


            String resultFileName = String.format("%s/%s.%s", book.getCategory().getName(), book.getName(), typeFile[typeFile.length - 1]);

            try {
                image.transferTo(new File(String.format("%s/%s", uploadPath, resultFileName)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            log.info("Name book:",book.getName());

            book.setPathImage(resultFileName);
            bookService.saveBook(book);
        }
        return "redirect:/";
    }

}
