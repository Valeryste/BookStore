package com.example.bookstore.controllers;

import com.example.bookstore.models.Person;
import com.example.bookstore.security.PersonDetails;
import com.example.bookstore.services.CartService;
import com.example.bookstore.services.PersonDetailsService;
import com.example.bookstore.services.RegistrationService;
import com.example.bookstore.util.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final RegistrationService registrationService;

    private final PersonValidator personValidator;

    private final PersonDetailsService personDetailsService;

    private final CartService cartService;


    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, PersonDetailsService personDetailsService, CartService cartService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.personDetailsService = personDetailsService;
        this.cartService = cartService;
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        return "auth/login";
    }

    @GetMapping("/error")
    public String errorPage(Model model){
        return "auth/login";
    }

    @PostMapping("/error")
    public String loginError(@ModelAttribute("username") String username,Model model){
        try {
            PersonDetails person = (PersonDetails) personDetailsService.loadUserByUsername(username);
            model.addAttribute("loginError", "Password is incorrect");
        }catch(UsernameNotFoundException ex){
            model.addAttribute("loginError",ex.getMessage());
            log.info("User not found or banned");
        }

       return "/auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult){
           personValidator.validate(person,bindingResult);
           if(bindingResult.hasErrors()){
               return "/auth/registration";
           }

           registrationService.register(person);
           cartService.createCart(person.getId());
           return "redirect:/auth/login";
    }
}
