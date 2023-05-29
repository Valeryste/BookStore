package com.example.bookstore.controllers;


import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Cart_Book;
import com.example.bookstore.models.CategoryBooks;
import com.example.bookstore.security.PersonDetails;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.CartService;
import com.example.bookstore.services.Cart_BookService;
import com.example.bookstore.services.CategoryBooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
public class MainController {
    private final BookService bookService;

    private final CategoryBooksService categoryBooksService;

    private final Cart_BookService cart_bookService;

    @Autowired
    public MainController(BookService bookService, CategoryBooksService categoryBooksService, Cart_BookService cart_bookService, CartService cartService) {
        this.bookService = bookService;
        this.categoryBooksService = categoryBooksService;
        this.cart_bookService = cart_bookService;
    }


    @ModelAttribute("authPerson")
    private PersonDetails authPerson(Principal principal){
        if(principal==null){
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails;
    }

    @ModelAttribute("cart_books")
    private List<Cart_Book> cart_bookList(Principal principal){
        if(principal==null){
            return null;
        }
        return cart_bookService.findAll(authPerson(principal).getPerson().getCart());
    }

    @ModelAttribute("totalPriceCart")
    private int totalPriceCart(Principal principal){
        if(principal==null){
            return 0;
        }
        int totalPriceCart = cart_bookList(principal).stream()
                .mapToInt(Cart_Book::getTotalPrice)
                .sum();
        return totalPriceCart;
    }

    @GetMapping("/")
    public String mainPage(@RequestParam(name = "category", required = false) String category, Model model,Principal principal){
        List<Book> bookList=bookService.allBook();
        List<CategoryBooks> categoryBooks=categoryBooksService.allCategoryBooks();

        model.addAttribute("books",bookList);
        model.addAttribute("categorybooks",categoryBooks);

        return "main/index";

    }

    @GetMapping("/book/{id}")
    public String oneBooks(@PathVariable("id") int id, Model model, HttpSession session){

        Book book = bookService.findBookByID(id);


        model.addAttribute("book",book);

        session.setAttribute("books", book);//сделал для того чтобы отправлять на страницу заказа
        session.setAttribute("bookListType", "list2");
        // и такое же сделал в кантроллере корзины

        return "main/book";
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(name = "category", required = false) String category, Model model, HttpSession session){
        List<Book> bookList;

        if(category == null || category.isEmpty()){
            bookList = bookService.allBook();
        } else{
            bookList = bookService.bookByCategory(category);
        }

        List<CategoryBooks> categoryBooks=categoryBooksService.allCategoryBooks();

        model.addAttribute("books",bookList);
        model.addAttribute("categorybooks",categoryBooks);



        return "main/shop";
    }

    @GetMapping("/banned")
    public String banPage(){
        return "main/banned";
    }

}
