package com.example.bookstore.repositories;

import com.example.bookstore.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query("SELECT c FROM Cart c WHERE c.person.id = ?1")
    Optional<Cart> findByUserId(int personId);

    Optional<Cart> findById(int cart_id);
}
