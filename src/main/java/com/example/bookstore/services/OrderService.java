package com.example.bookstore.services;


import com.example.bookstore.models.Order;
import com.example.bookstore.repositories.OrderRepository;
import com.example.bookstore.repositories.PeopleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;


    private final PeopleRepository peopleRepository;

    public OrderService(OrderRepository orderRepository, PeopleRepository peopleRepository) {
        this.orderRepository = orderRepository;
        this.peopleRepository = peopleRepository;
    }

    public Order findOrderById(int order_id){
        return orderRepository.findById(order_id);
    }

    @Transactional
    public Order  makingOrder(Order order,int person_id) {
        order.setPerson(peopleRepository.findById(person_id));

        orderRepository.save(order);

        return order;

    }
}
