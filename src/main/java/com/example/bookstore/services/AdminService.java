package com.example.bookstore.services;

import com.example.bookstore.models.Person;
import com.example.bookstore.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public AdminService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public List<Person> allPerson() {
        return peopleRepository.findAll();
    }

    public Person personById(int id) {
        return peopleRepository.findById(id);
    }

    @Transactional
    public void deletePersonById(int id) {
        peopleRepository.deleteById(id);
    }


    @Transactional
    public void banPerson(int id) {
        Person person = peopleRepository.findById(id);
        if (person.isActive()) {
            person.setActive(false);
        } else {
            person.setActive(true);
        }
        peopleRepository.save(person);
    }

}
