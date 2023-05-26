package com.example.bookstore.services;

import com.example.bookstore.models.Person;
import com.example.bookstore.repositories.PeopleRepository;
import com.example.bookstore.security.PersonDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;


    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Person> person=peopleRepository.findByUsername(username);

        if(person.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        if(!person.get().isActive()){
            throw new UsernameNotFoundException("User is banned");
        }

        return new PersonDetails(person.get());
    }

    public boolean loadUserByEmail(String email){
        Optional<Person> person=peopleRepository.findByEmail(email);
        if(person.isEmpty()){
            return false;
        }
        return true;
    }

}
