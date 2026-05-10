package com.levraijmk.orderservice.service;

import com.levraijmk.commonevents.event.BookingEvent;
import com.levraijmk.orderservice.client.InventoryServiceClient;
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
    private final InventoryServiceClient inventoryServiceClient;

     @Autowired
    public OrderService(final OrderRepository orderRepository,final InventoryServiceClient inventoryServiceClient){
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
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
         try{
             log.info("Reception de la reservation {} ",bookingEvent);
             System.out.println("******Kafka Log : "+bookingEvent + "*******\n");

             //Creation de la reservation dans la db

             Order order = createOrder(bookingEvent);
             orderRepository.save(order);

             // Mettre à jour les inventaires

             inventoryServiceClient.updateInventory(order.getEvent_id(),order.getTicketCount());

             log.info("L'inventaire a été mise à jour");

         }
         catch (Exception ex){
             System.out.println("******* Message d'exception "+ ex);
         }




    }


}
