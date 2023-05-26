package com.example.bookstore.services;

import com.example.bookstore.models.Person;
import com.example.bookstore.repositories.PeopleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RegistrationService {
    private final PeopleRepository peopleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person){
        log.info("Registrating person:{}", person);
         person.setActive(true);
         person.setPassword(passwordEncoder.encode(person.getPassword()));
         person.setRole("ROLE_USER");
         peopleRepository.save(person);
    }
}
