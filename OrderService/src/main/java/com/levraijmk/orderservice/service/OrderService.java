package com.levraijmk.orderservice.service;

import com.levraijmk.orderservice.Event.BookingEvent;
import com.levraijmk.orderservice.entity.Order;
import com.levraijmk.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

     @Autowired
    public OrderService(final OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    //methode privé pour la creation de la commande
    private Order createOrder(BookingEvent bookingEvent){
        return Order.builder()
                .customerId(bookingEvent.getUserId())
                .event_id(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

    @KafkaListener(topics = "booking",groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent){
        log.info("Reception de la reservation {} ",bookingEvent);

        //Creation de la reservation dans la db

        Order order = createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);

        // Mettre à jour les inventaires



    }


}
