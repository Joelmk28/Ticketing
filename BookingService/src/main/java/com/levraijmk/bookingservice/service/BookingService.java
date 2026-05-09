package com.levraijmk.bookingservice.service;

import com.levraijmk.bookingservice.client.InventoryServiceClient;
import com.levraijmk.bookingservice.entity.Customer;
import com.levraijmk.bookingservice.event.BookingEvent;
import com.levraijmk.bookingservice.repository.CustomerRepository;
import com.levraijmk.bookingservice.request.BookingRequest;
import com.levraijmk.bookingservice.response.BookingResponse;
import com.levraijmk.bookingservice.response.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.math.BigDecimal;
import java.text.DateFormat;

@Service
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String,BookingEvent> kafkaTemplate;

    public BookingService(final CustomerRepository customerRepository,final InventoryServiceClient inventoryServiceClient,
                          final KafkaTemplate<String,BookingEvent> kafkaTemplate){
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.kafkaTemplate = kafkaTemplate;

    }


    private BookingEvent createBookingEvent(final BookingRequest request,final Customer customer,final InventoryResponse inventoryResponse){
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();

    }

    public BookingResponse createBooking(BookingRequest request) {
        //verifier si l'utilisateur existe

        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);

        if (customer == null) {
            throw new RuntimeException("User Not found");
        }
        //verifier l'inventaire des billets

        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());
        //on verifie si le place sont disponibles
       if(inventoryResponse.getCapacity() < request.getTicketCount()){
           throw new RuntimeException("Not enough inventory");
       }

       //creer une reservation
     final BookingEvent bookingEvent = createBookingEvent(request,customer,inventoryResponse);
//
//       //envoyer la reservation au service de commande via le topic kafka
       kafkaTemplate.send("booking",bookingEvent);
        log.info("--- Booking sent to kafka: {} ", bookingEvent);


        return BookingResponse.builder()
                .event(inventoryResponse.getEvent())
                .ticketPrice(inventoryResponse.getTicketPrice().toString())
               .totalPrice(bookingEvent.getTotalPrice())
                .venue(inventoryResponse.getVenue())
                .userId(request.getUserId())
                .eventId(request.getEventId())
                .build();
    }
}
