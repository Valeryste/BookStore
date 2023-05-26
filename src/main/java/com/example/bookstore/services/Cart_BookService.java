package com.example.bookstore.services;


import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Cart_Book;
import com.example.bookstore.repositories.Cart_BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class Cart_BookService  {
    private final Cart_BookRepository cart_bookRepository;

    private final CartService cartService;

    private final BookService bookService;


    @Autowired
    public Cart_BookService(Cart_BookRepository cart_bookRepository, BookService bookService, CartService cartService, CartService cartService1) {
        this.cart_bookRepository = cart_bookRepository;
        this.bookService = bookService;
        this.cartService = cartService1;
    }


    @Transactional
    public void addBookToCart(int bookId, Cart cart,int quantity) {
        Book book = bookService.findBookByID(bookId);
        Optional<Cart_Book> cart_book = cart_bookRepository.findCart_BookByBookAndCart(book,cart);

        if(cart_book.isPresent()){
            int currentQuantity = cart_book.get().getQuantity();
            int newQuantity = currentQuantity + quantity;

            cart_book.get().setTotalPrice(newQuantity*book.getPrice());

            cart_book.get().setQuantity(newQuantity);

            cart_bookRepository.save(cart_book.get());
        } else{
            Cart_Book new_cart_book = new Cart_Book();

            new_cart_book.setCart(cart);
            new_cart_book.setBook(bookService.findBookByID(bookId));
            new_cart_book.setQuantity(quantity);
            new_cart_book.setTotalPrice(quantity*book.getPrice());

            log.info("cart_book:" + cart_book);

            cart_bookRepository.save(new_cart_book);

        }

    }
    public List<Cart_Book> findAll(Cart cart){
        return cart_bookRepository.findAllByCart(cart);
    }

    public Cart_Book findById(int cart_book_id){
        return cart_bookRepository.findById(cart_book_id);
    }

    @Transactional
    public void deleteCart_Book(int cart_book_id){

        cart_bookRepository.deleteCart_BookById(cart_book_id);
    }

    @Transactional
    public void updatedCart_Book(int cart_book_id, int quantity){
        Cart_Book cart_book = cart_bookRepository.findById(cart_book_id);

        cart_book.setQuantity(quantity);
        cart_book.setTotalPrice(quantity*cart_book.getBook().getPrice());

        cart_bookRepository.save(cart_book);
    }


    @Transactional
    public void clearCart(Cart cart){
        cart_bookRepository.deleteAllByCart(cart);
    }


}
