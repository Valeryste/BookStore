package com.example.bookstore.models;


import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name="orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_date")
    private Date date = new Date();

    @Column(name = "customer_name")
    @NotBlank(message = "name cannot be empty")
    private String customerName;

    @Column(name = "customer_last_name")
    @NotBlank(message = "last name cannot be empty")
    private String customerLastName;

    @Column(name = "delivery_address")
    @NotBlank(message = "address cannot be empty")
    private String deliveryAddress;

    @Column(name = "delivery_region")
    @NotBlank(message = "region cannot be empty")
    private String deliveryRegion;

    @Column(name="cc_number")
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp  ="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$" , message = "Must be formatted MM/YY")
    @Column(name="cc_experation")
    private String ccExperation;

    @Column(name = "cvv")
    @Digits(integer = 3, message = "CVV must be a three-digit number", fraction = 0)
    private int CVV;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;


    @OneToMany(mappedBy = "order")
    private List<Order_Book> order_bookList;
}
