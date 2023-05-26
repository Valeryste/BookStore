package com.example.bookstore.util;

import com.example.bookstore.models.Person;
import com.example.bookstore.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
         Person person = (Person) target;
         try{
             personDetailsService.loadUserByUsername(person.getUsername());
         }catch(UsernameNotFoundException ignored){
             if(personDetailsService.loadUserByEmail(person.getEmail())){
                 errors.rejectValue("email","","Person with such email exist");
             }
             return;
         }
         errors.rejectValue("username","","Person with such name exist");
    }

}
