package com.example.bookstore.models;


import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
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
    private String customerName;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_region")
    private String deliveryRegion;

    @Column(name = "cvv")
    private int CVV;

    @Column(name="cc_number")
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp  ="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$" , message = "Must be formatted MM/YY")
    @Column(name="cc_experation")
    private String ccExperation;

    @Column(name = "cost_order")
    private int costOrder;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;


    @OneToMany(mappedBy = "order")
    private List<Order_Book> order_bookList;
}
