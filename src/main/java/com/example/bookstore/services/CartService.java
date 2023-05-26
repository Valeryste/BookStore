package com.example.bookstore.services;


import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Cart_Book;
import com.example.bookstore.repositories.CartRepository;
import com.example.bookstore.repositories.Cart_BookRepository;
import com.example.bookstore.repositories.PeopleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    private final PeopleRepository peopleRepository;


    public CartService(CartRepository cartRepository, PeopleRepository peopleRepository, Cart_BookRepository cart_bookRepository) {
        this.cartRepository = cartRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void createCart(int person_id){
            Cart cart = new Cart();
            cart.setPerson(peopleRepository.findById(person_id));
            cartRepository.save(cart);
            log.info("cart create");
    }

    public Optional<Cart> getCart(int person_id){
        return cartRepository.findByUserId(person_id);
    }


    public void save(Cart cart){
        cartRepository.save(cart);
    }



}
