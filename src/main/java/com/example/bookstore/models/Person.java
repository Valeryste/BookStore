package com.example.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
@Data
@Entity
@Table(name="Person")
public class Person {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Username should be not empty")
    @Size(min=2, max=100, message = "Username should be length from 2 to 100")
    @Column(name="username")
    private String username;


    @Min(value=1960, message= "Year of birth should be more than 1960")
    @Column(name="year_of_birth")
    private int yearOfBirth;

    @NotBlank(message = "email couldn't be empty")
    @Email(message = "Email is not correct")
    @Column(name="email")
    private String email;
    @NotBlank(message = "password couldn't be empty")
    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Column(name="is_activ")
    private boolean isActive;

    @OneToOne(mappedBy = "person",cascade = CascadeType.ALL)
    @JsonIgnore
    private Cart cart;


    @Override
    public String toString(){
        return "Person{"+"id=" + id +
                ", username='" + username + '\'' +
                ", yearOfBirth= " + yearOfBirth +'\'' +
                ", password='"+ password + '\'' +'}';

    }

    public String getRoleToTemplates() {
        if(role.equals("ROLE_ADMIN")){
            return "ADMIN";
        }
        return "USER";
    }


}
