package com.example.bookstore.repositories;

import com.example.bookstore.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);

    Optional<Person> findByEmail(String email);

    List<Person> findAll();

    void deleteById(int id);

    Person findById(int id);
}
