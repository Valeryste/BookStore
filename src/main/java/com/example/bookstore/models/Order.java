package com.example.bookstore.models;


import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;


/*@Data
@Entity*/
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date placedAt = new Date();

    private String customerName;

    private String customerLastName;

    private String deliveryAddress;

    private String deliveryRegion;

    @Column(name="delivery_zip")
    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @Column(name="cc_number")
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp  ="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$" , message = "Must be formatted MM/YY")
    @Column(name="cc_expiration")
    private String ccExpiration;
    
    private int costOrder;


}
