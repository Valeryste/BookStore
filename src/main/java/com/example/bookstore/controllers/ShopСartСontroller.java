package com.example.bookstore.controllers;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Cart_Book;
import com.example.bookstore.security.PersonDetails;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.CartService;
import com.example.bookstore.services.Cart_BookService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/cart")
public class ShopСartСontroller {

    private final CartService cartService;

    private final BookService bookService;

    private final Cart_BookService cart_bookService;


    public ShopСartСontroller(CartService cartService, BookService bookService, Cart_BookService cart_bookService) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.cart_bookService = cart_bookService;
    }


    @GetMapping("/updateTotalPriceCart")
    @ResponseBody
    private int totalPriceCart(HttpSession session,Principal principal){
        List<Cart_Book> cart_books =  cart_bookService.findAll(authPerson(principal).getPerson().getCart());

        int totalPriceCart =cart_books.stream()
                .mapToInt(Cart_Book::getTotalPrice)
                .sum();

        session.setAttribute("totalPriceOrder",totalPriceCart);//для страницы заказа

        return totalPriceCart;
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

    @GetMapping("")
    public String showCart(Model model, Principal principal, HttpSession session){
        List<Cart_Book> cart_books =  cart_bookService.findAll(authPerson(principal).getPerson().getCart());

        int totalPriceCart = totalPriceCart(session,principal);
        model.addAttribute("totalPriceCart", totalPriceCart);

        if(cart_books.isEmpty()){
            model.addAttribute("cartEmpty",true);
            return "main/cart";
        }

        session.setAttribute("books",cart_books);
        session.setAttribute("bookListType", "list1");


        model.addAttribute("cart_books",cart_books);
        return "main/cart";
    }


    @PostMapping("/addBook/{id}")
    public  String addBook(@PathVariable("id") int bookId,@RequestParam(name = "quantity", required = false) Integer quantity,
                           Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(principal==null){
            return "auth/login";
        }

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        cart_bookService.addBookToCart(bookId,cartService.getCart(personDetails.getId()).get(),quantity);


        return "redirect:/book/" + bookId;
    }


    @DeleteMapping("{id}/delete")
    public String deleteCart_Book(@PathVariable("id") int cart_book_id){
        log.info("idCart_BOOk"+ cart_book_id);

        cart_bookService.deleteCart_Book(cart_book_id);

        return "redirect:/cart";
    }

    @PostMapping("/update-quantity")
    @ResponseBody
    public ResponseEntity<Cart_Book> updateCart_Book(HttpSession session,@RequestParam("cart_book_id") int cart_book_id, @RequestParam("quantity") int quantity,Principal principal){
        List<Cart_Book> cart_books =  cart_bookService.findAll(authPerson(principal).getPerson().getCart());
        cart_bookService.updatedCart_Book(cart_book_id,quantity);

        Cart_Book cart_book = cart_bookService.findById(cart_book_id);

        session.setAttribute("books",cart_books);
        session.setAttribute("bookListType", "list1");

        return ResponseEntity.ok(cart_book);
    }

    @GetMapping("/clear")
    public String clearCart(Principal principal){
        cart_bookService.clearCart(cartService.getCart(authPerson(principal).getId()).get());
        log.info("cart clear");
        return "redirect:/cart";
    }
}
