package com.example.bookstore.config;

import com.example.bookstore.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        //конифгурация Spring Security
        //конфигурация авторизации
        httpSecurity
                .authorizeRequests()
                .antMatchers( "/images/**/**", "/fonts/**/**", "/css/**/**", "/js/**/**", "/", "/auth/login",
                        "/auth/registration", "/error","/**")
                .permitAll()
                .anyRequest().hasAnyRole("USER","ADMIN")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/procces_login")
                .defaultSuccessUrl("/",true)
                .failureForwardUrl("/auth/error")
                .and()
                .rememberMe()
                .userDetailsService(personDetailsService)
                .key("mySecret")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");

    }


    //настраивает конфигурацию аутефинкация
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEcnoder());

    }

    @Bean
    public PasswordEncoder getPasswordEcnoder(){
        return new BCryptPasswordEncoder();
    }

}
