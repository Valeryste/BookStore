package com.example.bookstore.controllers;


import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart_Book;
import com.example.bookstore.models.Order;
import com.example.bookstore.security.PersonDetails;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.Cart_BookService;
import com.example.bookstore.services.OrderService;
import com.example.bookstore.services.Order_BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {

    private final BookService bookService;

    private final OrderService orderService;

    private final Order_BookService order_bookService;

    private final Cart_BookService cart_bookService;

    public OrderController(BookService bookService, OrderService orderService, Order_BookService order_bookService, Cart_BookService cart_bookService) {
        this.bookService = bookService;
        this.orderService = orderService;
        this.order_bookService = order_bookService;
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
    public List<Cart_Book> getCart_Books(HttpSession session){
        String bookListType = (String) session.getAttribute("bookListType");
        if(bookListType.equals("list1")) {
            List<Cart_Book> cart_books = (List<Cart_Book>) session.getAttribute("books");

            return cart_books;
        }
        return null;
    }

    @ModelAttribute("book")
    public Book getBook(HttpSession session){
        String bookListType = (String) session.getAttribute("bookListType");
        if(bookListType.equals("list2")) {
            Book book = (Book) session.getAttribute("books");

            return book;
        }

       return null;

    }


    @ModelAttribute("regions")
    public List<String> getRegions(){
        List<String> regions = new ArrayList<>();

        regions.add("Minsk");
        regions.add("Grodno");
        regions.add("Gomel");
        regions.add("Mogilev");
        regions.add("Brest");
        regions.add("Vitebsk");

        return regions;
    }


    @GetMapping("")
    public String order( @ModelAttribute("order") Order order, Model model, Principal principal){
        if(principal==null){
            return "auth/login";
        }

        return "main/order";
    }

   @PostMapping("/making-order")
    public String makingOrder(@Valid Order order, BindingResult bindingResult,Principal principal,HttpSession session){

        if(bindingResult.hasErrors()){
            return "/main/order";
        }

        orderService.makingOrder(order,authPerson(principal).getId());


        String bookListType = (String) session.getAttribute("bookListType");
        if(bookListType.equals("list1")) {
            order_bookService.saveOrder_Book(order.getId(), getCart_Books(session));
        } else{
            order_bookService.saveOrder_Book(order.getId(), getBook(session));
        }


        log.info("Processing order: ",order.getCustomerName());



        return "redirect:/shop";
    }


}
